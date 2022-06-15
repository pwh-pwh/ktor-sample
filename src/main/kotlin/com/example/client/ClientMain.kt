package com.example.client

import com.example.entry.Msg
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*


/**
 * @author coderpwh
 * @date 2022-06-15 14:31
 * @version 1.0.0 v
 */

suspend fun main() {
    var client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    var resp = client.get("http://localhost:8080/ck")
    client.get("http://localhost:8080/")
}