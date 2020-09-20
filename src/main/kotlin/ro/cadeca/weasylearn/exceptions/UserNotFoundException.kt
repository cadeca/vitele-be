package ro.cadeca.weasylearn.exceptions

class UserNotFoundException(username: String) : RuntimeException("User with username: $username does not exist!")
