package com.example.vkcupfinal.base

import java.io.Serializable

class ActResultManager(private val activity: BaseActivity) {

    private val resultHandlers: MutableMap<String, (Serializable?) -> Unit> = mutableMapOf()

    private val activityResultBase =
        activity.registerForActivityResult(ActResultContactBase()) { data ->
            val result = data as? ActivityResultData ?: return@registerForActivityResult
            val uid = result.activityStartData.uid
            resultHandlers.get(uid)?.invoke(result.result)
        }

    fun startForResult(data: ActivityStartData, onResult: (Serializable?) -> Unit) {
        resultHandlers.put(data.uid, { onResult.invoke(it) })
        activityResultBase.launch(data)
    }
}