package ro.cadeca.weasylearn.model

import java.util.*

class Teacher(
        username: String,
        firstName: String? = null,
        lastName: String? = null,
        dateOfBirth: Date? = null,
        profilePicture: Long? = null,
        email: String? = null,
        var department: String? = null,
        var titles: List<String>? = null,
        var eduUser: String? = null,
        var githubUser: String? = null
) : User(username, firstName, lastName, dateOfBirth, profilePicture, email)
