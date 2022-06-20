package com.example.entry
@kotlinx.serialization.Serializable
data class Data(
    val image_height: Int,
    val image_url: String,
    val image_width: Int
)