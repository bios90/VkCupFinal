package com.example.vkcupfinal.screens.act_welcome

import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.base.ext.Toast
import com.example.vkcupfinal.base.ext.safe
import com.example.vkcupfinal.base.vm.BaseEffectsData
import com.example.vkcupfinal.base.vm.BaseViewModel
import com.example.vkcupfinal.di.ActWelcomeInjector
import com.example.vkcupfinal.screens.act_camera_record.ActCameraRecord
import com.example.vkcupfinal.screens.act_camera_record.ActCameraRecordVm
import com.example.vkcupfinal.screens.act_welcome.ActWelcomeVm.Effect
import com.example.vkcupfinal.screens.act_welcome.ActWelcomeVm.State
import com.example.vkcupfinal.utils.PermissionsManager
import java.io.Serializable

class ActWelcomeVm : BaseViewModel<State, Effect>() {

    private val injector = ActWelcomeInjector()
    private var permissionManager: PermissionsManager? = null

    data class State(
        val inputText: String
    )

    sealed class Effect {
        data class BaseEffectWrapper(val data: BaseEffectsData) : Effect()
    }

    override val initialState = State(
        inputText = ""
    )

    override fun onCreate(act: BaseActivity) {
        super.onCreate(act)
        permissionManager = injector.providePermissionsManager(act)
    }

    inner class UiListener {

        fun onInputTextChanged(text: String) {
            setStateResult(
                state = currentState.copy(
                    inputText = text
                )
            )
        }

        fun onResult(data: Serializable?) {
            when (data) {
                is ActCameraRecordVm.Result -> {
                    Toast(data.fileItem.getSizeString())
                }
            }
        }

        fun onButtonClicked() {
            permissionManager?.checkPermissions {
                if (permissionManager?.areAllPermissionsGranted().safe()) {
                    setStateResult(
                        state = currentState,
                        effects = setOf(
                            Effect.BaseEffectWrapper(
                                data = BaseEffectsData.NavigateTo(
                                    clazz = ActCameraRecord::class.java,
                                    onResult = {
                                        val result = it as? ActCameraRecordVm.Result
                                        Toast(result?.fileItem?.getSizeString())
                                    }
                                )
                            )
                        )
                    )
                }
            }
        }
    }
}