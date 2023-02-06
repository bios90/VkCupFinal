package com.example.vkcupfinal.screens.act_welcome

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.example.vkcupfinal.base.ActResultContactBase
import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.base.ext.subscribeState
import com.example.vkcupfinal.base.vm.createViewModelFactory
import com.example.vkcupfinal.ui.common.ScreenWindowData
import com.example.vkcupfinal.ui.theme.AppTheme
import java.io.Serializable

class ActWelcome : BaseActivity() {

    override val screenWindowData = ScreenWindowData(
        colorStatusBar = AppTheme.color.Accent,
        colorNavBar = AppTheme.color.Background,
        isUnderNavBar = false,
        isUnderStatusBar = false
    )

    private val vm: ActWelcomeVm by viewModels {
        createViewModelFactory<ActWelcomeVm>()
    }

    override val onActivityResult: (Serializable?) -> Unit = { result ->
        vm.UiListener().onResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeState(
            act = this,
            vm = vm,
            stateConsumer = ::consumeState,
            effectsConsumer = ::handleEffect
        )
    }

    private fun consumeState(state: ActWelcomeVm.State) {
        setContent {
            ActWelcomeCompose(
                state = state,
                onInputTextChanged = vm.UiListener()::onInputTextChanged,
                onButtonClicked = vm.UiListener()::onButtonClicked,
            )
        }
    }

    private fun handleEffect(effects: Set<ActWelcomeVm.Effect>) {
        for (effect in effects) {
            when (effect) {
                is ActWelcomeVm.Effect.BaseEffectWrapper -> handleBaseEffects(effect.data)
            }
        }
    }
}