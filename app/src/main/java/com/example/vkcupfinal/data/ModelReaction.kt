package com.example.vkcupfinal.data

import java.io.Serializable

data class ModelReaction(
    val id: Long,
    val video: ObjectWithVideoUrl,
    val preview: ImagePreview?,
    val userName: String,
    val like: TypeLike,
    val emoji: TypeEmoji?,
    val isSelfReaction: Boolean = false
) : Serializable

data class ImagePreview(
    val resId: Int?,
    val url: String?
) : Serializable