package com.example.vkcupfinal.data

import androidx.compose.ui.graphics.Color
import com.example.vkcupfinal.ui.theme.AppTheme

enum class TypeLike {
    LIKE,
    DISLIKE,
    NOT_SELECTED
}

fun TypeLike.getColor(): Color = when (this) {
    TypeLike.LIKE -> AppTheme.color.SuccessDark
    TypeLike.DISLIKE -> AppTheme.color.ErrorDark
    TypeLike.NOT_SELECTED -> AppTheme.color.Transparent
}