package ro.cadeca.weasylearn.services

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.model.KeycloakUser


@Service
class AuthenticationService {

    fun getAuthentication(): KeycloakAuthenticationToken {
        return SecurityContextHolder.getContext().authentication as KeycloakAuthenticationToken
    }

    fun getKeycloakUser(): KeycloakUser {
        val authentication = getAuthentication()
        val token = authentication.account.keycloakSecurityContext.token
        return KeycloakUser(token.preferredUsername, token.name, token.givenName, token.familyName, authentication.authorities.map { it.authority })
    }
}