package ro.cadeca.weasylearn.services.keycloak

import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.*
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ro.cadeca.weasylearn.model.KeycloakUser

@Service
class KeycloakAdminService(
        @Value("\${keycloak.client.id}")
        private val clientId: String,
        @Value("\${keycloak.client.secret}")
        private val clientSecret: String,
        @Value("\${keycloak.realm}")
        private val realm: String,
        @Value("\${keycloak.main.realm}")
        private val mainRealm: String,
        @Value("\${keycloak.server.url}")
        private val serverUrl: String,
        @Value("\${keycloak.be.client.id}")
        private val beClientId: String,

        private val passwordGenerator: PasswordGenerator
) {

    private var _clientId: String? = null

    private val keycloak = KeycloakBuilder.builder()
            .serverUrl(serverUrl)
            .realm(mainRealm)
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .build()

    private fun realm(): RealmResource {
        return keycloak.realm(realm)
    }

    private fun users(): UsersResource {
        return realm().users()
    }

    private fun user(userId: String): UserResource {
        return users()[userId] ?: throw IllegalStateException("No such user with id $userId")
    }

    private fun client(): ClientResource {
        return realm().clients()[clientId()]
    }

    private fun clientId(): String {
        return _clientId ?: realm().clients().findByClientId(beClientId).first().id.also { _clientId = it }
    }

    private fun getRole(role: String): RoleResource {
        return client().roles()[role]
    }

    fun createUser(username: String, firstName: String, lastName: String, email: String, role: String) {
        //Create user representation
        val user = UserRepresentation().also {
            it.isEnabled = true
            it.username = username
            it.firstName = firstName
            it.lastName = lastName
            it.email = email
            it.requiredActions = listOf()
        }

        val response = users().create(user)
        val userId = CreatedResponseUtil.getCreatedId(response)

        // Create Credentials
        val passwordCred = CredentialRepresentation().also {
            it.isTemporary = true
            it.type = CredentialRepresentation.PASSWORD
            it.value = passwordGenerator.generate(username)
        }
        // Set Password
        val userResource = user(userId)
        userResource.resetPassword(passwordCred)

        //set Role
        val userClientRole = getRole(role).toRepresentation()
        userResource.roles().clientLevel(clientId()).add(listOf(userClientRole))
    }

    fun assignRoleToUser(username: String, role: String) {
        val userClientRole = getRole(role).toRepresentation()
        val userId = users().search(username).first().id
        val userResource = user(userId)
        userResource.roles().clientLevel(clientId()).add(listOf(userClientRole))
    }

    fun getAllUsers() =
            users().list().map {
                KeycloakUser(
                        username = it.username,
                        firstName = it.firstName,
                        lastName = it.lastName,
                        email = it.email,
                        roles = getRoles(it)
                )
            }

    private fun getRoles(it: UserRepresentation): List<String> {
        return user(it.id).roles().clientLevel(clientId()).listEffective().map { it.name }
    }
}
