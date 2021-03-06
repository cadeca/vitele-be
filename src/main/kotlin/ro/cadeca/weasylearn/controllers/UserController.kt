package ro.cadeca.weasylearn.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ro.cadeca.weasylearn.config.Roles.Companion.ADMIN
import ro.cadeca.weasylearn.converters.UserToSearchDtoConverter
import ro.cadeca.weasylearn.converters.user.StudentToDtoConverter
import ro.cadeca.weasylearn.converters.user.TeacherToDtoConverter
import ro.cadeca.weasylearn.converters.user.UserToDtoConverter
import ro.cadeca.weasylearn.converters.user.UserToWrapperDtoConverter
import ro.cadeca.weasylearn.dto.*
import ro.cadeca.weasylearn.services.UserService
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("api/user")
class UserController(private val userService: UserService,
                     private val userToDtoConverter: UserToDtoConverter,
                     private val studentToDtoConverter: StudentToDtoConverter,
                     private val teacherToDtoConverter: TeacherToDtoConverter,
                     private val userToWrapperDtoConverter: UserToWrapperDtoConverter,
                     private val userToSearchDtoConverter: UserToSearchDtoConverter) {

    @GetMapping("{username}")
    fun findUserByUsername(@PathVariable username: String): UserWrapperDTO {
        return userService.findUserByUsername(username).let(userToWrapperDtoConverter::convert)
    }

    @GetMapping("search")
    fun findByQuery(@RequestParam("query") query: String, @RequestParam(required = false) type: String?): List<UserSearchDTO> {
        return userService.findAllByNameQuery(query, type).map(userToSearchDtoConverter::convert)
    }

    @GetMapping("all")
    @RolesAllowed(ADMIN)
    fun findAllUsers(): List<UserWrapperDTO> = userService.findAllUsers().map(userToWrapperDtoConverter::convert)

    @GetMapping("others")
    @RolesAllowed(ADMIN)
    fun findAllOtherUsers(): List<UserDTO> = userService.findAllOtherUsers().map(userToDtoConverter::convert)

    @GetMapping("teachers")
    @RolesAllowed(ADMIN)
    fun findAllTeachers(): List<TeacherDTO> = userService.findAllTeachers().map(teacherToDtoConverter::convert)

    @GetMapping("students")
    @RolesAllowed(ADMIN)
    fun findAllStudents(): List<StudentDTO> = userService.findAllStudents().map(studentToDtoConverter::convert)

    @GetMapping("profile")
    fun getProfile(): UserProfileDTO = userService.getCurrentUserProfile()

    @PostMapping("profile/image")
    fun uploadProfileImage(@RequestParam("file") file: MultipartFile) = userService.saveProfilePicture(file)

    @GetMapping("profile/image", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun serveProfileImage() = userService.getProfilePicture()

    @PutMapping(value = ["/type"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun setRoleForUser(@RequestBody userType: UserType) {
        this.userService.convertUserToType(userType.username, userType.type)
    }

    data class UserType(val username: String, val type: String)
}

