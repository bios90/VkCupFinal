package com.example.vkcupfinal.di

import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.utils.PermissionsManager

class SubScreenReactionsInjector {
    private var permissionsManager: PermissionsManager? = null
    fun providePermissionsManager(act: BaseActivity) = permissionsManager
        ?: PermissionsManager(act)
            .also { this.permissionsManager = it }
}