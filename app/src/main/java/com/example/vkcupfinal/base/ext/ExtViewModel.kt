package com.example.vkcupfinal.base.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun ViewModel.makeActionDelayed(delayTime: Long, action: () -> Unit) {
    viewModelScope.launch {
        delay(delayTime)
        action.invoke()
    }
}