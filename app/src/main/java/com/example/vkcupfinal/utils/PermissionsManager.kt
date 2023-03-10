package com.example.vkcupfinal.utils

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.vkcupfinal.base.BaseActivity

class PermissionsManager(private val act: BaseActivity) {

    data class PermissionCheckResult(
        val requested: List<String>,
        val granted: List<String>,
        val deniedNow: List<String>,
        val deniedPermanently: List<String>
    )

    private var onResultListener: ((PermissionCheckResult) -> Unit)? = null
    private var requestedPermissions: List<String> = emptyList()
    private val permissionsResultListener =
        act.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            ::onResult
        )

    fun checkPermissions(
        permissions: List<String> = appPermissions,
        onPermissionsResult: (PermissionCheckResult) -> Unit
    ) {
        if (onResultListener != null) {
            return
        }
        if (areAllPermissionsGranted(permissions)) {
            onPermissionsResult.invoke(
                PermissionCheckResult(
                    requested = permissions,
                    granted = permissions,
                    deniedNow = emptyList(),
                    deniedPermanently = emptyList()
                )
            )
        } else {
            requestedPermissions = listOf(permissions).flatten()
            onResultListener = onPermissionsResult
            permissionsResultListener.launch(permissions.toTypedArray())
        }
    }

    private fun isPermissionGranted(permission: String) =
        ContextCompat.checkSelfPermission(act, permission) == PackageManager.PERMISSION_GRANTED

    fun isPermissionDeniedNow(permission: String) =
        ContextCompat.checkSelfPermission(act, permission) == PackageManager.PERMISSION_DENIED

    fun isPermissionDeniedPermanently(permission: String): Boolean =
        isPermissionDeniedNow(permission) && ActivityCompat.shouldShowRequestPermissionRationale(
            act,
            permission
        ).not()

    private fun areAllPermissionsGranted(permissions: List<String>): Boolean =
        permissions.all(::isPermissionGranted)

    fun areAllPermissionsGranted() = areAllPermissionsGranted(appPermissions)
    fun hasAnyDeniedPermanently() = appPermissions.any(::isPermissionDeniedPermanently)


    private fun onResult(result: Map<String, Boolean>) {
        val resultData = if (result.all { it.value == true }) {
            PermissionCheckResult(
                requested = requestedPermissions,
                granted = requestedPermissions,
                deniedNow = emptyList(),
                deniedPermanently = emptyList()
            )
        } else {
            PermissionCheckResult(
                requested = requestedPermissions,
                granted = requestedPermissions.filter(::isPermissionGranted),
                deniedNow = requestedPermissions.filter(::isPermissionDeniedNow),
                deniedPermanently = requestedPermissions.filter(::isPermissionDeniedPermanently),
            )
        }
        onResultListener?.invoke(resultData)
        onResultListener = null
        requestedPermissions = emptyList()
    }

    fun navigateToAppPermissionsSettings() = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .apply {
            val uri = Uri.fromParts("package", act.getPackageName(), null)
            setData(uri)
        }
        .apply(act::startActivity)

    companion object {
        private val recordPermissions = listOf(
            Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA
        )
        private val filesPermissions =
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        private val appPermissions =
            listOf(filesPermissions, recordPermissions).flatten().distinct()
    }
}
