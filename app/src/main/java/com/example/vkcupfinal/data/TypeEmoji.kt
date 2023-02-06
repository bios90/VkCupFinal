package com.example.vkcupfinal.data

import com.example.vkcupfinal.R

enum class TypeEmoji {
    LIKE,
    HEART,
    HUG,
    ANGRY,
    LAUGH,
    WOW,
    TEAR
}

fun TypeEmoji.getDrawableResId(): Int = when (this) {
    TypeEmoji.LIKE -> R.drawable.ic_like
    TypeEmoji.HEART -> R.drawable.ic_heart
    TypeEmoji.HUG -> R.drawable.ic_hug
    TypeEmoji.ANGRY -> R.drawable.ic_angry
    TypeEmoji.LAUGH -> R.drawable.ic_laugh
    TypeEmoji.WOW -> R.drawable.ic_wow
    TypeEmoji.TEAR -> R.drawable.ic_tear
}