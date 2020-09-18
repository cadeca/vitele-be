package ro.cadeca.weasylearn.services

import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.converters.user.*
import ro.cadeca.weasylearn.dto.UserProfileDTO
import ro.cadeca.weasylearn.model.KeycloakUser
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.*

@Service
class UserService(private val userRepository: UserRepository,
                  private val authenticationService: AuthenticationService,
                  private val userToUserProfileDtoConverter: UserDocumentToUserProfileDtoConverter,
                  private val keycloakUserToUserDocumentConverter: KeycloakUserToUserDocumentConverter,
                  private val userToUserModelConverter: UserDocumentToUserModelConverter,
                  private val userToStudentModelConverter: UserDocumentToStudentModelConverter,
                  private val userToTeacherModelConverter: UserDocumentToTeacherModelConverter) {

    fun findAllStudents() = userRepository.findByType(STUDENT).map(userToStudentModelConverter::convert)

    fun findAllTeachers() = userRepository.findByType(TEACHER).map(userToTeacherModelConverter::convert)

    fun findAllOtherUsers(): List<User> =
            userRepository.findByType(USER).map(userToUserModelConverter::convert)

    fun findAllByLastName(lastName: String): List<User> {
        val foundUsersByLastName = arrayListOf<User>()

        userRepository.findAll().iterator().forEach {
            if (it.lastName == lastName) {
                foundUsersByLastName.add(userToUserModelConverter.convert(it))
            }
        }

        return foundUsersByLastName
    }

    fun findAllByFirstName(firstName: String): List<User> {
        val foundUsersByFirstName = arrayListOf<User>()

        userRepository.findAll().iterator().forEach {
            if (it.firstName == firstName) {
                foundUsersByFirstName.add(userToUserModelConverter.convert(it))
            }
        }

        return foundUsersByFirstName
    }

    fun findAllByFullName(lastName: String, firstName: String): List<User> {
        val foundUsersByFullName = arrayListOf<User>()

        userRepository.findAll().iterator().forEach {
            if (it.lastName == lastName && it.firstName == firstName) {
                foundUsersByFullName.add(userToUserModelConverter.convert(it))
            }
        }

        return foundUsersByFullName
    }

    fun getCurrentUserProfile(): UserProfileDTO {
        val keycloakUser = authenticationService.getKeycloakUser()
        val user = userRepository.findByUsername(keycloakUser.username) ?: createNewUserFrom(keycloakUser)

        return userToUserProfileDtoConverter.convert(user)
    }

    protected fun createNewUserFrom(kcUser: KeycloakUser): UserDocument {
        val newUser = keycloakUserToUserDocumentConverter.convert(kcUser)

        return userRepository.save(newUser)
    }
}
