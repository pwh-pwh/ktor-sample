package com.example.plugins

import com.example.entry.UserSession
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

/**
 * @author coderpwh
 * @date 2022-06-15 13:49
 * @version 1.0.0 v
 */
fun Application.configureSession() {
    install(Sessions) {
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60*10
        }
    }
    routing {
        get("/login") {
            call.sessions.set(UserSession(id = "123abc", count = 0))
            call.respondRedirect("/user")
        }
        get("/user") {
            val userSession = call.sessions.get<UserSession>()
            if (userSession != null) {
                call.sessions.set(userSession.copy(count = userSession.count + 1))
                call.respondText("Session ID is ${userSession.id}. Reload count is ${userSession.count}.")
            } else {
                call.respondText("Session doesn't exist or is expired.")
            }
        }
        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/user")
        }
    }

}