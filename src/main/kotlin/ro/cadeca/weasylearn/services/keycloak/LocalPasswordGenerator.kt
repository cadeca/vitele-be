package ro.cadeca.weasylearn.services.keycloak

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.*
import java.util.Calendar.YEAR

@Component
@Profile("local")
class LocalPasswordGenerator: PasswordGenerator {
    override fun generate(username: String): String {
        return "#${username}:${getCurrentYear()}|t0pS3cr3t!"
    }

    private fun getCurrentYear(): Int {
        return Calendar.getInstance().get(YEAR) - 1900
    }
}
