package com.example.vkcupfinal.screens.act_camera_record

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.base.ext.hideSystemUI
import com.example.vkcupfinal.base.ext.subscribeState
import com.example.vkcupfinal.base.ext.toVisibility
import com.example.vkcupfinal.base.vm.createViewModelFactory
import com.example.vkcupfinal.data.ScreenState
import com.example.vkcupfinal.databinding.ActCameraRecordBinding
import com.example.vkcupfinal.screens.dialogs.ReactionResultDialog

class ActCameraRecord : BaseActivity() {

    private val vm: ActCameraRecordVm by viewModels {
        createViewModelFactory<ActCameraRecordVm>()
    }

    private val bndActCameraRecord by lazy {
        ActCameraRecordBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setupBars()
        setContentView(bndActCameraRecord.root)
        subscribeState(
            act = this,
            vm = vm,
            stateConsumer = ::consumeState,
            effectsConsumer = ::handleEffects
        )
    }

    private fun setupBars() {
        window.hideSystemUI()
    }

    private fun consumeState(state: ActCameraRecordVm.State) {
        bndActCameraRecord.recView.seconds = state.recSeconds
        with(bndActCameraRecord.recordButton) {
            isRecording = state.isRecording
            onClick = vm.UiListener()::clickedRecordOrStop
        }
        with(bndActCameraRecord.emojiView) {
            selected = state.selectedEmoji
            onEmojiClicked = vm.UiListener()::onEmojiClicked
        }
        with(bndActCameraRecord.likeDislikeView) {
            selected = state.like
            onClicked = vm.UiListener()::onLikeSelected
        }
        bndActCameraRecord.progressView.visibility =
            (state.screenState == ScreenState.LOADING).toVisibility()
    }

    private fun handleEffects(effects: Set<ActCameraRecordVm.Effect>) {
        for (effect in effects) {
            when (effect) {
                is ActCameraRecordVm.Effect.BaseEffectWrapper -> handleBaseEffects(effect.data)
                is ActCameraRecordVm.Effect.ShowResultDialog -> {
                    ReactionResultDialog.show(
                        fm = supportFragmentManager,
                        reaction = effect.data,
                        onSaveClicked = vm.UiListener()::onResultAccepted,
                        onCancelClicked = vm.UiListener()::onResultDeclined
                    )
                }
            }
        }
    }

    fun provideContainerView(): ViewGroup {
        return bndActCameraRecord.glCameraView
    }
}