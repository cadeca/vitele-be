package ro.cadeca.weasylearn.services

import ro.cadeca.weasylearn.model.KeycloakUser

interface UserTypeSelector {
    fun selectType(kcUser: KeycloakUser): String
}
