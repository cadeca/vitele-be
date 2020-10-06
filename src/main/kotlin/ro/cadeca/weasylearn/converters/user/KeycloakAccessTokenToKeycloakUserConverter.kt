package ro.cadeca.weasylearn.converters.user

import org.keycloak.representations.AccessToken
import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.model.KeycloakUser

@Converter
class KeycloakAccessTokenToKeycloakUserConverter : IConverter<AccessToken, KeycloakUser> {
    override fun convert(source: AccessToken): KeycloakUser {
        return KeycloakUser(
                source.preferredUsername,
                source.givenName,
                source.familyName,
                source.email)
    }
}
