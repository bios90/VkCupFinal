package com.example.vkcupfinal.ui.subviews

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.vkcupfinal.R
import com.example.vkcupfinal.data.TypeLike
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupformats.ui.common.appClickable
import com.example.vkcupformats.ui.common.modifyIf

class LikeDislikeSelectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {

    var selected: TypeLike by mutableStateOf(TypeLike.NOT_SELECTED)
    var size: Dp by mutableStateOf(AppTheme.dimens.x8)
    var colorNotSelected: Color by mutableStateOf(AppTheme.color.Surface)
    var colorLikeSelected: Color by mutableStateOf(AppTheme.color.SuccessDark)
    var colorDislikeSelected: Color by mutableStateOf(AppTheme.color.ErrorDark)
    var isSelectionEnabled: Boolean by mutableStateOf(true)
    var onClicked: (TypeLike) -> Unit by mutableStateOf({})

    @Composable
    override fun Content() {
        LikeDislikeSelect(
            onClicked = { like -> onClicked.invoke(like) },
            selected = selected,
            size = size,
            colorNotSelected = colorNotSelected,
            colorLikeSelected = colorLikeSelected,
            colorDislikeSelected = colorDislikeSelected,
            isSelectionEnabled = isSelectionEnabled
        )
    }
}


@Composable
fun LikeDislikeSelect(
    modifier: Modifier = Modifier,
    onClicked: (TypeLike) -> Unit,
    selected: TypeLike = TypeLike.NOT_SELECTED,
    size: Dp = AppTheme.dimens.x8,
    colorNotSelected: Color = AppTheme.color.Surface,
    colorLikeSelected: Color = AppTheme.color.SuccessDark,
    colorDislikeSelected: Color = AppTheme.color.ErrorDark,
    isSelectionEnabled: Boolean = true
) {
    val (colorLike, colorDislike) = when (selected) {
        TypeLike.LIKE -> Pair(colorLikeSelected, colorNotSelected)
        TypeLike.DISLIKE -> Pair(colorNotSelected, colorDislikeSelected)
        TypeLike.NOT_SELECTED -> Pair(colorNotSelected, colorNotSelected)
    }
    val additionalPadding = size / 12f
    Row(modifier = modifier) {
        Image(
            modifier = Modifier
                .padding(bottom = AppTheme.dimens.x2 + additionalPadding)
                .clip(CircleShape)
                .size(size + AppTheme.dimens.x4)
                .scale(-1f, 1f)
                .modifyIf(
                    condition = isSelectionEnabled,
                    modifier = {
                        this.appClickable(onClick = { onClicked.invoke(TypeLike.LIKE) })
                    }
                )
                .padding(AppTheme.dimens.x2),
            painter = painterResource(id = R.drawable.ic_thumb_up),
            contentDescription = "ic_thumb_up",
            colorFilter = ColorFilter.tint(color = colorLike)
        )
        AppSpacerHorizontal(width = AppTheme.dimens.x1)
        Image(
            modifier = Modifier
                .padding(top = AppTheme.dimens.x2 + additionalPadding)
                .clip(CircleShape)
                .size(size + AppTheme.dimens.x4)
                .modifyIf(
                    condition = isSelectionEnabled,
                    modifier = {
                        this.appClickable(onClick = { onClicked.invoke(TypeLike.DISLIKE) })
                    }
                )
                .padding(AppTheme.dimens.x2),
            painter = painterResource(id = R.drawable.ic_thumb_down),
            contentDescription = "ic_thumb_down",
            colorFilter = ColorFilter.tint(color = colorDislike)
        )
    }
}
//
//@Preview
//@Composable
//private fun Preview() {
//    LikeDislikeSelect(onClicked = {})
//}