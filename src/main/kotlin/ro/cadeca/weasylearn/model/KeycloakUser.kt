package ro.cadeca.weasylearn.model

class KeycloakUser(val username: String,
                   val fullName: String? = null,
                   val firstName: String? = null,
                   val lastName: String? = null,
                   val email: String? = null,
                   var roles: List<String>? = emptyList(),
                   val githubUsername: String? = null,
                   val uptUsername: String? = null,
                   val googleUsername: String? = null,
                   val facebookUsername: String? = null)
