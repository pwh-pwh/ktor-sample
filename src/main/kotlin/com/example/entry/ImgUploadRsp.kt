package com.example.entry
@kotlinx.serialization.Serializable
data class ImgUploadRsp(
    val code: Int,
    val data: Data,
    val message: String
)