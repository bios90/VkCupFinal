package com.example.vkcupfinal.utils

import android.opengl.GLSurfaceView
import android.view.ViewGroup
import com.daasuu.camerarecorder.CameraRecorder
import com.daasuu.camerarecorder.CameraRecorderBuilder
import com.daasuu.camerarecorder.LensFacing
import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.base.ext.addLifeCycleObserver
import com.example.vkcupfinal.utils.files.FileItem
import com.example.vkcupfinal.utils.files.FileManager
import java.io.File

class CameraRecordManager(
    private val act: BaseActivity,
    private val container: ViewGroup
) {
    private var cameraRecorder: CameraRecorder? = null
    private var isRecording: Boolean = false
    private var recordingFile: File? = null
    private var surfaceView: GLSurfaceView? = null
    var onError: () -> Unit = {}

    init {
        act.addLifeCycleObserver(
            onResume = {
                surfaceView = GLSurfaceView(act)
                container.addView(surfaceView)
                cameraRecorder = CameraRecorderBuilder(act, surfaceView)
                    .lensFacing(LensFacing.FRONT)
                    .build()
            },
            onPause = {
                if (isRecording) {
                    onError.invoke()
                }
                isRecording = false
                surfaceView?.onPause()
                cameraRecorder?.stop()
                cameraRecorder?.release()
                recordingFile = null
                cameraRecorder = null
                surfaceView = null
            }
        )
    }

    fun startRecording() {
        if (isRecording) {
            return
        }
        val file = FileManager.createTempVideoFile()
        cameraRecorder?.start(file.absolutePath)
        recordingFile = file
        isRecording = true
    }

    fun stop(): FileItem? {
        cameraRecorder?.stop()
        isRecording = false
        return recordingFile?.let(FileItem::createFromFile)
    }
}
