package com.example.utils

/**
 * @author coderpwh
 * @date 2022-06-20 11:05
 * @version 1.0.0 v
 */
object ByteUtils {
    fun byteToInt(bytes: ByteArray): Int {
        return bytes[0].toInt() and 0xff shl 24 or (bytes[1].toInt() and 0xff shl 16
                ) or (bytes[2].toInt() and 0xff shl 8
                ) or (bytes[3].toInt() and 0xff)
    }

    fun intToByte(num: Int): ByteArray {
        val byteArray = ByteArray(4)
        byteArray[0] = num.shr(24).and(0xff).toByte()
        byteArray[1] = num.shr(16).and(0xff).toByte()
        byteArray[2] = num.shr(8).and(0xff).toByte()
        byteArray[3] = num.and(0xff).toByte()
        return byteArray
    }
}