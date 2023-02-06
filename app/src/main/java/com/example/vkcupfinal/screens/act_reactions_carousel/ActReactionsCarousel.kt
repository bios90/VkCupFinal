package com.example.vkcupfinal.screens.act_reactions_carousel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE
import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.base.ext.*
import com.example.vkcupfinal.base.vm.createViewModelFactory
import com.example.vkcupfinal.databinding.ActReactionsCarouselBinding
import com.example.vkcupfinal.databinding.LaReactionBinding
import com.example.vkcupfinal.ui.adapters.AdapterVpUniversal
import com.example.vkcupfinal.ui.subviews.ReactionView
import com.example.vkcupfinal.ui.theme.AppTheme

class ActReactionsCarousel : BaseActivity() {

    private val vmActReactionsCarousel: ActReactionsCarouselVm by viewModels {
        createViewModelFactory<ActReactionsCarouselVm, ActReactionsCarouselVm.Args>(args = getArgs())
    }
    private val adapterPager by lazy { AdapterVpUniversal() }
    private val bndReactionsCarousel: ActReactionsCarouselBinding by lazy {
        ActReactionsCarouselBinding.inflate(
            layoutInflater,
            null,
            false
        )
    }
    private var lastState: ActReactionsCarouselVm.State? = null
    private var reactViews: List<ReactionView> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setupBars()
        setContentView(bndReactionsCarousel.root)
        setupMainView()
    }

    override fun onPause() {
        super.onPause()
        reactViews.forEach(ReactionView::pause)
    }

    override fun onResume() {
        super.onResume()
        startCurrentPageVideo()
    }

    override fun onDestroy() {
        super.onDestroy()
        reactViews.forEach(ReactionView::stop)
    }

    private fun setupBars() {
        window.hideSystemUI()
        window.statusBarColor = AppTheme.color.Transparent.toArgb()
        window.navigationBarColor = AppTheme.color.Transparent.toArgb()
    }

    private fun startCurrentPageVideo() {
        for ((index, reactionView) in reactViews.withIndex()) {
            if (index == bndReactionsCarousel.vp.currentItem) {
                reactionView.play()
            } else {
                reactionView.pause()
            }
        }
    }


    private fun setupMainView() {
        bndReactionsCarousel.vp.adapter = adapterPager
        bndReactionsCarousel.vp.addOnPageChangeListener(
            object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    startCurrentPageVideo()
                }
            }
        )
        subscribeState(
            act = this,
            vm = vmActReactionsCarousel,
            stateConsumer = { state ->
                val isNewItems = state.items.size != lastState?.items?.size
                if (isNewItems) {
                    reactViews = state.items.map { reaction ->
                        ReactionView(layoutInflater, null)
                            .apply {
                                setData(reaction)
                                prepare()
                            }
                    }
                    bndReactionsCarousel.vp.offscreenPageLimit = state.items.size
                    adapterPager.setViews(reactViews.map(ReactionView::getRootView))
                }

                if (bndReactionsCarousel.vp.currentItem != state.currentIndex) {
                    bndReactionsCarousel.vp.setCurrentItem(state.currentIndex, true)
                }

                if (isNewItems) {
                    startCurrentPageVideo()
                }

                lastState = state
            },
            effectsConsumer = { effects ->
                for (effect in effects) {
                    when (effect) {
                        is ActReactionsCarouselVm.Effect.BaseEffectWrapper ->
                            handleBaseEffects(effect.data)
                    }
                }
            }
        )
    }
}