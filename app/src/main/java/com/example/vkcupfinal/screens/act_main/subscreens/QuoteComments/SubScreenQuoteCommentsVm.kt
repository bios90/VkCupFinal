package com.example.vkcupfinal.screens.act_main.subscreens.QuoteComments

import com.example.vkcupfinal.base.ext.replace
import com.example.vkcupfinal.base.vm.BaseEffectsData
import com.example.vkcupfinal.base.vm.BaseViewModel
import com.example.vkcupfinal.data.*
import com.example.vkcupfinal.screens.act_main.subscreens.QuoteComments.SubScreenQuoteCommentsVm.*
import com.example.vkcupfinal.utils.MockHelper

class SubScreenQuoteCommentsVm : BaseViewModel<State, Effect>() {

    data class State(
        val items: List<ModelQuoteCommentItem>
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    override val initialState: State = State(items = getQuoteCommentItems())

    private fun getQuoteCommentItems() =
        MockHelper.getQuoteCommentItems().distinctBy(ModelQuoteCommentItem::id)

    private fun updateItem(itemUpdated: ModelQuoteCommentItem) {
        val itemIndex = currentState.items.indexOfFirst { it.id == itemUpdated.id }
        if (itemIndex == -1) {
            return
        }

        val fixedItems = currentState.items.replace(
            index = itemIndex,
            item = itemUpdated,
        )
            .distinctBy(ModelQuoteCommentItem::id)
        setStateResult(
            state = currentState.copy(
                items = fixedItems
            )
        )
    }

    inner class UiListener {
        fun onLikeClicked(item: ModelQuoteCommentItem, like: TypeLike) {
            val updatedItem = item.copy(
                like = like,
                reaction = ModelQuoteCommentReaction(
                    like = like,
                    text = null
                ).takeIf { item.isCommentAllowed.not() }
            )
            updateItem(updatedItem)
        }

        fun onInputChanged(item: ModelQuoteCommentItem, text: String) {
            val updatedItem = item.copy(
                inputText = text
            )
            updateItem(updatedItem)
        }

        fun onAddReactionClicked(item: ModelQuoteCommentItem) {
            if (item.inputText.isNotEmpty() || item.like != TypeLike.NOT_SELECTED) {
                val updatedItem = item.copy(
                    reaction = ModelQuoteCommentReaction(
                        like = item.like,
                        text = item.inputText
                    )
                )
                updateItem(updatedItem)
            }
        }

        fun onScrolledToBottom() {
            val newItems = currentState.items
                .plus(getQuoteCommentItems())
                .distinctBy(ModelQuoteCommentItem::id)
            setStateResult(
                state = currentState.copy(
                    items = newItems
                )
            )
        }
    }
}