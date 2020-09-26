package ro.cadeca.weasylearn.exceptions.user

class UserNotFoundException(username: String) : RuntimeException("User with username: $username does not exist!")
