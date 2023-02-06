package com.example.vkcupfinal.di

import com.example.vkcupfinal.screens.act_camera_record.ActCameraRecord
import com.example.vkcupfinal.utils.CameraRecordManager

class ActCameraRecordInjector {
    private var cameraRecordManager: CameraRecordManager? = null

    fun provideCameraRecordManager(act: ActCameraRecord) =
        cameraRecordManager ?: CameraRecordManager(
            act = act,
            container = act.provideContainerView()
        ).also {
            this.cameraRecordManager = it
        }
}