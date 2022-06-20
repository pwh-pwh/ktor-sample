package com.example.plugins

import com.example.client.ClientObj
import com.example.client.MyClient
import com.example.entry.Msg
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.http.content.*
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
        post("upload") {
            var partData = call.receiveMultipart()
            partData.forEachPart {
                part ->
                if (part.name=="file") {
                    var bytes = (part as PartData.FileItem).streamProvider().readBytes()
                    var rsp = ClientObj.client.uploadOriginFile(bytes)
                    call.respond(rsp)
                }
            }
            call.respond(HttpStatusCode.BadRequest,"check your parameter")
        }
        get("data") {
            var url = call.request.queryParameters["url"]
            if (url!=null) {
                call.respondBytes(ClientObj.client.getOriginFile(url),ContentType.Any)
            }
            call.respond(HttpStatusCode.BadRequest,"check your url")
        }
    }
}
