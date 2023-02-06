package com.example.vkcupfinal.base.vm

import androidx.lifecycle.MutableLiveData

class StateResultLiveData<State, Effect> : MutableLiveData<StateResultEvent<State, Effect>>()
