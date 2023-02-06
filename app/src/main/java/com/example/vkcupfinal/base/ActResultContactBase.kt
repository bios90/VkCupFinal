package com.example.vkcupfinal.base

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.vkcupfinal.base.ext.addClearAllPreviousFlags
import com.example.vkcupfinal.base.ext.getResult
import java.io.Serializable
import java.util.UUID

class ActResultContactBase : ActivityResultContract<ActivityStartData, Serializable?>() {
    override fun createIntent(context: Context, input: ActivityStartData): Intent {
        return Intent(
            context, input.actClass
        ).apply {
            putExtra(Consts.ARGS, input.params)
            putExtra(Consts.START_DATA, input)
            if (input.clearAllPrevious) {
                addClearAllPreviousFlags()
            }
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Serializable? = intent?.getResult()
}

data class ActivityStartData(
    val actClass: Class<out BaseActivity>,
    val params: Serializable?,
    val clearAllPrevious: Boolean,
    val uid: String = UUID.randomUUID().toString()
) : Serializable

data class ActivityResultData(
    val activityStartData: ActivityStartData,
    val result: Serializable
) : Serializable
