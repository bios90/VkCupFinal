package com.example.vkcupfinal.ui.subviews

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vkcupfinal.R
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupformats.ui.common.appClickable
import com.example.vkcupformats.ui.common.appShadow

class ComposeAppBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {

    var title: String by mutableStateOf("")
    var onCircleQuestionClick: () -> Unit by mutableStateOf({})

    @Composable
    override fun Content() {
        ComposeAppBar(
            title = title,
            onQuestionClick = onCircleQuestionClick
        )
    }
}


@Composable
fun ComposeAppBar(
    title: String,
    onQuestionClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(AppTheme.color.Surface)
            .height(AppTheme.dimens.x14)
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.x4)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            style = AppTheme.typography.SemiBoldXl
        )
        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .size(AppTheme.dimens.x7)
                .appClickable(onClick = onQuestionClick)
                .padding(AppTheme.dimens.x1),
            painter = painterResource(id = R.drawable.ic_circle_question),
            contentDescription = "ic_question",
            colorFilter = ColorFilter.tint(AppTheme.color.TextPrimary)
        )
    }
}