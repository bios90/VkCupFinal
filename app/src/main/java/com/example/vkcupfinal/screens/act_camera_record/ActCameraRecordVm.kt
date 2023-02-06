package com.example.vkcupfinal.screens.act_camera_record

import androidx.lifecycle.viewModelScope
import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.base.Consts
import com.example.vkcupfinal.base.ext.makeActionDelayed
import com.example.vkcupfinal.base.ext.randomInt
import com.example.vkcupfinal.base.ext.toSet
import com.example.vkcupfinal.base.vm.BaseEffectsData
import com.example.vkcupfinal.base.vm.BaseViewModel
import com.example.vkcupfinal.data.*
import com.example.vkcupfinal.di.ActCameraRecordInjector
import com.example.vkcupfinal.utils.CameraRecordManager
import com.example.vkcupfinal.utils.MockHelper
import com.example.vkcupfinal.utils.files.FileItem
import com.example.vkcupfinal.utils.getImageFromVideo
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable

class ActCameraRecordVm : BaseViewModel<ActCameraRecordVm.State, ActCameraRecordVm.Effect>() {

    data class Result(
        val fileItem: FileItem,
        val preview: ImagePreview?,
        val like: TypeLike,
        val emoji: TypeEmoji?,
    ) : Serializable

    data class State(
        val screenState: ScreenState,
        val isRecording: Boolean,
        val recSeconds: Int?,
        val like: TypeLike,
        val selectedEmoji: TypeEmoji?
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
        data class ShowResultDialog(val data: ModelReaction) : Effect()
    }

    override val initialState: State = State(
        screenState = ScreenState.SUCCESS,
        isRecording = false,
        recSeconds = null,
        like = TypeLike.NOT_SELECTED,
        selectedEmoji = null
    )

    private val injector = ActCameraRecordInjector()
    private var recordManager: CameraRecordManager? = null
    private var timerJob: Job? = null

    override fun onCreate(act: BaseActivity) {
        super.onCreate(act)
        recordManager = injector.provideCameraRecordManager(act as ActCameraRecord)
        recordManager?.onError = { cancelRecording() }
    }

    fun startRecording() {
        recordManager ?: return
        setStateResult(
            state = currentState.copy(
                isRecording = true
            )
        )
        recordManager?.startRecording()
        timerJob = viewModelScope.launch {
            for (i in 0..Consts.RECORD_DURATION_SECONDS) {
                if (i == 10) {
                    stopRecording()
                } else {
                    setStateResult(
                        state = currentState.copy(
                            recSeconds = i
                        )
                    )
                }
                delay(1000)
            }
        }
    }

    private fun cancelRecording() {
        timerJob?.cancel()
        setStateResult(
            state = currentState.copy(
                isRecording = false,
                recSeconds = null
            )
        )
    }

    private fun stopRecording() {
        cancelRecording()
        val result = recordManager?.stop() ?: return
        setStateResult(
            state = currentState.copy(
                screenState = ScreenState.LOADING
            )
        )
        makeActionDelayed(
            delayTime = 3000,
            action = {
                setStateResult(
                    state = currentState,
                    effects = setOf(
                        Effect.ShowResultDialog(
                            data = ModelReaction(
                                id = randomInt.toLong(),
                                video = result,
                                preview = getPreviewImageFromFile(result),
                                userName = MockHelper.getUserName(),
                                like = currentState.like,
                                emoji = currentState.selectedEmoji
                            )
                        )
                    )
                )
            }
        )
    }

    private fun getPreviewImageFromFile(fileItem: FileItem): ImagePreview {
        return ImagePreview(resId = null, url = getImageFromVideo(fileItem)?.imageUrl)
    }

    inner class UiListener {

        fun onEmojiClicked(emoji: TypeEmoji) {
            setStateResult(
                state = currentState.copy(
                    selectedEmoji = emoji
                )
            )
        }

        fun onLikeSelected(like: TypeLike) {
            setStateResult(
                state = currentState.copy(
                    like = like
                )
            )
        }

        fun clickedRecordOrStop() {
            if (currentState.isRecording) {
                stopRecording()
            } else {
                startRecording()
            }
        }

        fun onResultDeclined() {
            setStateResult(
                state = currentState.copy(
                    screenState = ScreenState.SUCCESS
                )
            )
        }

        fun onResultAccepted(reaction: ModelReaction) {
            val file = reaction.video as? FileItem ?: return
            setStateResult(
                state = currentState,
                effects = Effect.BaseEffectWrapper(
                    data = BaseEffectsData.FinishWithResult(
                        result = Result(
                            fileItem = file,
                            like = currentState.like,
                            emoji = currentState.selectedEmoji,
                            preview = reaction.preview
                        )
                    )
                ).toSet()
            )
        }
    }
}