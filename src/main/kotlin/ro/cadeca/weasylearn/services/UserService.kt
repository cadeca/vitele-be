package ro.cadeca.weasylearn.services

import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ro.cadeca.weasylearn.converters.factory.UserDocumentToModelConverterFactory
import ro.cadeca.weasylearn.converters.user.*
import ro.cadeca.weasylearn.exceptions.user.UserNotFoundException
import ro.cadeca.weasylearn.model.KeycloakUser
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserRepository
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.STUDENT
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.TEACHER
import ro.cadeca.weasylearn.persistence.user.UserTypes.Companion.USER
import ro.cadeca.weasylearn.services.keycloak.KeycloakAdminService
import javax.annotation.PostConstruct

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

    fun findAllByNameQuery(query: String, type: String?): List<User> {
        return (type?.let {
            userRepository.findByQueryAndType(query, it)
        }
                ?: userRepository.findByFullNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, query))
                .map { userDocumentToModelConverterFactory.getDocumentToModelConverter(it.type).convert(it) }
    }

    fun findUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
                ?.let { userDocumentToModelConverterFactory.getDocumentToModelConverter(it).convert(it) }
                ?: throw UserNotFoundException(username)
    }

    fun getCurrentUserProfile() =
            userToUserProfileDtoConverter.convert(getCurrentUser())

    fun isType(username: String, type: String) =
            userRepository.existsByUsernameAndType(username, type)

    fun convertUserToType(username: String, type: String) {
        val userDocument = userRepository.findByUsername(username) ?: throw UserNotFoundException(username)
        userDocument.type = type
        userRepository.save(userDocument)

        keycloakAdminService.assignRoleToUser(username, type)
    }

    @PostConstruct
    fun syncWithKeycloakUsers() =
            keycloakAdminService.getAllUsers()
                    .forEach {
                        userRepository.findByUsername(it.username) ?: userRepository.save(createNewUserFrom(it))
                    }

    fun exists(username: String) =
            userRepository.existsByUsername(username)

    fun saveProfilePicture(file: MultipartFile) {
        getCurrentUser().apply {
            profilePicture = file.bytes
        }.let(userRepository::save)
    }

    fun getProfilePicture(): Resource {
        return ByteArrayResource((getCurrentUser().profilePicture ?: ByteArray(0)))
    }

    private fun getCurrentUser(): UserDocument {
        val keycloakUser = authenticationService.getKeycloakUser()
        return userRepository.findByUsername(keycloakUser.username) ?: createNewUserFrom(keycloakUser)
    }

    private fun createNewUserFrom(kcUser: KeycloakUser) =
            keycloakUserToUserDocumentConverter.convert(kcUser)
                    .let(userRepository::save)
}
