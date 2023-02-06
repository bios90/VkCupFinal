package com.example.vkcupfinal.screens.act_main

import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.base.ext.makeActionDelayed
import com.example.vkcupfinal.base.vm.BaseViewModel
import com.example.vkcupfinal.data.TypeTab

class ActMainVm : BaseViewModel<ActMainVm.State, ActMainVm.Effect>() {

    data class State(
        val selectedTab: TypeTab
    )

    sealed class Effect

    override val initialState: State = State(
        selectedTab = TypeTab.values().first()
    )

    inner class UiListener {
        fun onTabClicked(tab: TypeTab) {
            setStateResult(
                state = currentState.copy(
                    selectedTab = tab
                )
            )
        }
    }
}