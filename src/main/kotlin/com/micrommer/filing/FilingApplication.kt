package com.micrommer.filing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FilingApplication

fun main(args: Array<String>) {
	runApplication<FilingApplication>(*args)
}
