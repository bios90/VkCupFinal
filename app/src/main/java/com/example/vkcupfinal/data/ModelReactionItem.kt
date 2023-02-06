package com.example.vkcupfinal.data

data class ModelReactionItem(
    val id: Long,
    val reactions: List<ModelReaction>,
    val userReaction: ModelReaction?,
    val likesCount: Int,
    val dislikeCount: Int
)