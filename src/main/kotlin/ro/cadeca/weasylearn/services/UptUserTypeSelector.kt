package ro.cadeca.weasylearn.services

import org.springframework.stereotype.Component
import ro.cadeca.weasylearn.config.Roles.Companion.STUDENT
import ro.cadeca.weasylearn.config.Roles.Companion.TEACHER
import ro.cadeca.weasylearn.model.KeycloakUser
import ro.cadeca.weasylearn.persistence.user.UserTypes

@Component
class UptUserTypeSelector : UserTypeSelector {
    private val emailRegex = Regex("[a-zA-Z0-9+._%\\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")

    private val professorEmailDomains = listOf("cs.upt.ro", "upt.ro", "aut.upt.ro")

    override fun selectType(kcUser: KeycloakUser): String {
        val roles = kcUser.roles
        if (roles.isNotEmpty()) {
            if (roles.contains(STUDENT))
                return UserTypes.STUDENT
            else if (roles.contains(TEACHER))
                return UserTypes.TEACHER
        }

        var emailDomain: String? = null

        if (kcUser.username.matches(emailRegex)) {
            emailDomain = kcUser.username.split("@")[1]
        } else if (kcUser.email?.matches(emailRegex) == true) {
            emailDomain = kcUser.email.split("@")[1]
        }

        if (emailDomain != null) {
            if (emailDomain == "student.upt.ro") {
                return UserTypes.STUDENT
            }
            if (professorEmailDomains.contains(emailDomain)) {
                return UserTypes.TEACHER
            }
        }

        return UserTypes.USER
    }
}
