package com.example.vkcupfinal.utils

import android.os.SystemClock
import android.view.View
import java.util.*

class DebounceClickListener(
    private val action: (View) -> Unit,
    private val interval: Long = 500
) :
    View.OnClickListener {

    private val lastClickedMap: MutableMap<View, Long> = WeakHashMap()

    override fun onClick(v: View?) {
        v ?: return
        val lastClickedTime = lastClickedMap.get(v)
        val currentTime = SystemClock.uptimeMillis()

        lastClickedMap.put(v, currentTime)
        if (lastClickedTime === null || Math.abs(currentTime - lastClickedTime) > interval) {
            action.invoke(v)
        }
    }
}

fun View.setDebounceClickListener(interval: Long = 500, action: (View) -> Unit) {
    this.setOnClickListener(DebounceClickListener(action, interval))
}
