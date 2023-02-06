package com.example.vkcupfinal.ui.subviews

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vkcupfinal.R
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupformats.ui.common.withAlpha


class BaseProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {
    @Composable
    override fun Content() {
        BaseProgress()
    }
}

@Composable
fun BaseProgress() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.color.Background.withAlpha(0.7f))
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = AppTheme.color.Surface,
                modifier = Modifier.size(52.dp)
            )
            AppSpacer(height = AppTheme.dimens.x3)
            Text(
                style = AppTheme.typography.RegL.copy(color = AppTheme.color.Surface),
                text = stringResource(R.string.loading)
            )
        }
    }
}