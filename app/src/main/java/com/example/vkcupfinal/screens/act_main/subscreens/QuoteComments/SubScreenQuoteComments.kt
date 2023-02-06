package com.example.vkcupfinal.screens.act_main.subscreens.QuoteComments

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vkcupfinal.R
import com.example.vkcupfinal.data.ModelQuoteCommentItem
import com.example.vkcupfinal.data.TypeLike
import com.example.vkcupfinal.ui.subviews.*
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupfinal.utils.MockHelper
import com.example.vkcupformats.ui.common.*

@Composable
fun SubScreenQuoteComments(
    items: List<ModelQuoteCommentItem>,
    onLikeClicked: (ModelQuoteCommentItem, TypeLike) -> Unit,
    onInputChanged: (ModelQuoteCommentItem, String) -> Unit,
    onAddReactionClicked: (ModelQuoteCommentItem) -> Unit,
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
                        QuoteCommentItem(
                            item = item,
                            onLikeClicked = { onLikeClicked.invoke(item, it) },
                            onInputChanged = { onInputChanged.invoke(item, it) },
                            onAddReactionClicked = { onAddReactionClicked.invoke(item) }
                        )
                    }
                }
                AppSpacerItem(height = AppTheme.dimens.x3)
            }
        )
    }
}

@Composable
fun QuoteCommentItem(
    item: ModelQuoteCommentItem,
    onLikeClicked: (TypeLike) -> Unit,
    onInputChanged: (String) -> Unit,
    onAddReactionClicked: () -> Unit,
) {
    val shape = RoundedCornerShape(AppTheme.dimens.x3)
    Column(
        modifier = Modifier
            .padding(horizontal = AppTheme.dimens.x1)
            .appShadow()
            .clip(shape)
            .background(AppTheme.color.Surface)
            .padding(vertical = AppTheme.dimens.x4)
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.x4),
            text = stringResource(R.string.quote_comment),
            style = AppTheme.typography.SemiBoldL,
            color = AppTheme.color.TextPrimary
        )
        AppSpacer(height = AppTheme.dimens.x2)
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.x4),
            text = item.text,
            style = AppTheme.typography.RegM,
            color = AppTheme.color.TextPrimary,
            textAlign = TextAlign.Start
        )

        if (item.reaction != null) {
            if (item.isLikeAllowed) {
                AppSpacer(height = AppTheme.dimens.x2)
                LikesRating(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.x4)
                        .fillMaxWidth(),
                    likes = item.likesCount,
                    dislikes = item.dislikeCount,
                    userSelected = item.reaction.like
                )
            }
            if (item.isCommentAllowed && item.reaction.text.isNullOrEmpty().not()) {
                AppSpacer(height = AppTheme.dimens.x2)
                Text(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.x4)
                        .fillMaxWidth(),
                    text = stringResource(R.string.your_comment),
                    style = AppTheme.typography.SemiBoldL.alignStart()
                )
                AppSpacer(height = AppTheme.dimens.x2)
                Text(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.x4)
                        .fillMaxWidth(),
                    text = item.reaction.text ?: "",
                    style = AppTheme.typography.RegM.alignStart(),
                    color = AppTheme.color.Primary
                )
            }
        } else {
            if (item.isLikeAllowed) {
                AppSpacer(height = AppTheme.dimens.x2)
                LikeDislikeSelect(
                    onClicked = onLikeClicked,
                    selected = item.like,
                    colorNotSelected = AppTheme.color.TextSecondary.withAlpha(0.2f),
                    isSelectionEnabled = true
                )
            }
            if (item.isCommentAllowed) {
                AppSpacer(height = AppTheme.dimens.x2)
                AppInput(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.x4)
                        .height(100.dp)
                        .fillMaxWidth(),
                    style = AppTheme.typography.RegL.alignStart(),
                    value = item.inputText,
                    onValueChange = onInputChanged,
                    strokeColor = AppTheme.color.TextSecondary.withAlpha(0.2f),
                    strokeWidth = AppTheme.dimens.x05,
                    cursorColor = AppTheme.color.TextSecondary.withAlpha(0.5f)
                )
            }
            if (item.isCommentAllowed) {
                AppSpacer(height = AppTheme.dimens.x3)
                ButtonPrimary(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.x4)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.add),
                    onClick = onAddReactionClicked
                )
            }
        }
    }
}

//@Preview
//@Composable
//private fun Preview() {
//    QuoteCommentItem(
//        item = MockHelper.getQuoteCommentItems().first(),
//        onLikeClicked = {},
//        onInputChanged = {},
//        onAddReactionClicked = {}
//    )
//}