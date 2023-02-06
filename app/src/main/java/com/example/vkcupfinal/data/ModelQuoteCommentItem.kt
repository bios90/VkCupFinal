package com.example.vkcupfinal.data

data class ModelQuoteCommentItem(
    val id: Long,
    val text: String,
    val inputText: String = "",
    val likesCount: Int,
    val dislikeCount: Int,
    val like: TypeLike = TypeLike.NOT_SELECTED,
    val isCommentAllowed: Boolean = true,
    val isLikeAllowed: Boolean = true,
    val reaction: ModelQuoteCommentReaction? = null
)

data class ModelQuoteCommentReaction(
    val like: TypeLike,
    val text: String?
)