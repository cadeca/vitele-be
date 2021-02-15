package ro.cadeca.weasylearn.annotations.validation

import javax.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidSubject(
        val message: String = "Invalid subject!",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
)
