package com.example

import com.example.entry.SessionData
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import java.util.Scanner

fun main(args: Array<String>) {
    if (args.size!=0) {
        SessionData.sessionData = args[0]
    }
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSession()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
