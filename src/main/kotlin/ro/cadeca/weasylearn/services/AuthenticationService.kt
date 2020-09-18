package ro.cadeca.weasylearn.services

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.converters.user.KeycloakAccessTokenToKeycloakUserConverter
import ro.cadeca.weasylearn.model.KeycloakUser


@Service
class AuthenticationService(
        private val kcTokenConverter: KeycloakAccessTokenToKeycloakUserConverter) {

    fun getAuthentication(): KeycloakAuthenticationToken {
        return SecurityContextHolder.getContext().authentication as KeycloakAuthenticationToken
    }

    fun getKeycloakUser(): KeycloakUser {
        val authentication = getAuthentication()
        val token = authentication.account.keycloakSecurityContext.token
        return kcTokenConverter.convert(token).also {
            it.roles = authentication.authorities.map { ga -> ga.authority.removePrefix("ROLE_") }
        }
    }
}
