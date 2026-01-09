package labs.catmarket.common

import org.testcontainers.containers.GenericContainer


class WireMockContainer : GenericContainer<WireMockContainer>("wiremock/wiremock:3.3.1") {

    init {
        withExposedPorts(8080)
    }

    fun baseUrl(): String = "http://${host}:${firstMappedPort}"
}