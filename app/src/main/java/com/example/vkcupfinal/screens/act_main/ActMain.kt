package com.example.vkcupfinal.screens.act_main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.base.ext.setNavBarLightDark
import com.example.vkcupfinal.base.ext.setStatusBarColorMy
import com.example.vkcupfinal.base.ext.setStatusLightDark
import com.example.vkcupfinal.base.ext.subscribeState
import com.example.vkcupfinal.base.vm.createViewModelFactory
import com.example.vkcupfinal.data.*
import com.example.vkcupfinal.databinding.ActMainBinding
import com.example.vkcupfinal.screens.act_main.subscreens.QuoteComments.SubScreenQuoteComments
import com.example.vkcupfinal.screens.act_main.subscreens.QuoteComments.SubScreenQuoteCommentsVm
import com.example.vkcupfinal.screens.act_main.subscreens.Reactions.SubScreenReactions
import com.example.vkcupfinal.screens.act_main.subscreens.Reactions.SubScreenReactionsVm
import com.example.vkcupfinal.screens.act_main.subscreens.Subscriptions.SubScreenSubscriptions
import com.example.vkcupfinal.screens.act_main.subscreens.Subscriptions.SubScreenSubscriptionsVm
import com.example.vkcupfinal.ui.adapters.AdapterVpUniversal
import com.example.vkcupfinal.ui.subviews.BottomSheetForText
import com.example.vkcupfinal.ui.theme.AppTheme

class ActMain : BaseActivity() {

    val vmActMain by viewModels<ActMainVm> {
        createViewModelFactory<ActMainVm>()
    }

    val vmSubscriptions by viewModels<SubScreenSubscriptionsVm> {
        createViewModelFactory<SubScreenSubscriptionsVm>()
    }

    val vmReactions by viewModels<SubScreenReactionsVm> {
        createViewModelFactory<SubScreenReactionsVm>()
    }

    val vmQuoteComments by viewModels<SubScreenQuoteCommentsVm> {
        createViewModelFactory<SubScreenQuoteCommentsVm>()
    }

    private val bndActFormatDetails: ActMainBinding by lazy {
        ActMainBinding.inflate(
            layoutInflater,
            null,
            false
        )
    }

    private val adapterPager by lazy { AdapterVpUniversal() }

    private val pagerViews: List<ComposeView> by lazy { TypeTab.values().map { ComposeView(this) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bndActFormatDetails.root)
        setupMainView()
        setupSubViews()
    }

    private fun setupBars() {
        window.setStatusBarColorMy(AppTheme.color.Surface.toArgb())
        window.navigationBarColor = AppTheme.color.Surface.toArgb()
        window.setStatusLightDark(true)
        window.setNavBarLightDark(true)
    }

    override fun onResume() {
        super.onResume()
        setupBars()
    }

    private fun setupMainView() {
        adapterPager.setViews(pagerViews)
        bndActFormatDetails.vp.adapter = adapterPager
        bndActFormatDetails.bottomBar.onTabSelected = vmActMain.UiListener()::onTabClicked
        subscribeState(
            act = this,
            vm = vmActMain,
            stateConsumer = { state ->
                bndActFormatDetails.bottomBar.selectedTab = state.selectedTab
                val pos = TypeTab.values().indexOf(state.selectedTab)
                bndActFormatDetails.vp.currentItem = pos
                bndActFormatDetails.appBar.title = getString(state.selectedTab.getAppbarTitle())
                bndActFormatDetails.appBar.onCircleQuestionClick = {
                    BottomSheetForText.show(
                        fm = supportFragmentManager,
                        args = state.selectedTab.getBottomTextArgs()
                    )
                }
            }
        )
    }

    private fun setupSubViews() {
        setSubscriptionsView()
        setQuoteCommentsView()
        setReactionsView()
    }

    private fun setSubscriptionsView() {
        subscribeState(
            act = this,
            vm = vmSubscriptions,
            stateConsumer = { state ->
                getViewForTab(TypeTab.BITES).setContent {
                    SubScreenSubscriptions(
                        items = state.items,
                        onSubscribeClicked = vmSubscriptions.UiListener()::onSubscribeClicked,
                        onScrolledToBottom = vmSubscriptions.UiListener()::onScrolledToBottom
                    )
                }
            }
        )
    }

    private fun setReactionsView() {
        subscribeState(
            act = this,
            vm = vmReactions,
            stateConsumer = { state ->
                getViewForTab(TypeTab.REACTS).setContent {
                    SubScreenReactions(
                        items = state.items,
                        onReactionClicked = vmReactions.UiListener()::onReactionClicked,
                        onRemoveUserReactionClicked = vmReactions.UiListener()::onRemoveUserReactionClicked,
                        onAddReactionClicked = vmReactions.UiListener()::onAddReactionClicked,
                        onScrolledToBottom = vmReactions.UiListener()::onScrolledToBottom
                    )
                }
            },
            effectsConsumer = { effects ->
                for (effect in effects) {
                    when (effect) {
                        is SubScreenReactionsVm.Effect.BaseEffectWrapper -> {
                            handleBaseEffects(effect.data)
                        }
                    }
                }
            }
        )
    }

    private fun setQuoteCommentsView() {
        subscribeState(
            act = this,
            vm = vmQuoteComments,
            stateConsumer = { state ->
                getViewForTab(TypeTab.QUOTES).setContent {
                    SubScreenQuoteComments(
                        items = state.items,
                        onLikeClicked = vmQuoteComments.UiListener()::onLikeClicked,
                        onInputChanged = vmQuoteComments.UiListener()::onInputChanged,
                        onAddReactionClicked = vmQuoteComments.UiListener()::onAddReactionClicked,
                        onScrolledToBottom = vmQuoteComments.UiListener()::onScrolledToBottom
                    )
                }
            },
            effectsConsumer = { effects ->
                for (effect in effects) {
                    when (effect) {
                        is SubScreenQuoteCommentsVm.Effect.BaseEffectWrapper -> {
                            handleBaseEffects(effect.data)
                        }
                    }
                }
            }
        )
    }

    private fun getViewForTab(tab: TypeTab): ComposeView =
        pagerViews.get(TypeTab.values().indexOf(tab))
}