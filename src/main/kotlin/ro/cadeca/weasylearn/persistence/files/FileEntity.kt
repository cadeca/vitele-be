package ro.cadeca.weasylearn.persistence.files

import ro.cadeca.weasylearn.persistence.BaseEntity
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class FileEntity(
        @Column
        var name: String,
        @Column
        var contentType: String?,
        @Lob
        @Column
        var content: ByteArray,
        @Column
        var size: Long
) : BaseEntity()
