package ro.cadeca.weasylearn.services

import org.springframework.stereotype.Component
import ro.cadeca.weasylearn.config.Roles.Companion.STUDENT
import ro.cadeca.weasylearn.config.Roles.Companion.TEACHER
import ro.cadeca.weasylearn.model.KeycloakUser

@Component
class UptUserTypeSelector : UserTypeSelector {
    private val emailRegex = Regex("[a-zA-Z0-9+._%\\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")

    private val professorEmailDomains = listOf("cs.upt.ro", "upt.ro", "aut.upt.ro")

    override fun selectType(kcUser: KeycloakUser): String {
        val roles = kcUser.roles
        if (!roles.isNullOrEmpty()) {
            if (roles.contains(STUDENT))
                return ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT
            else if (roles.contains(TEACHER))
                return ro.cadeca.weasylearn.persistence.user.UserTypes.TEACHER
        }

        var emailDomain: String? = null

        if (kcUser.username.matches(emailRegex)) {
            emailDomain = kcUser.username.split("@")[1]
        } else if (kcUser.email?.matches(emailRegex) == true) {
            emailDomain = kcUser.email.split("@")[1]
        }

        if(emailDomain != null) {
            if (emailDomain == "student.upt.ro") {
                return ro.cadeca.weasylearn.persistence.user.UserTypes.STUDENT
            }
            if (professorEmailDomains.contains(emailDomain)) {
                return ro.cadeca.weasylearn.persistence.user.UserTypes.TEACHER
            }
        }

        return ro.cadeca.weasylearn.persistence.user.UserTypes.USER
    }
}