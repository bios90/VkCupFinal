package com.example.vkcupfinal.data

import com.example.vkcupfinal.R
import com.example.vkcupfinal.base.ext.getStringByApp
import com.example.vkcupfinal.ui.subviews.BottomSheetForText

enum class TypeTab {
    BITES,
    REACTS,
    QUOTES,
}

fun TypeTab.getIconResId(): Int = when (this) {
    TypeTab.BITES -> R.drawable.ic_candy
    TypeTab.REACTS -> R.drawable.ic_camera
    TypeTab.QUOTES -> R.drawable.ic_quote
}

fun TypeTab.getTabTitleResId(): Int = when (this) {
    TypeTab.BITES -> R.string.bites
    TypeTab.REACTS -> R.string.reacts
    TypeTab.QUOTES -> R.string.comments
}

fun TypeTab.getAppbarTitle(): Int = when (this) {
    TypeTab.BITES -> R.string.bites
    TypeTab.REACTS -> R.string.reacts
    TypeTab.QUOTES -> R.string.quote_comments
}

fun TypeTab.getBottomTextArgs() = when (this) {
    TypeTab.BITES -> BottomSheetForText.Args(
        title = getStringByApp(R.string.bites),
        text = getStringByApp(R.string.bites_description)
    )
    TypeTab.QUOTES -> BottomSheetForText.Args(
        title = getStringByApp(R.string.quote_comment),
        text = getStringByApp(R.string.quote_comment_description)
    )
    TypeTab.REACTS -> BottomSheetForText.Args(
        title = getStringByApp(R.string.reacts),
        text = getStringByApp(R.string.reacts_description)
    )
}