package com.example.vkcupfinal.screens.act_main.subscreens.Reactions

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.vkcupfinal.R
import com.example.vkcupfinal.data.ModelReaction
import com.example.vkcupfinal.data.ModelReactionItem
import com.example.vkcupfinal.data.TypeLike
import com.example.vkcupfinal.ui.subviews.*
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupfinal.utils.MockHelper
import com.example.vkcupformats.ui.common.appShadow
import com.example.vkcupformats.ui.common.isScrolledToEnd

@Composable
fun SubScreenReactions(
    items: List<ModelReactionItem>,
    onReactionClicked: (ModelReaction, ModelReactionItem) -> Unit,
    onRemoveUserReactionClicked: (ModelReactionItem) -> Unit,
    onAddReactionClicked: (ModelReactionItem) -> Unit,
    onScrolledToBottom: () -> Unit,
) {
    val scrollState = rememberLazyListState()
    val endOfListReached by remember {
        derivedStateOf {
            scrollState.isScrolledToEnd()
        }
    }
    LaunchedEffect(endOfListReached) {
        if (endOfListReached) {
            onScrolledToBottom.invoke()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState,
            content = {
                for (item in items) {
                    AppSpacerItem(height = AppTheme.dimens.x3)
                    item(
                        key = item.id
                    ) {
                        ReactionItem(
                            reactionItem = item,
                            onReactionClicked = { onReactionClicked.invoke(it, item) },
                            onAddReactionClicked = { onAddReactionClicked.invoke(item) },
                            onRemoveUserReactionClicked = { onRemoveUserReactionClicked.invoke(item) }
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun ReactionItem(
    reactionItem: ModelReactionItem,
    onReactionClicked: (ModelReaction) -> Unit,
    onRemoveUserReactionClicked: () -> Unit,
    onAddReactionClicked: () -> Unit
) {
    val shape = RoundedCornerShape(AppTheme.dimens.x3)
    Column(
        modifier = Modifier
            .appShadow()
            .clip(shape)
            .background(AppTheme.color.Surface)
            .padding(vertical = AppTheme.dimens.x4)
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(R.string.react),
            style = AppTheme.typography.SemiBoldL,
            color = AppTheme.color.TextPrimary
        )
        AppSpacer(height = AppTheme.dimens.x2)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            content = {
                AppSpacerItemHorizontal(width = AppTheme.dimens.x3)
                for (reaction in reactionItem.reactions) {
                    AppSpacerItemHorizontal(width = AppTheme.dimens.x1)
                    item(
                        key = reaction.id,
                        content = {
                            ReactionCircle(
                                reaction = reaction,
                                onClick = { onReactionClicked.invoke(reaction) }
                            )
                        }
                    )
                }
            }
        )

        AppSpacer(height = AppTheme.dimens.x4)
        LikesRating(
            modifier = Modifier.padding(horizontal = AppTheme.dimens.x4),
            likes = reactionItem.likesCount,
            dislikes = reactionItem.dislikeCount,
            userSelected = reactionItem.userReaction?.like ?: TypeLike.NOT_SELECTED
        )
        if (reactionItem.userReaction == null) {
            AppSpacer(height = AppTheme.dimens.x4)
            ButtonPrimary(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimens.x4)
                    .fillMaxSize(),
                text = stringResource(R.string.add),
                onClick = onAddReactionClicked
            )
        } else {
            AppSpacer(height = AppTheme.dimens.x4)
            UserReactionCircle(
                reaction = reactionItem.userReaction,
                onClick = { onReactionClicked.invoke(reactionItem.userReaction) },
                onRemoveClick = { onRemoveUserReactionClicked.invoke() }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    SubScreenReactions(
//        items = MockHelper.getReactionItems(),
//        onReactionClicked = { _, _ -> },
//        onRemoveUserReactionClicked = {},
//        onAddReactionClicked = {},
//        onScrolledToBottom = {},
//    )
//}