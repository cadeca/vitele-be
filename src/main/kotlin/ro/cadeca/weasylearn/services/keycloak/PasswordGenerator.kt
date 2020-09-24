package ro.cadeca.weasylearn.services.keycloak

import java.util.*

interface PasswordGenerator {
    fun generate(username: String = ""): String {
        return UUID.randomUUID().toString()
    }
}
