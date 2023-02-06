package com.example.vkcupfinal.ui.subviews

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.vkcupfinal.R
import com.example.vkcupfinal.data.TypeEmoji
import com.example.vkcupfinal.data.TypeLike
import com.example.vkcupfinal.data.getDrawableResId
import com.example.vkcupfinal.ui.theme.AppTheme

class ReactionBottomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {
    var name: String by mutableStateOf("")
    var selectedLike: TypeLike by mutableStateOf(TypeLike.LIKE)
    var selectedEmoji: TypeEmoji? by mutableStateOf(null)

    @Composable
    override fun Content() {
        ReactionBottom(name = name, selectedLike = selectedLike, selectedEmoji = selectedEmoji)
    }
}

@Composable
fun ReactionBottom(
    modifier: Modifier = Modifier,
    name: String,
    selectedLike: TypeLike,
    selectedEmoji: TypeEmoji?
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = AppTheme.dimens.x4),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.user_collon),
                style = AppTheme.typography.RegS,
                color = AppTheme.color.Surface
            )
            Text(
                text = name,
                style = AppTheme.typography.SemiBoldM,
                color = AppTheme.color.Surface
            )
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            LikeDislikeSelect(
                onClicked = {},
                selected = selectedLike,
                isSelectionEnabled = false
            )
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (selectedEmoji != null) {
                Image(
                    modifier = Modifier.size(AppTheme.dimens.x8),
                    painter = painterResource(id = selectedEmoji.getDrawableResId()),
                    contentDescription = "img_selected_emoji"
                )
            }
        }
    }
}

//@Preview
//@Composable
//private fun Preview() {
//    ReactionBottom(name = "Vasya", selectedLike = TypeLike.LIKE, selectedEmoji = TypeEmoji.HUG)
//}