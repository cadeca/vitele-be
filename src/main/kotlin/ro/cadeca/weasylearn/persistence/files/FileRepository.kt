package ro.cadeca.weasylearn.persistence.files

import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository : JpaRepository<FileEntity, Long>
