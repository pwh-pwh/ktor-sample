package com.example.storage

import io.ktor.client.plugins.cookies.*
import io.ktor.http.*
import io.ktor.util.date.*
import kotlinx.coroutines.sync.withLock

/**
 * @author coderpwh
 * @date 2022-06-15 15:40
 * @version 1.0.0 v
 */
class AcceptDomainCookiesStorage(private val cookiesStorage: AcceptAllCookiesStorage):CookiesStorage by cookiesStorage{

}