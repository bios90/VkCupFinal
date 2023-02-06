package com.example.vkcupfinal.data

import java.io.Serializable

interface ObjectWithId {
    val id: Long?
}

interface ObjectWithImageUrl : Serializable {
    val imageUrl: String?

    companion object {
        fun fromString(str: String): ObjectWithImageUrl =
            object : ObjectWithImageUrl {
                override val imageUrl: String = str
            }
    }
}

interface ObjectWithVideoUrl : Serializable {
    val videoUrl: String?

    companion object {
        fun fromString(str: String): ObjectWithVideoUrl =
            object : ObjectWithVideoUrl {
                override val videoUrl: String = str
            }
    }
}
