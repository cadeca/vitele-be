package ro.cadeca.weasylearn.services

import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.config.Roles
import ro.cadeca.weasylearn.converters.factory.UserDocumentToModelConverterFactory
import ro.cadeca.weasylearn.converters.user.*
import ro.cadeca.weasylearn.dto.UserProfileDTO
import ro.cadeca.weasylearn.exceptions.UserNotFoundException
import ro.cadeca.weasylearn.model.KeycloakUser
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserRepository
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.STUDENT
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.TEACHER
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.USER
import ro.cadeca.weasylearn.services.keycloak.KeycloakAdminService

@Service
class UserService(private val userRepository: UserRepository,
                  private val authenticationService: AuthenticationService,
                  private val keycloakAdminService: KeycloakAdminService,
                  private val userToUserProfileDtoConverter: UserDocumentToUserProfileDtoConverter,
                  private val keycloakUserToUserDocumentConverter: KeycloakUserToUserDocumentConverter,
                  private val userToUserModelConverter: UserDocumentToUserModelConverter,
                  private val userToStudentModelConverter: UserDocumentToStudentModelConverter,
                  private val userToTeacherModelConverter: UserDocumentToTeacherModelConverter,
                  private val userDocumentToModelConverterFactory: UserDocumentToModelConverterFactory) {

    fun findAllStudents() = userRepository.findByType(STUDENT).map(userToStudentModelConverter::convert)

    fun findAllTeachers() = userRepository.findByType(TEACHER).map(userToTeacherModelConverter::convert)

    fun findAllOtherUsers(): List<User> =
            userRepository.findByType(USER).map(userToUserModelConverter::convert)

    fun findAllUsers(): List<User> = userRepository.findAll()
            .map { userDocumentToModelConverterFactory.getDocumentToModelConverter(it.type).convert(it) }

    fun findAllByNameQuery(query: String): List<User> {
        return userRepository.findByFullNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, query)
                .map { userDocumentToModelConverterFactory.getDocumentToModelConverter(it.type).convert(it) }
    }

    fun findUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
                ?.let { userDocumentToModelConverterFactory.getDocumentToModelConverter(it).convert(it) }
                ?: throw UserNotFoundException(username)
    }

    fun getCurrentUserProfile(): UserProfileDTO {
        val keycloakUser = authenticationService.getKeycloakUser()
        val user = userRepository.findByUsername(keycloakUser.username) ?: createNewUserFrom(keycloakUser)

        return userToUserProfileDtoConverter.convert(user)
    }

    protected fun createNewUserFrom(kcUser: KeycloakUser): UserDocument {
        var newUser = keycloakUserToUserDocumentConverter.convert(kcUser)
        newUser = userRepository.save(newUser)

        val username = newUser.username
        when (newUser.type) {
            STUDENT -> keycloakAdminService.assignRoleToUser(username, Roles.STUDENT)
            TEACHER -> keycloakAdminService.assignRoleToUser(username, Roles.TEACHER)
        }

        return newUser
    }

    fun convertUserToType(username: String, type: String) {
        val userDocument = userRepository.findByUsername(username) ?: throw UserNotFoundException(username)
        userDocument.type = type
        userRepository.save(userDocument)

        keycloakAdminService.assignRoleToUser(username, type)
    }
}
