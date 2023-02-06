@file:Suppress("UNCHECKED_CAST")

package com.example.vkcupfinal.base.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vkcupfinal.screens.act_camera_record.ActCameraRecord
import com.example.vkcupfinal.screens.act_camera_record.ActCameraRecordVm
import com.example.vkcupfinal.screens.act_main.ActMainVm
import com.example.vkcupfinal.screens.act_main.subscreens.QuoteComments.SubScreenQuoteCommentsVm
import com.example.vkcupfinal.screens.act_main.subscreens.Reactions.SubScreenReactionsVm
import com.example.vkcupfinal.screens.act_main.subscreens.Subscriptions.SubScreenSubscriptionsVm
import com.example.vkcupfinal.screens.act_reactions_carousel.ActReactionsCarousel
import com.example.vkcupfinal.screens.act_reactions_carousel.ActReactionsCarouselVm
import com.example.vkcupfinal.screens.act_welcome.ActWelcomeVm
import java.io.Serializable

class BaseViewModelsFactory<V : ViewModel, A : Serializable>(
    val viewModelClass: Class<V>,
    val args: A? = null,
) : ViewModelProvider.Factory {

    override fun <V : ViewModel> create(modelClass: Class<V>): V {
        return when (viewModelClass) {
            ActWelcomeVm::class.java -> ActWelcomeVm()
            ActCameraRecordVm::class.java -> ActCameraRecordVm()
            ActMainVm::class.java -> ActMainVm()
            SubScreenReactionsVm::class.java -> SubScreenReactionsVm()
            SubScreenQuoteCommentsVm::class.java -> SubScreenQuoteCommentsVm()
            SubScreenSubscriptionsVm::class.java -> SubScreenSubscriptionsVm()
            ActReactionsCarouselVm::class.java -> ActReactionsCarouselVm(args = args as ActReactionsCarouselVm.Args)
            else -> throw IllegalStateException("Trying to create unknown ViewModel")
        } as V
    }
}

inline fun <reified V : ViewModel> createViewModelFactory(): BaseViewModelsFactory<V, Serializable> =
    createViewModelFactory(args = null)

inline fun <reified V : ViewModel, A : Serializable> createViewModelFactory(args: A? = null): BaseViewModelsFactory<V, A> =
    BaseViewModelsFactory(
        viewModelClass = V::class.java,
        args = args
    )
