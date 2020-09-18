package ro.cadeca.weasylearn.model

import java.util.*

class Teacher (
        userName: String,
        firstName: String? = null,
        lastName: String? = null,
        dateOfBirth: Date? = null,
        profilePicture: ByteArray? = null,
        email: String? = null,
        var department: String? = null,
        var titles: List<String>? = null,
        var eduUser: String? = null,
        var githubUser: String? = null
) : User(userName, firstName, lastName, dateOfBirth, profilePicture, email)
