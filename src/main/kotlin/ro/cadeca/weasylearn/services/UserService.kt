package ro.cadeca.weasylearn.services

import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.converters.user.KeycloakUserToUserDocumentConverter
import ro.cadeca.weasylearn.converters.user.UserDocumentToUserModelConverter
import ro.cadeca.weasylearn.converters.user.UserDocumentToUserProfileDtoConverter
import ro.cadeca.weasylearn.converters.user.UserModelToDocumentConverter
import ro.cadeca.weasylearn.dto.UserProfileDTO
import ro.cadeca.weasylearn.model.KeycloakUser
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserRepository

@Service
class UserService(private val userRepository: UserRepository,
                  private val authenticationService: AuthenticationService,
                  private val userDocumentToUserProfileDtoConverter: UserDocumentToUserProfileDtoConverter,
                  private val keycloakUserToUserDocumentConverter: KeycloakUserToUserDocumentConverter,
                  private val userDocumentToUserModelConverter: UserDocumentToUserModelConverter,
                  private val userModelToDocumentConverter: UserModelToDocumentConverter) {

    fun createUser(user: User) =
            user.let(userModelToDocumentConverter::convert)
                    .let(userRepository::save)

    fun findAll(): List<User> =
            userRepository.findAll().map(userDocumentToUserModelConverter::convert)

    fun findAllByLastName(lastName: String): List<User> {
        val foundUsersByLastName = arrayListOf<User>()

        userRepository.findAll().iterator().forEach {
            if (it.lastName == lastName) {
                foundUsersByLastName.add(userDocumentToUserModelConverter.convert(it))
            }
        }

        return foundUsersByLastName
    }

    fun findAllByFirstName(firstName: String): List<User> {
        val foundUsersByFirstName = arrayListOf<User>()

        userRepository.findAll().iterator().forEach {
            if (it.firstName == firstName) {
                foundUsersByFirstName.add(userDocumentToUserModelConverter.convert(it))
            }
        }

        return foundUsersByFirstName
    }

    fun findAllByFullName(lastName: String, firstName: String): List<User> {
        val foundUsersByFullName = arrayListOf<User>()

        userRepository.findAll().iterator().forEach {
            if (it.lastName == lastName && it.firstName == firstName) {
                foundUsersByFullName.add(userDocumentToUserModelConverter.convert(it))
            }
        }

        return foundUsersByFullName
    }

    fun getCurrentUserProfile(): UserProfileDTO {
        val keycloakUser = authenticationService.getKeycloakUser()
        val user = userRepository.findByUsername(keycloakUser.username) ?: createNewUserFrom(keycloakUser)

        return userDocumentToUserProfileDtoConverter.convert(user)
    }

    private fun createNewUserFrom(kcUser: KeycloakUser): UserDocument {
        val newUser = keycloakUserToUserDocumentConverter.convert(kcUser)

        return userRepository.save(newUser)
    }
}
