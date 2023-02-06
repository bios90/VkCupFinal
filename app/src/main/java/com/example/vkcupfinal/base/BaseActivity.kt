package com.example.vkcupfinal.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.bios.yacupsurpise.ui.common.utils.WindowWrapper
import com.example.vkcupfinal.base.ext.*
import com.example.vkcupfinal.base.vm.BaseEffectsData
import com.example.vkcupfinal.screens.act_camera_record.ActCameraRecordVm
import com.example.vkcupfinal.ui.subviews.BottomSheetForText
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupformats.ui.common.getComposeRootView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseActivity : AppCompatActivity(), WindowWrapper {

    private val activityResultManger = ActResultManager(this)

    protected open val onActivityResult: (java.io.Serializable?) -> Unit = {}

    private val rootView: ComposeView by lazy { getComposeRootView(this) }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(rootView)
    }

    protected fun setContent(content: @Composable () -> Unit) {
        rootView.setContent {
            AppTheme {
                ApplyScreenWindowData()
                content.invoke()
            }
        }
    }

    fun handleBaseEffects(data: BaseEffectsData) {
        when (data) {
            is BaseEffectsData.Toast -> Toast(data.text)
            is BaseEffectsData.NavigateTo -> {
                activityResultManger.startForResult(
                    data = ActivityStartData(
                        actClass = data.clazz,
                        params = data.args,
                        clearAllPrevious = data.finishAllPrevious
                    ),
                    onResult = { data.onResult?.invoke(it) }
                )
                if (data.finishCurrent && data.finishAllPrevious.not()) {
                    finish()
                }
            }
            is BaseEffectsData.Finish -> {
                if (isFinishing.not()) {
                    finish()
                }
            }
            is BaseEffectsData.FinishWithResult -> {
                val startData = getStartForResultData() ?: error("Error no start for result data")
                val resultData = ActivityResultData(
                    activityStartData = startData,
                    result = data.result
                )
                val returnIntent = Intent()
                    .apply { putExtra(Consts.RESULT, resultData) }
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
            is BaseEffectsData.ShowBottomTextDialog -> {
                BottomSheetForText.show(
                    fm = supportFragmentManager,
                    args = data.args
                )
            }
        }
    }
}