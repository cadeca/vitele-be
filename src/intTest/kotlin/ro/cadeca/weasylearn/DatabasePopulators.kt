package ro.cadeca.weasylearn

import ro.cadeca.weasylearn.persistence.subject.SubjectEntity
import ro.cadeca.weasylearn.persistence.subject.SubjectRepository
import ro.cadeca.weasylearn.persistence.user.UserDocument
import ro.cadeca.weasylearn.persistence.user.UserRepository
import ro.cadeca.weasylearn.persistence.user.UserTypes
import java.util.*

val rioBirthDate: Date = Calendar.getInstance().also { it.set(1985, 1, 1) }.time
val tokyoBirthDate: Date = Calendar.getInstance().also { it.set(1990, 2, 2) }.time
val elProfessorBirthDate: Date = Calendar.getInstance().also { it.set(1987, 4, 20) }.time
val berlinBirthDate: Date = Calendar.getInstance().also { it.set(1982, 10, 9) }.time
val doeBirthDate: Date = Calendar.getInstance().also { it.set(2000, 6, 10) }.time
val snowBirthDate: Date = Calendar.getInstance().also { it.set(1997, 5, 10) }.time

val user1 = UserDocument(username = "rioCity",
        firstName = "Rio",
        lastName = "City",
        dateOfBirth = rioBirthDate,
        email = "rio@gmail.com",
        type = UserTypes.USER
)

val user2 = UserDocument(username = "tokyo",
        firstName = "Tokyo Drift",
        lastName = "City",
        dateOfBirth = tokyoBirthDate,
        email = "tokyo@yahoo.com",
        type = UserTypes.USER
)

val teacher1 = UserDocument(username = "elProfessor",
        firstName = "Professor",
        lastName = "Papel",
        dateOfBirth = elProfessorBirthDate,
        email = "professor.papel@upt.ro",
        type = UserTypes.TEACHER,
        details = mapOf("department" to "CTI",
                "titles" to listOf("prof.", "dr.", "ing."),
                "eduUser" to "professor.papel",
                "githubUser" to "professorPapel"
        )
)

val teacher2 = UserDocument(username = "berlin",
        firstName = "Berlin",
        lastName = "Fonollosa",
        dateOfBirth = berlinBirthDate,
        email = "berlin.fonollosa@aut.upt.ro",
        type = UserTypes.TEACHER,
        details = mapOf("department" to "AIA",
                "titles" to listOf("conf.", "dr.", "ing."),
                "eduUser" to "berlin.fonollosa",
                "githubUser" to "berlinFonollosa"
        )
)

val student1 = UserDocument(username = "johnDoe",
        firstName = "John Albert",
        lastName = "Doe",
        dateOfBirth = doeBirthDate,
        email = "john.doe@student.upt.ro",
        type = UserTypes.STUDENT,
        details = mapOf("studyType" to "Bachelor",
                "year" to 2,
                "group" to "2.1",
                "githubUser" to "john_doe",
                "facebookUser" to "JohnDoe",
                "eduUser" to "john.doe"
        )
)

val student2 = UserDocument(username = "JohnSnow",
        firstName = "John",
        lastName = "Snow",
        dateOfBirth = snowBirthDate,
        email = "john.snow@student.upt.ro",
        type = UserTypes.STUDENT,
        details = mapOf("studyType" to "Master",
                "year" to 1,
                "group" to "2.2",
                "githubUser" to "john_snow",
                "facebookUser" to "JohnSnow",
                "eduUser" to "john.snow"
        )
)

fun populateUsers(userRepository: UserRepository) {
    userRepository.save(user1)
    userRepository.save(user2)
    userRepository.save(teacher1)
    userRepository.save(teacher2)
    userRepository.save(student1)
    userRepository.save(student2)
}


val subject1 = SubjectEntity("subj 1 e2", "code1", "description1", 1)
val subject2 = SubjectEntity("subj2", "code2")
val subject3 = SubjectEntity("subj3 3 e3", "code2", "desc3", 1, teacher1.username, setOf(user1.username, teacher2.username), setOf(student1.username, student2.username))

fun populateSubjects(subjectRepository: SubjectRepository) {
    subjectRepository.save(subject1)
    subjectRepository.save(subject2)
    subjectRepository.save(subject3)
}
