package ro.cadeca.weasylearn.services

import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.converters.user.UserEntityToModelConverter
import ro.cadeca.weasylearn.converters.user.UserModelToEntityConverter
import ro.cadeca.weasylearn.model.User
import ro.cadeca.weasylearn.persistence.user.UserRepository

@Service
class UserService(private val userRepository: UserRepository,
                  private val userEntityToModelConverter: UserEntityToModelConverter,
                  private val userModelToEntityConverter: UserModelToEntityConverter) {

    fun createUser(user: User) =
            user.let(userModelToEntityConverter::convert)
                    .let(userRepository::save)

    fun findAll(): List<User> =
            userRepository.findAll().map(userEntityToModelConverter::convert)

    fun findAllByLastName(lastName: String): List<User> {
        val foundUsersByLastName = arrayListOf<User>()

        userRepository.findAll().iterator().forEach {
            if (it.lastName == lastName) {
                foundUsersByLastName.add(userEntityToModelConverter.convert(it))
            }
        }

        return foundUsersByLastName
    }

    fun findAllByFirstName(firstName: String): List<User> {
        val foundUsersByFirstName = arrayListOf<User>()

        userRepository.findAll().iterator().forEach {
            if (it.firstName == firstName) {
                foundUsersByFirstName.add(userEntityToModelConverter.convert(it))
            }
        }

        return foundUsersByFirstName
    }

    fun findAllByFullName(lastName: String, firstName: String): List<User> {
        val foundUsersByFullName = arrayListOf<User>()

        userRepository.findAll().iterator().forEach {
            if (it.lastName == lastName && it.firstName == firstName) {
                foundUsersByFullName.add(userEntityToModelConverter.convert(it))
            }
        }

        return foundUsersByFullName
    }

    fun findAllByYear(year: Int): List<User> {
        val foundUsersByYear = arrayListOf<User>()

        userRepository.findAll().iterator().forEach {
            if (it.year == year) {
                foundUsersByYear.add(userEntityToModelConverter.convert(it))
            }
        }

        return foundUsersByYear
    }

    fun findAllByGroupId(groupId: String): List<User> {
        val foundUsersByGroup = arrayListOf<User>()

        userRepository.findAll().iterator().forEach {
            if (it.groupId == groupId) {
                foundUsersByGroup.add(userEntityToModelConverter.convert(it))
            }
        }

        return foundUsersByGroup
    }
}
