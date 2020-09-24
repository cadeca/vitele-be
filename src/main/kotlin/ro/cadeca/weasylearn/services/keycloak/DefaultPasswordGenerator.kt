package ro.cadeca.weasylearn.services.keycloak

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!local")
class DefaultPasswordGenerator: PasswordGenerator
