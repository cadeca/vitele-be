package ro.cadeca.weasylearn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WLBeApplication

fun main(args: Array<String>) {
	runApplication<WLBeApplication>(*args)
}
