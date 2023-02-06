package com.example.vkcupfinal.ui.subviews

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.vkcupfinal.data.ModelReaction
import com.example.vkcupfinal.databinding.LaReactionBinding
import com.example.vkcupfinal.utils.getVideoSourceFromUri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout

class ReactionView(private val layoutInflater: LayoutInflater, parent: ViewGroup?) {
    private val bndLaReaction = LaReactionBinding.inflate(layoutInflater, parent, false)
    var exoPlayer: ExoPlayer? = null
    var reactionData: ModelReaction? = null
    fun setData(reaction: ModelReaction) {
        reactionData = reaction
        with(bndLaReaction.laReactionView) {
            name = reaction.userName
            selectedLike = reaction.like
            selectedEmoji = reaction.emoji
        }
    }

    fun prepare() {
        val videoUrl = reactionData?.video?.videoUrl ?: return
        val player = exoPlayer ?: ExoPlayer.Builder(layoutInflater.context).build()
            .also { exoPlayer = it }
        val source = getVideoSourceFromUri(videoUrl)
        player.setMediaSource(source)
        player.prepare()
        with(bndLaReaction.playerView) {
            this.player = player
            this.useController = false
            this.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM)
        }
    }

    fun play() {
        exoPlayer?.playWhenReady = true
    }

    fun pause() {
        exoPlayer?.playWhenReady = false
    }

    fun stop() {
        exoPlayer?.stop()
        exoPlayer?.release()
    }

    fun getRootView() = bndLaReaction.root
}