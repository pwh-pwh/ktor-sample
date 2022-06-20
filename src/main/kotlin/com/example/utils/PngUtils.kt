package com.example.utils

import com.example.utils.ByteUtils.byteToInt
import com.example.utils.ByteUtils.intToByte
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * @author coderpwh
 * @date 2022-06-20 11:06
 * @version 1.0.0 v
 */
object PngUtils {
    const val MAX = 4096.0
    //获取图片合适的宽高
    fun getSuitableWH(size: Int): Pair<Int, Int> {
        val beg = Math.ceil(size / MAX).toInt()
        val end = Math.ceil(Math.sqrt(size.toDouble())).toInt()
        var minSize = Int.MAX_VALUE
        var bestH = 0
        var bestW = 0
        for (h in beg..end) {
            var w = Math.ceil(size.toDouble() / h).toInt()
            var tSize = w * h
            if (tSize < minSize) {
                minSize = tSize
                bestW = w
                bestH = h
            }
            if (size == tSize) {
                break
            }
        }
        if(bestW<10||bestH<10) {
            return 10 to 10
        }
        return bestH to bestW
    }
    fun appendLen2Bytes(bytes: ByteArray): ByteArray {
        var size = bytes.size
        return byteArrayOf(0, 0, *intToByte(size), *bytes)
    }
    fun writeByteToPng(bytes: ByteArray, fileName: String) {
        ///每个像素储存3个字节
        // int	4字节	4*8
        var (h, w) = getSuitableWH(
            Math.ceil(bytes.size.toDouble() / 3).toInt()
        )
        var startIndex = 0
        var iterator = generateSequence {
            if (startIndex < bytes.size) {
                var r = byteToInt(
                    byteArrayOf(
                        0,
                        bytes.getOrElse(startIndex) {
                            0
                        },
                        bytes.getOrElse(startIndex + 1) {
                            0
                        },
                        bytes.getOrElse(startIndex + 2) {
                            0
                        }
                    )
                )
                startIndex += 3
                r
            } else {
                null
            }
        }.iterator()
        var bufferedImage = BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
        for (i in 0 until h) {
            for (j in 0 until w) {
                if (iterator.hasNext()) {
                    var next = iterator.next()
                    bufferedImage.setRGB(j, i, next)
                } else {
                    bufferedImage.setRGB(j, i, 0)
                }
            }
        }
        ImageIO.write(bufferedImage, "PNG", File(fileName))
    }
    fun readDataFromPng(fileName: String, outFile: File?): ByteArray {
        val image = ImageIO.read(File(fileName))
        val height = image.height
        val width = image.width
        var r1 = image.getRGB(0, 0)
        var r2 = image.getRGB(1, 0)
        var bytes = intToByte(r2)
        var b1 = intToByte(r1)
        bytes[0] = b1[0]
        var size = byteToInt(bytes)
        var bArray = ByteArray(size)
        var t = 0
        for (i in 0 until height) {
            for (j in 0 until width) {
                if (i == 0 && (j == 1 || j == 0)) {
                    continue
                }
                var rgb = image.getRGB(j, i)
                if (t + 3 <= size) {
                    var bt = intToByte(rgb)
                    bArray[t++] = bt[1]
                    bArray[t++] = bt[2]
                    bArray[t++] = bt[3]
                } else {
                    var bt = intToByte(rgb)
                    if (t + 1 == size) {
                        bArray[t++] = bt[1]
                    } else if (t + 2 == size) {
                        bArray[t++] = bt[1]
                        bArray[t++] = bt[2]
                    }
                    break
                }
            }
        }
        outFile?.let {
            outFile.writeBytes(bArray)
        }
        return bArray
    }
    fun writeImg(bytes:ByteArray,fileName:String):ByteArray {
        writeByteToPng(appendLen2Bytes(bytes), fileName)
        return File(fileName).readBytes()
    }
}