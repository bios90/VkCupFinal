package com.example.vkcupfinal.screens.act_reactions_carousel

import com.example.vkcupfinal.base.vm.BaseEffectsData
import com.example.vkcupfinal.base.vm.BaseViewModel
import com.example.vkcupfinal.data.ModelReaction
import com.example.vkcupfinal.screens.act_reactions_carousel.ActReactionsCarouselVm.*
import java.io.Serializable

class ActReactionsCarouselVm(args: Args) : BaseViewModel<State, Effect>() {

    data class Args(
        val items: List<ModelReaction>,
        val startIndex: Int
    ) : Serializable

    data class State(
        val items: List<ModelReaction>,
        val currentIndex: Int
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    override val initialState: State = State(
        items = args.items,
        currentIndex = args.startIndex
    )
}