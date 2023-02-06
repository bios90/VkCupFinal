package com.example.vkcupfinal.screens.act_main.subscreens.Subscriptions

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.vkcupfinal.R
import com.example.vkcupfinal.base.Consts.SPACE
import com.example.vkcupfinal.base.ext.getPluralsByApp
import com.example.vkcupfinal.data.*
import com.example.vkcupfinal.ui.subviews.AppSpacer
import com.example.vkcupfinal.ui.subviews.AppSpacerItem
import com.example.vkcupfinal.ui.subviews.ButtonPrimary
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupfinal.utils.DateManager
import com.example.vkcupformats.ui.common.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SubScreenSubscriptions(
    items: List<ModelSubscriptionItem>,
    onSubscribeClicked: (ModelSubscriptionItem) -> Unit,
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
                        SubscriptionItem(
                            item = item,
                            onSubscribeClicked = { onSubscribeClicked.invoke(item) }
                        )
                    }
                }
                AppSpacerItem(height = AppTheme.dimens.x3)
            }
        )
    }
}

@Composable
private fun SubscriptionItem(
    item: ModelSubscriptionItem,
    onSubscribeClicked: () -> Unit
) {
    val shape = RoundedCornerShape(AppTheme.dimens.x3)
    var isContentAvailable by remember {
        mutableStateOf(false)
    }
    isContentAvailable = item.isContentAvailable()

    var isUserSubscribed by remember {
        mutableStateOf(false)
    }
    isUserSubscribed = item.isUserSubscribed()
    val borderWidth = animateDpAsState(
        targetValue = if (isContentAvailable) AppTheme.dimens.x05 else 0.dp,
        animationSpec = tween(
            500,
            easing = LinearEasing
        )
    )

    var subtitleText: AnnotatedString? by remember {
        mutableStateOf(null)
    }
    subtitleText = item.getSubtitleText()

    val composableScope = rememberCoroutineScope()
    LaunchedEffect(key1 = isContentAvailable, key2 = isUserSubscribed, block = {
        if (item.isUserSubscribed() && isContentAvailable.not()) {
            composableScope.launch {
                while (isContentAvailable.not()) {
                    isContentAvailable = item.isContentAvailable()
                    subtitleText = item.getSubtitleText()
                    delay(1000)
                }
            }
        }
    })

    Column(
        modifier = Modifier
            .padding(horizontal = AppTheme.dimens.x1)
            .appShadow()
            .clip(shape)
            .background(AppTheme.color.Surface)
            .modifyIf(
                condition = isContentAvailable,
                modifier = {
                    border(
                        width = borderWidth.value,
                        color = AppTheme.color.Primary,
                        shape = shape
                    )
                }
            )
            .padding(vertical = AppTheme.dimens.x4, horizontal = AppTheme.dimens.x4)
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            style = AppTheme.typography.RegM,
            text = item.text,
            color = AppTheme.color.TextPrimary,
            maxLines = if (isContentAvailable) Int.MAX_VALUE else 1,
            overflow = TextOverflow.Ellipsis
        )
        AppSpacer(height = AppTheme.dimens.x1)
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = AppTheme.typography.RegL.alignStart(),
            text = item.getTitleText(),
            color = AppTheme.color.TextSecondary.withAlpha(0.3f)
        )
        if (subtitleText != null) {
            AppSpacer(height = AppTheme.dimens.x2)
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = AppTheme.typography.RegS.alignStart(),
                text = subtitleText ?: AnnotatedString(""),
                color = AppTheme.color.Primary
            )
        }
        if (isUserSubscribed.not()) {
            AppSpacer(height = AppTheme.dimens.x2)
            ButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.subscribe),
                onClick = onSubscribeClicked
            )
        }
    }
}

private fun ModelSubscriptionItem.getSubtitleText(): AnnotatedString? {
    val durationToAvailable = this.getDurationToAvailable()
    return if (this.isUserSubscribed()) {

        if (durationToAvailable != null && durationToAvailable > 0) {
            val durationText = DateManager.formatDurationToString(
                duration = durationToAvailable + 1000,
                isInCase = true
            )
            buildAnnotatedString {
                append("Вы подписаны. Для вас данный контент станет доступен через")
                append(SPACE)
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(durationText)
                }
            }

        } else if (this.minSubscriptionDuration == null) {
            buildAnnotatedString { append("Вы подписаны. Данный контент доступен вам и всем подписчикам") }
        } else {
            val subsribersText = getPluralsByApp(R.plurals.to_subscribers, this.subscribersCount)
            buildAnnotatedString {
                append("Данный контент доступен вам и еще")
                append(SPACE)
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("${this@getSubtitleText.subscribersCount} $subsribersText")
                }
            }
        }
    } else {
        null
    }
}

private fun ModelSubscriptionItem.getTitleText() = when (this.minSubscriptionDuration) {
    null -> "Данный контент доступен только подписчикам"
    else -> {
        val durationText = DateManager.formatDurationToString(
            duration = this.minSubscriptionDuration,
            isInCase = false
        )
        "Данный контент доступен подписчикам, чей срок подписки больше чем $durationText"
    }
}