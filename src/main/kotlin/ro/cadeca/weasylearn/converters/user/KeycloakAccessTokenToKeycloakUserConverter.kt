package ro.cadeca.weasylearn.converters.user

import org.keycloak.representations.AccessToken
import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.KeycloakUser

@Converter
class KeycloakAccessTokenToKeycloakUserConverter : IConverter<AccessToken, KeycloakUser> {
    override fun convert(token: AccessToken): KeycloakUser {
        return KeycloakUser(
                token.preferredUsername,
                token.name, token.givenName,
                token.familyName,
                token.email)
    }
}
