package com.example.vkcupfinal.ui.subviews

import android.content.Context
import android.util.AttributeSet
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.vkcupfinal.data.TypeEmoji
import com.example.vkcupfinal.data.getDrawableResId
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupformats.ui.common.appClickable

class EmojiSelectorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {
    var size: Dp by mutableStateOf(AppTheme.dimens.x8)
    var selected: TypeEmoji? by mutableStateOf(null)
    var onEmojiClicked: (TypeEmoji) -> Unit by mutableStateOf({})

    @Composable
    override fun Content() {
        EmojiSelector(
            size = size,
            selected = selected,
            onEmojiClicked = { emoji ->
                onEmojiClicked.invoke(emoji)
            }
        )
    }
}

@Composable
fun EmojiSelector(
    modifier: Modifier = Modifier,
    size: Dp = AppTheme.dimens.x8,
    selected: TypeEmoji? = null,
    onEmojiClicked: (TypeEmoji) -> Unit
) {
    val animationScale = 1.25f
    val padding = (size * animationScale) / 2f
    val emojiAnimationsProgress = (0..TypeEmoji.values().size).map {
        remember {
            Animatable(1f)
        }
    }
    var selectedEmoji: TypeEmoji? by remember {
        mutableStateOf(null)
    }
    selectedEmoji = selected

    LaunchedEffect(key1 = selectedEmoji, block = {
        for ((index, emoji) in TypeEmoji.values().withIndex()) {
            val animation = emojiAnimationsProgress.get(index)
            if (selectedEmoji == emoji) {
                animation.snapTo(1f)
                animation.animateTo(
                    targetValue = animationScale,
                    animationSpec = repeatable(
                        iterations = Int.MAX_VALUE,
                        repeatMode = RepeatMode.Reverse,
                        animation = tween(
                            1000,
                            easing = LinearEasing
                        )
                    )
                )
            } else {
                animation.snapTo(1f)
            }
        }
    })
    Row(
        modifier = modifier.padding(padding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for ((index, emoji) in TypeEmoji.values().withIndex()) {

            val scale = if (emoji == selectedEmoji) {
                emojiAnimationsProgress.get(index).value
            } else {
                1f
            }
            Image(
                modifier = Modifier
                    .scale(scale)
                    .size(size)
                    .appClickable(
                        withRipple = false,
                        onClick = { onEmojiClicked.invoke(emoji) }
                    ),
                painter = painterResource(id = emoji.getDrawableResId()),
                contentDescription = "img_emoji_${emoji.name}"
            )
        }
    }
}

//@Preview
//@Composable
//private fun Preview() {
//    EmojiSelector(
//        modifier = Modifier.width(320.dp),
//        onEmojiClicked = {}
//    )
//}