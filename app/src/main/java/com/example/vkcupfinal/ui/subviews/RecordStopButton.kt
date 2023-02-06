package com.example.vkcupfinal.ui.subviews

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.example.vkcupformats.ui.common.appClickable

class RecordStopButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {
    var isRecording: Boolean by mutableStateOf(false)
    var onClick: () -> Unit by mutableStateOf({})

    @Composable
    override fun Content() {
        RecordStopButton(
            isRecording = isRecording,
            onClick = onClick
        )
    }
}

@Composable
fun RecordStopButton(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    onClick: () -> Unit
) {
    val color = if (isRecording) {
        AppTheme.color.Surface
    } else {
        AppTheme.color.ErrorDark
    }
    Box(
        modifier = modifier
            .clip(CircleShape)
            .appClickable(onClick = onClick)
            .border(
                width = AppTheme.dimens.x2,
                shape = CircleShape,
                color = color
            )
            .padding(AppTheme.dimens.x4),
        contentAlignment = Alignment.Center
    ) {
        if (isRecording) {
            Box(
                modifier = Modifier
                    .padding(AppTheme.dimens.x3)
                    .fillMaxSize()
                    .background(color)
            )
        } else {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxSize()
                    .background(color)
            )
        }
    }
}

//@Preview
//@Composable
//private fun PreviewRecording() {
//    RecordStopButton(
//        modifier = Modifier.size(100.dp),
//        isRecording = true,
//        onClick = {}
//    )
//}
//
//@Preview
//@Composable
//private fun PreviewNotRecording() {
//    RecordStopButton(
//        modifier = Modifier.size(100.dp),
//        isRecording = false,
//        onClick = {}
//    )
//}