package ro.cadeca.weasylearn.validators

import ro.cadeca.weasylearn.annotations.validation.ValidSubject
import ro.cadeca.weasylearn.dto.subjects.SubjectSaveDTO
import ro.cadeca.weasylearn.persistence.subject.SubjectEntity
import ro.cadeca.weasylearn.persistence.user.UserTypes
import ro.cadeca.weasylearn.services.UserService
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class SubjectValidator(
        private val userService: UserService
): ConstraintValidator<ValidSubject, SubjectSaveDTO> {

    override fun isValid(value: SubjectSaveDTO?, context: ConstraintValidatorContext?): Boolean {
        if (context != null && value != null) {
            return validateSubject(value, context)
        }
        return true
    }

    private fun validateSubject(value: SubjectSaveDTO, context: ConstraintValidatorContext): Boolean {
        return validateTeacher(value.teacher, context) &&
               validateUsersExist(value.students, context) &&
               validateUsersExist(value.tutors, context)
    }

    private fun validateTeacher(teacher: String?, context: ConstraintValidatorContext): Boolean {
        if (teacher == null) {
            return true
        }
        if (!userService.exists(teacher)) {
            context.buildConstraintViolationWithTemplate("The given teacher is not an existent user!")
                   .addPropertyNode("subject")
                   .addConstraintViolation()
            return false
        }
        if (!userService.isType(teacher, UserTypes.TEACHER)) {
            context.buildConstraintViolationWithTemplate("The given user is not a teacher!")
                   .addPropertyNode("subject")
                   .addConstraintViolation()
            return false
        }
        return true
    }

    private fun validateUsersExist(users: List<String>?, context: ConstraintValidatorContext): Boolean {
        if (users == null) {
            return true
        }
        users.forEach {
            if (!userService.exists(it)) {
                context.buildConstraintViolationWithTemplate("The student with the username: $it doesn't exist!")
                       .addPropertyNode("subject")
                       .addConstraintViolation()
                return@validateUsersExist false
            }
        }
        return true
    }

}