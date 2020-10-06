package ro.cadeca.weasylearn

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ro.cadeca.weasylearn.services.keycloak.KeycloakAdminService
import ro.cadeca.weasylearn.services.keycloak.PasswordGenerator

class SetupKeycloak {

    private val keycloakAdminService = KeycloakAdminService(
            clientId = "weasylearn-local-admin",
            beClientId = "weasylearn-be",
            clientSecret = "11e4710d-ef43-4187-b38d-fc5a7de9ebd4",
            mainRealm = "master",
            passwordGenerator = object : PasswordGenerator {
                override fun generate(username: String): String {
                    return username
                }
            },
            realm = "weasylearn-local",
            serverUrl = "http://localhost:9999/auth"
    )

    @Test
    @Disabled
    fun run() {
        (javaClass.classLoader.getResource("users.userdata") ?: error("resource is null"))
                .readText().split("\n")
                .filterNot(String::isBlank)
                .map {
                    it.split(" ").filterNot(String::isBlank)
                }.filter { it.size == 3 }
                .forEach {
                    val firstName = it[0]
                    val lastName = it[1]
                    val role = it[2].trim()
                    keycloakAdminService.createUser(
                            username = "${firstName.toLowerCase()}.${lastName.toLowerCase()}",
                            email = "${firstName.toLowerCase()}.${lastName.toLowerCase()}@${getEmailDomain(role)}",
                            firstName = firstName,
                            lastName = lastName,
                            role = role
                    )
                }
    }

    private fun getEmailDomain(role: String): String {
        return (if (role.toLowerCase() == "student") "student." else "") + "wl.ro"
    }
}
