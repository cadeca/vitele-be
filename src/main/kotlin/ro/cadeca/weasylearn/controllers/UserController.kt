package ro.cadeca.weasylearn.controllers

import org.springframework.web.bind.annotation.*
import ro.cadeca.weasylearn.config.Roles.Companion.ADMIN
import ro.cadeca.weasylearn.dto.UserProfileDTO
import ro.cadeca.weasylearn.dto.UserWrapperDTO
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.model.Teacher
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.services.UserService
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("api/user")
class UserController(private val userService: UserService) {

    @GetMapping("{username}")
    fun findUserByUsername(@PathVariable username: String): UserWrapperDTO {
        return userService.findUserByUsername(username).let { UserWrapperDTO() }
    }

    @GetMapping("search")
    fun findByQuery(@RequestParam query: String): List<User> {
        return userService.findAllByNameQuery(query)
    }

    @GetMapping("all")
    @RolesAllowed(ADMIN)
    fun findAllUsers(): List<User> = userService.findAllUsers()

    @GetMapping("other")
    @RolesAllowed(ADMIN)
    fun findAllOtherUsers(): List<User> = userService.findAllOtherUsers()

    @GetMapping("teachers")
    @RolesAllowed(ADMIN)
    fun findAllTeachers(): List<Teacher> = userService.findAllTeachers()

    @GetMapping("students")
    @RolesAllowed(ADMIN)
    fun findAllStudents(): List<Student> = userService.findAllStudents()

    @GetMapping("profile")
    fun gerProfile(): UserProfileDTO = userService.getCurrentUserProfile()

}
