package ro.cadeca.weasylearn.persistence.user

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Long>