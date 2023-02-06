package com.example.vkcupfinal.ui.common

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.vkcupfinal.ui.theme.AppTheme

class AppRippleTheme(
    private val color: Color = AppTheme.color.Accent,
) : RippleTheme {
    @Composable
    override fun defaultColor(): Color = color

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleAlpha(
            0.2f,
            0.2f,
            0.2f,
            0.2f,
        )
}
