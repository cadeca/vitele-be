package ro.cadeca.weasylearn.controllers

import org.springframework.web.bind.annotation.*
import ro.cadeca.weasylearn.config.ADMIN
import ro.cadeca.weasylearn.config.STUDENT
import ro.cadeca.weasylearn.config.TEACHER
import ro.cadeca.weasylearn.dto.UserProfileDTO
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.model.Teacher
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.services.UserService
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("api/user")
class UserController(private val userService: UserService) {

    @GetMapping("allOtherUsers")
    @RolesAllowed(ADMIN)
    fun findAllOtherUsers(): List<User> = userService.findAllOtherUsers()

    @GetMapping("allTeachers")
    @RolesAllowed(ADMIN)
    fun findAllTeachers(): List<Teacher> = userService.findAllTeachers()

    @GetMapping("allStudents")
    @RolesAllowed(ADMIN)
    fun findAllStudents(): List<Student> = userService.findAllStudents()

    @GetMapping("profile")
    @RolesAllowed(ADMIN, TEACHER, STUDENT)
    fun gerProfile(): UserProfileDTO = userService.getCurrentUserProfile()

    @GetMapping("allByLastName")
    @RolesAllowed(ADMIN)
    fun findAllByLastName(@RequestParam lastName: String): List<User> = userService.findAllByLastName(lastName)

    @GetMapping("allByFirstName")
    @RolesAllowed(ADMIN)
    fun findAllByFirstName(@RequestParam firstName: String): List<User> = userService.findAllByFirstName(firstName)

    @GetMapping("allByFullName")
    @RolesAllowed(ADMIN)
    fun findAllByFullName(@RequestParam lastName: String, @RequestParam firstName: String): List<User> = userService.findAllByFullName(lastName, firstName)
}
