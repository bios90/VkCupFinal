package com.example.vkcupfinal.ui.subviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.vkcupfinal.R
import com.example.vkcupfinal.base.ext.randomInt
import com.example.vkcupfinal.data.*
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupfinal.utils.getVideoSourceFromUri
import com.example.vkcupformats.ui.common.appClickable
import com.example.vkcupformats.ui.common.appShadow
import com.example.vkcupformats.ui.common.emptyPainter
import com.example.vkcupformats.ui.common.modifyIf
import com.imherrera.videoplayer.ResizeMode
import com.imherrera.videoplayer.VideoPlayer
import com.imherrera.videoplayer.rememberVideoPlayerState


@Composable
fun UserReactionCircle(
    modifier: Modifier = Modifier,
    reaction: ModelReaction,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Box(
        modifier = modifier,
    ) {
        val circleSize = 128.dp
        val emojiSize = AppTheme.dimens.x8
        val emojiPadding = circleSize - (emojiSize / 2f)
        Box(
            modifier = Modifier
                .padding(horizontal = emojiSize / 2f)
                .size(circleSize)
                .clip(CircleShape)
                .modifyIf(condition = reaction.like != TypeLike.NOT_SELECTED,
                    modifier = {
                        border(
                            width = AppTheme.dimens.x1,
                            color = reaction.like.getColor(),
                            shape = CircleShape
                        )
                    }
                )
        ) {
            val playerState = rememberVideoPlayerState()
            VideoPlayer(
                modifier = Modifier
                    .fillMaxSize()
                    .appClickable(onClick = onClick),
                playerState = playerState,
                controller = {}
            )
            LaunchedEffect(Unit) {
                val videoUrl = reaction.video.videoUrl ?: return@LaunchedEffect
                playerState.player.setMediaSource(getVideoSourceFromUri(videoUrl))
                playerState.player.prepare()
                playerState.player.playWhenReady = true
                playerState.player.volume = 0f
                playerState.control.setVideoResize(ResizeMode.Zoom)
            }
        }

        Box(
            modifier = Modifier
                .width(circleSize + emojiSize)
                .height(circleSize),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                modifier = Modifier
                    .appShadow(emojiSize / 2)
                    .clip(CircleShape)
                    .size(emojiSize)
                    .background(AppTheme.color.Surface)
                    .appClickable(onClick = onRemoveClick)
                    .padding(3.dp),
                painter = painterResource(id = R.drawable.ic_times),
                colorFilter = ColorFilter.tint(AppTheme.color.ErrorDark),
                contentDescription = "ic_times"
            )
        }

        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = emojiPadding)
                .size(emojiSize),
            painter = reaction.emoji
                ?.getDrawableResId()
                ?.let { painterResource(id = it) }
                ?: emptyPainter,
            contentDescription = "img_emoji"
        )
    }
}

@Composable
fun ReactionCircle(
    reaction: ModelReaction,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .size(AppTheme.dimens.x16)
                .clip(CircleShape)
                .modifyIf(condition = reaction.like != TypeLike.NOT_SELECTED,
                    modifier = {
                        border(
                            width = AppTheme.dimens.x05,
                            color = reaction.like.getColor(),
                            shape = CircleShape
                        )
                    }
                )
        ) {
            val painter = rememberImagePainter(
                data = reaction.preview?.resId ?: reaction.preview?.url?.let { "file://$it" } ?: reaction.video.videoUrl,
            )
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .appClickable(onClick = onClick),
                contentScale = ContentScale.Crop,
                painter = painter,
                contentDescription = "img_reaction_circle"
            )
        }
        val emojiSize = AppTheme.dimens.x6
        val emojiPadding = AppTheme.dimens.x16 - (emojiSize / 2f)
        Image(
            modifier = Modifier
                .padding(top = emojiPadding)
                .size(emojiSize),
            painter = reaction.emoji
                ?.getDrawableResId()
                ?.let { painterResource(id = it) }
                ?: emptyPainter,
            contentDescription = "img_emoji"
        )
    }
}

private val reactionMock = ModelReaction(
    id = randomInt.toLong(),
    preview = null,
    video = object : ObjectWithVideoUrl {
        override val videoUrl: String =
            "https://images.unsplash.com/photo-1611915387288-fd8d2f5f928b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2526&q=80"
    },
    userName = "Vasya",
    like = TypeLike.LIKE,
    emoji = TypeEmoji.HUG
)

//@Preview(showBackground = true)
//@Composable
//private fun PreviewReactionCircle() {
//    ReactionCircle(
//        reaction = reactionMock,
//        onClick = {}
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun PreviewUserReactionCircle() {
//    UserReactionCircle(
//        reaction = reactionMock,
//        onClick = { },
//        onRemoveClick = { }
//    )
//}