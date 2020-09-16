package ro.cadeca.weasylearn.controllers

import org.springframework.web.bind.annotation.*
import ro.cadeca.weasylearn.config.ADMIN
import ro.cadeca.weasylearn.config.TEACHER
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.services.UserService
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    @RolesAllowed(ADMIN, TEACHER)
    fun findAll(): List<User> = userService.findAll()

    @GetMapping("allByLastName")
    @RolesAllowed(ADMIN, TEACHER)
    fun findAllByLastName(@RequestParam lastName: String): List<User> = userService.findAllByLastName(lastName)

    @GetMapping("allByFirstName")
    @RolesAllowed(ADMIN, TEACHER)
    fun findAllByFirstName(@RequestParam firstName: String): List<User> = userService.findAllByFirstName(firstName)

    @GetMapping("allByFullName")
    @RolesAllowed(ADMIN, TEACHER)
    fun findAllByFullName(@RequestParam lastName: String, @RequestParam firstName: String): List<User> = userService.findAllByFullName(lastName, firstName)

    @GetMapping("allByYear")
    @RolesAllowed(ADMIN, TEACHER)
    fun findAllByYear(@RequestParam year: Int): List<User> = userService.findAllByYear(year)

    @GetMapping("allByGroupId")
    @RolesAllowed(ADMIN, TEACHER)
    fun findAllByGroupId(@RequestParam groupId: String): List<User> = userService.findAllByGroupId(groupId)

    @PutMapping
    @RolesAllowed(ADMIN)
    fun create(@RequestBody user: User) = userService.createUser(user)
}
