package ro.cadeca.vitele

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ViteleBeApplication

fun main(args: Array<String>) {
	runApplication<ViteleBeApplication>(*args)
}
