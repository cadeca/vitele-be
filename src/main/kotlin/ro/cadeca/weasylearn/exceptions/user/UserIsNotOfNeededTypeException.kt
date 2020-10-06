package ro.cadeca.weasylearn.exceptions.user

class UserIsNotOfNeededTypeException(username: String, type: String) : RuntimeException("User $username is not a $type!") {
}
