package ro.cadeca.weasylearn.model

import java.util.*

class Student(
        username: String,
        firstName: String? = null,
        lastName: String? = null,
        dateOfBirth: Date? = null,
        profilePicture: ByteArray? = null,
        email: String? = null,
        var studyType: String? = null,
        var year: Int? = null,
        var group: String? = null,
        var githubUser: String? = null,
        var facebookUser: String? = null,
        var eduUser: String? = null
) : User(username, firstName, lastName, dateOfBirth, profilePicture, email)
