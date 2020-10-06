package ro.cadeca.weasylearn.services.keycloak

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.*

@Component
@Profile("!local")
class DefaultPasswordGenerator : PasswordGenerator {
    override fun generate(username: String) =
            UUID.randomUUID().toString()
}
