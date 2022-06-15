package com.example.plugins

import com.example.entry.Msg
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respond(Msg("this is msg", Clock.System.now().toLocalDateTime(TimeZone.UTC).date))
        }
        get("ck") {
            call.response.cookies.append("my_cookie","coderpwh")
            println(call.response.cookies["my_cookie"])
            call.respond(Msg("this is msg", Clock.System.now().toLocalDateTime(TimeZone.UTC).date))
        }
        get("ck/test") {
            call.respond(call.request.cookies.rawCookies)
        }
    }
}
