package com.example.vkcupfinal.screens.act_main.subscreens.Reactions

import android.view.Display.Mode
import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.base.ext.makeActionDelayed
import com.example.vkcupfinal.base.ext.randomInt
import com.example.vkcupfinal.base.ext.replace
import com.example.vkcupfinal.base.vm.BaseEffectsData
import com.example.vkcupfinal.base.vm.BaseViewModel
import com.example.vkcupfinal.data.ModelReaction
import com.example.vkcupfinal.data.ModelReactionItem
import com.example.vkcupfinal.data.TypeLike
import com.example.vkcupfinal.di.SubScreenReactionsInjector
import com.example.vkcupfinal.screens.act_camera_record.ActCameraRecord
import com.example.vkcupfinal.screens.act_camera_record.ActCameraRecordVm
import com.example.vkcupfinal.screens.act_main.subscreens.Reactions.SubScreenReactionsVm.*
import com.example.vkcupfinal.screens.act_reactions_carousel.ActReactionsCarousel
import com.example.vkcupfinal.screens.act_reactions_carousel.ActReactionsCarouselVm
import com.example.vkcupfinal.ui.subviews.BottomSheetForText
import com.example.vkcupfinal.utils.MockHelper
import com.example.vkcupfinal.utils.PermissionsManager
import com.example.vkcupfinal.utils.getImageFromVideo

class SubScreenReactionsVm : BaseViewModel<State, Effect>() {
    data class State(
        val items: List<ModelReactionItem>
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    override val initialState: State = State(items = getReactionItems())

    private fun getReactionItems() = MockHelper.getReactionItems().distinctBy(ModelReactionItem::id)

    private val injector = SubScreenReactionsInjector()
    private var permissionManager: PermissionsManager? = null

    override fun onCreate(act: BaseActivity) {
        super.onCreate(act)
        permissionManager = injector.providePermissionsManager(act)
    }

    private fun updateReaction(itemUpdated: ModelReactionItem) {
        val itemIndex = currentState.items.indexOfFirst { it.id == itemUpdated.id }
        if (itemIndex == -1) {
            return
        }

        val fixedItems = currentState.items.replace(
            index = itemIndex,
            item = itemUpdated,
        )
            .distinctBy(ModelReactionItem::id)
        setStateResult(
            state = currentState.copy(
                items = fixedItems
            )
        )
    }

    inner class UiListener {
        fun onReactionClicked(reaction: ModelReaction, reactionItem: ModelReactionItem) {
            val reactions = if (reactionItem.userReaction != null) {
                buildList {
                    add(reactionItem.userReaction)
                    addAll(reactionItem.reactions)
                }
            } else {
                reactionItem.reactions
            }
            val index = reactions.indexOf(reaction)

            val args = ActReactionsCarouselVm.Args(
                items = reactions,
                startIndex = if (index == -1) 0 else index
            )
            setStateResult(
                state = currentState,
                effects = setOf(
                    Effect.BaseEffectWrapper(
                        data = BaseEffectsData.NavigateTo(
                            clazz = ActReactionsCarousel::class.java,
                            args = args
                        )
                    )
                )
            )
        }

        fun onRemoveUserReactionClicked(reactionItem: ModelReactionItem) {
            if (reactionItem.userReaction != null) {
                val likes =
                    if (reactionItem.userReaction.like == TypeLike.LIKE) reactionItem.likesCount - 1 else reactionItem.likesCount
                val disLikes =
                    if (reactionItem.userReaction.like == TypeLike.DISLIKE) reactionItem.dislikeCount - 1 else reactionItem.dislikeCount
                val updatedReactionItem = reactionItem.copy(
                    userReaction = null,
                    likesCount = likes,
                    dislikeCount = disLikes
                )
                updateReaction(updatedReactionItem)
            }
        }

        fun onAddReactionClicked(reactionItem: ModelReactionItem) {
            permissionManager?.checkPermissions {
                setStateResult(
                    state = currentState,
                    effects = setOf(
                        Effect.BaseEffectWrapper(
                            data = BaseEffectsData.NavigateTo(
                                clazz = ActCameraRecord::class.java,
                                onResult = {
                                    val result =
                                        it as? ActCameraRecordVm.Result ?: return@NavigateTo
                                    val reaction = ModelReaction(
                                        id = randomInt.toLong(),
                                        video = result.fileItem,
                                        preview = result.preview,
                                        userName = MockHelper.getUserName(),
                                        like = result.like,
                                        emoji = result.emoji,
                                        isSelfReaction = true
                                    )
                                    val likes =
                                        if (reaction.like == TypeLike.LIKE) reactionItem.likesCount + 1 else reactionItem.likesCount
                                    val disLikes =
                                        if (reaction.like == TypeLike.DISLIKE) reactionItem.dislikeCount + 1 else reactionItem.dislikeCount
                                    val updatedReactionItem = reactionItem.copy(
                                        userReaction = reaction,
                                        likesCount = likes,
                                        dislikeCount = disLikes
                                    )
                                    updateReaction(updatedReactionItem)
                                }
                            )
                        )
                    )
                )
            }
        }

        fun onScrolledToBottom() {
            val newItems = currentState.items
                .plus(getReactionItems().shuffled())
                .distinctBy(ModelReactionItem::id)
            setStateResult(
                state = currentState.copy(
                    items = newItems
                )
            )
        }
    }
}