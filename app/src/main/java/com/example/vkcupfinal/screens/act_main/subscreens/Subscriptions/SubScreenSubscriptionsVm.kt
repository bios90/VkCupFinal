package com.example.vkcupfinal.screens.act_main.subscreens.Subscriptions

import com.example.vkcupfinal.base.ext.replace
import com.example.vkcupfinal.base.vm.BaseEffectsData
import com.example.vkcupfinal.base.vm.BaseViewModel
import com.example.vkcupfinal.data.ModelQuoteCommentItem
import com.example.vkcupfinal.data.ModelSubscriptionItem
import com.example.vkcupfinal.screens.act_main.subscreens.Subscriptions.SubScreenSubscriptionsVm.*
import com.example.vkcupfinal.utils.MockHelper
import java.util.*

class SubScreenSubscriptionsVm : BaseViewModel<State, Effect>() {
    data class State(
        val items: List<ModelSubscriptionItem>
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    override val initialState: State = State(
        items = getSubscriptionItems()
    )

    private fun getSubscriptionItems() =
        MockHelper.getModelSubscriptionItems().distinctBy(ModelSubscriptionItem::id)

    private fun updateItem(itemUpdated: ModelSubscriptionItem) {
        val itemIndex = currentState.items.indexOfFirst { it.id == itemUpdated.id }
        if (itemIndex == -1) {
            return
        }
        val fixedItems = currentState.items.replace(
            index = itemIndex,
            item = itemUpdated,
        )
            .distinctBy(ModelSubscriptionItem::id)
        setStateResult(
            state = currentState.copy(
                items = fixedItems
            )
        )
    }

    inner class UiListener {

        fun onSubscribeClicked(item: ModelSubscriptionItem) {
            val updatedItem = item.copy(
                userSubscriptionDate = Date()
            )
            updateItem(updatedItem)
        }

        fun onScrolledToBottom() {
            val newItems = currentState.items
                .plus(MockHelper.getModelSubscriptionItems())
                .distinctBy(ModelSubscriptionItem::id)
            setStateResult(
                state = currentState.copy(
                    items = newItems
                )
            )
        }
    }
}