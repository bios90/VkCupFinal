package com.example.vkcupfinal.utils

import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.widget.ImageView
import com.example.vkcupfinal.base.AppClass
import com.example.vkcupfinal.utils.files.FileItem
import com.example.vkcupfinal.utils.files.FileManager
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

fun getVideoSourceFromUri(url: String): MediaSource {
    val factory = DefaultDataSourceFactory(AppClass.app, "video_player")
    val item = MediaItem.fromUri(url)
    val progressive_source = ProgressiveMediaSource.Factory(factory).createMediaSource(item)
    val looped_source = LoopingMediaSource(progressive_source)
    return looped_source
}

fun getImageFromVideo(fileItem: FileItem): FileItem? {
    val bitmap = ThumbnailUtils.createVideoThumbnail(
        fileItem.getUriAsString(),
        MediaStore.Images.Thumbnails.MINI_KIND
    ) ?: return null
    val file = FileManager.saveBitmapToFile(bitmap) ?: return null
    return FileItem.createFromFile(file)
}
