package ro.cadeca.weasylearn.persistence

import org.springframework.data.annotation.Id

open class BaseDocument {

    @Id
    var id: String? = null
}
