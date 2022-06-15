package com.example.plugins;

import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import io.ktor.util.*
import kotlin.test.Test

class RoutingKtTest {


    @Test
    fun testGet() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            println(this.bodyAsText())
        }
    }
}