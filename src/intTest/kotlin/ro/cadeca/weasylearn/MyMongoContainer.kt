package ro.cadeca.weasylearn

import org.testcontainers.containers.MongoDBContainer

class MyMongoContainer : MongoDBContainer() {

    override fun start() {
        super.start()
        System.setProperty("MONGO_PORT", firstMappedPort.toString())
        System.setProperty("MONGO_HOST", host)
    }

    override fun stop() {
        super.stop()
    }
}
