package com.example.vkcupfinal.ui.subviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.vkcupfinal.base.ext.px2dp
import com.example.vkcupfinal.data.TypeLike
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupformats.ui.common.withAlpha

@Composable
fun LikesRating(
    modifier: Modifier = Modifier,
    likes: Int,
    dislikes: Int,
    userSelected: TypeLike
) {
    var ratingSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    Column(modifier = modifier) {
        Box {
            val ratio = likes.toFloat() / (likes + dislikes).toFloat()
            val size = px2dp(ratingSize.width * ratio).toInt()
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(AppTheme.dimens.x05))
                    .background(AppTheme.color.ErrorDark)
                    .fillMaxWidth()
                    .height(AppTheme.dimens.x1)
                    .onSizeChanged { ratingSize = it },
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(AppTheme.dimens.x05))
                    .background(AppTheme.color.SuccessDark)
                    .width(size.dp)
                    .height(AppTheme.dimens.x1)
            )
        }
    }
    AppSpacer(height = AppTheme.dimens.x3)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = AppTheme.dimens.x2),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = likes.toString(),
                style = AppTheme.typography.SemiBoldXl,
                color = AppTheme.color.SuccessDark
            )
        }
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            LikeDislikeSelect(
                size = AppTheme.dimens.x8,
                selected = userSelected,
                isSelectionEnabled = false,
                colorNotSelected = AppTheme.color.TextSecondary.withAlpha(0.2f),
                onClicked = {}
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = AppTheme.dimens.x2),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = dislikes.toString(),
                style = AppTheme.typography.SemiBoldXl,
                color = AppTheme.color.ErrorDark
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    LikesRating(
//        likes = 150,
//        dislikes = 200,
//        userSelected = TypeLike.LIKE
//    )
//}