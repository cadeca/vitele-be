package ro.cadeca.weasylearn.services.keycloak

interface PasswordGenerator {
    fun generate(username: String): String
}
