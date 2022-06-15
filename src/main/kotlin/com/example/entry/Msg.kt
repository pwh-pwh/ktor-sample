package com.example.entry

import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateComponentSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @author coderpwh
 * @date 2022-06-15 11:39
 * @version 1.0.0 v
 */
@Serializable
data class Msg(var msg:String,
               @Serializable(with = LocalDateComponentSerializer::class)
               var dateTime: LocalDate
)

fun main() {
    var msg = Msg("coderpwh", LocalDate(2022, 11, 20))
    var str = Json.encodeToString(msg)
    println(str)
}
