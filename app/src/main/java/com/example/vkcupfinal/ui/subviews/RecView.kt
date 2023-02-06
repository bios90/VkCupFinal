package com.example.vkcupfinal.ui.subviews

import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.unit.dp
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupformats.ui.common.AnimatedVisibilityFade

class RecSecondsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {
    var seconds: Int? by mutableStateOf(null)

    @Composable
    override fun Content() {
        RecSeconds(seconds = seconds)
    }
}


@Composable
fun RecSeconds(
    modifier: Modifier = Modifier,
    seconds: Int?,
) {
    AnimatedVisibilityFade(visible = seconds != null) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(12.dp)
                        .background(AppTheme.color.ErrorDark)
                )
                AppSpacerHorizontal(width = AppTheme.dimens.x2)
                Text(
                    style = AppTheme.typography.SemiBoldL,
                    text = "Rec",
                    color = AppTheme.color.Surface
                )
            }
            Text(
                style = AppTheme.typography.RegXxl,
                text = DateUtils.formatElapsedTime(seconds?.toLong() ?: 0),
                color = AppTheme.color.Surface
            )
        }
    }
}

//@Preview
//@Composable
//private fun Preview() {
//    RecSeconds(seconds = 9)
//}