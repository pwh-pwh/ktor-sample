package com.example.client

import com.example.entry.ImgUploadRsp
import com.example.utils.PngUtils
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.UUID

/**
 * @author coderpwh
 * @date 2022-06-20 11:22
 * @version 1.0.0 v
 */
class MyClient {
    private val client by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    contentType = ContentType.Text.Any
                )

            }
            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }
            install(Logging) {
                level = LogLevel.ALL
            }


            BrowserUserAgent()

        }
    }

    fun uploadImgFile(fileName:String):ImgUploadRsp = runBlocking {
        var resp = client.post("https://api.vc.bilibili.com/api/v1/drawImage/upload") {
            headers {
                append("Origin","https://t.bilibili.com")
                append("Referer","https://t.bilibili.com")
                append("Cookie","SESSDATA=***")
            }
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("file_up", File(fileName).readBytes(),Headers.build {
                            append(HttpHeaders.ContentType, "application/octet-stream")
                            append(HttpHeaders.ContentDisposition, """filename="$fileName"""")
                        })
                        append("biz","draw")
                        append("category","daily")
                    }
                )
            )
        }
        return@runBlocking resp.body()
    }
    fun uploadOriginFile(data:ByteArray):ImgUploadRsp {
        val fileName = "uporfile"+".png"
        PngUtils.writeImg(data,fileName)
        return uploadImgFile(fileName)
    }

    fun getOriginFile(url:String):ByteArray =
        runBlocking {
            var data:ByteArray = client.get(url).body()
            val fileName = "temp.png"
            File(fileName).writeBytes(data)
            return@runBlocking PngUtils.readDataFromPng(fileName,null)
    }


}

object ClientObj {
    val client = MyClient()
}
