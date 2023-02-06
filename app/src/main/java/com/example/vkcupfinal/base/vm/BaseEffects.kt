package com.example.vkcupfinal.base.vm

import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.ui.subviews.BottomSheetForText
import java.io.Serializable
import kotlin.reflect.KClass

sealed class BaseEffectsData {
    data class NavigateTo(
        val clazz: Class<out BaseActivity>,
        val args: Serializable? = null,
        val finishCurrent: Boolean = false,
        val finishAllPrevious: Boolean = false,
        val onResult: ((Serializable?) -> Unit)? = null,
    ) : BaseEffectsData()

    data class Toast(val text: String) : BaseEffectsData()
    object Finish : BaseEffectsData()
    data class FinishWithResult(val result: Serializable) : BaseEffectsData()
    data class ShowBottomTextDialog(val args: BottomSheetForText.Args) : BaseEffectsData()
}
