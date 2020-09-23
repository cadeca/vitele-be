package ro.cadeca.weasylearn

import org.testcontainers.containers.PostgreSQLContainer

class MyPostgresContainer : PostgreSQLContainer<MyPostgresContainer>() {

    override fun start() {
        super.start()
        System.setProperty("POSTGRES_URL", jdbcUrl)
        System.setProperty("POSTGRES_USERNAME", username)
        System.setProperty("POSTGRES_PASSWORD", password)
    }
}
