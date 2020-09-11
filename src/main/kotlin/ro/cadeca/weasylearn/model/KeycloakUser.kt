package ro.cadeca.weasylearn.model

class KeycloakUser(val username: String,
                   val fullName: String,
                   val firstName: String,
                   val lastName: String,
                   val roles: List<String>,
                   val githubUsername: String? = null,
                   val uptUsername: String? = null,
                   val googleUsername: String? = null,
                   val facebookUsername: String? = null)