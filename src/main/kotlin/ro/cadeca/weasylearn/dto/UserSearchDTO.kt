package ro.cadeca.weasylearn.dto


data class UserSearchDTO(
        val username: String,
        val firstName: String? = "",
        val lastName: String? = "",
        val email: String? = "",
        val profilePicture: ByteArray? = null) {
    lateinit var title: String
}
