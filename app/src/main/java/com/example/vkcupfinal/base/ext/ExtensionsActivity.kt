package com.example.vkcupfinal.base.ext

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.vkcupfinal.base.ActivityStartData
import com.example.vkcupfinal.base.AppClass
import com.example.vkcupfinal.base.BaseActivity
import com.example.vkcupfinal.base.Consts
import com.example.vkcupfinal.base.vm.BaseViewModel
import java.io.Serializable

fun AppCompatActivity.addLifeCycleObserver(
    onCreate: (LifecycleOwner?) -> Unit = { },
    onStart: (LifecycleOwner?) -> Unit = { },
    onResume: (LifecycleOwner?) -> Unit = { },
    onPause: (LifecycleOwner?) -> Unit = { },
    onStop: (LifecycleOwner?) -> Unit = { },
    onDestroy: (LifecycleOwner?) -> Unit = { },
) = lifecycle.addObserver(
    object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) = onCreate.invoke(owner)
        override fun onStart(owner: LifecycleOwner) = onStart.invoke(owner)
        override fun onResume(owner: LifecycleOwner) = onResume.invoke(owner)
        override fun onPause(owner: LifecycleOwner) = onPause.invoke(owner)
        override fun onStop(owner: LifecycleOwner) = onStop.invoke(owner)
        override fun onDestroy(owner: LifecycleOwner) = onDestroy.invoke(owner)
    }
)

fun <State, Effects> subscribeState(
    act: BaseActivity,
    vm: BaseViewModel<State, Effects>,
    stateConsumer: (State) -> Unit,
    effectsConsumer: (Set<Effects>) -> Unit = {},
) {
    vm.stateResult.observe(act) { resultEvent ->
        val stateResult = resultEvent.getIfNotHandled()
        stateConsumer.invoke(stateResult.first)
        effectsConsumer.invoke(stateResult.second)
    }
    act.addLifeCycleObserver(
        onCreate = { vm.onCreate(act) },
        onResume = { vm.onResume(act) },
        onStart = { vm.onStart(act) },
        onPause = { vm.onPause(act) },
        onStop = { vm.onStop(act) },
        onDestroy = { vm.onDestroy(act) },
    )
}

fun Window.setStatusLightDark(isLight: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        decorView.windowInsetsController?.apply {
            if (isLight) {
                setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
            } else {
                setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
            }
        }
    } else {
        var flags = this.decorView.systemUiVisibility
        if (isLight) {
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        this.decorView.systemUiVisibility = flags
    }
}

fun Window.setNavBarLightDark(isLight: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        decorView.windowInsetsController?.apply {
            if (isLight) {
                setSystemBarsAppearance(
                    APPEARANCE_LIGHT_NAVIGATION_BARS,
                    APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            } else {
                setSystemBarsAppearance(0, APPEARANCE_LIGHT_NAVIGATION_BARS)
            }
        }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        var flags = this.decorView.systemUiVisibility
        if (isLight) {
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        } else {
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        this.decorView.systemUiVisibility = flags
    }
}

fun Intent.putArgs(args: Serializable) {
    putExtra(Consts.ARGS, args)
}

fun <T : Serializable> Intent.getArgs(): T? {
    return getSerializableExtra(Consts.ARGS) as? T
}

fun AppCompatActivity.getStartForResultData(): ActivityStartData? {
    return intent.getSerializableExtra(Consts.START_DATA) as? ActivityStartData
}

fun <T : Serializable> Intent.getResult(): T? {
    return getSerializableExtra(Consts.RESULT) as? T
}


fun <T : Serializable> AppCompatActivity.getArgs(): T? = intent?.getArgs()

fun <T : Serializable> Fragment.getArgs(): T? = arguments?.getSerializable(Consts.ARGS) as? T

fun Fragment.putArgs(args: Serializable) = Bundle().apply {
    putSerializable(Consts.ARGS, args)
    this@putArgs.arguments = this
}

/*
*    Bundle bundle = new Bundle();
bundle.putString("latitude", latitude);
bundle.putString("longitude", longitude);
bundle.putString("board_id", board_id);
* */


fun Intent.addClearAllPreviousFlags() {
    this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
}

fun Window.hideSystemUI() {
    this.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

    this.statusBarColor = Color.TRANSPARENT
    this.navigationBarColor = Color.TRANSPARENT
}

val AppCompatActivity.rootView
    get() = this.findViewById<View>(android.R.id.content).getRootView()

fun Dialog.makeFullScreen() {
    val params: ViewGroup.LayoutParams = this.window?.attributes ?: return
    params.width = ViewGroup.LayoutParams.MATCH_PARENT
    params.height = ViewGroup.LayoutParams.MATCH_PARENT
    this.window?.attributes = params as WindowManager.LayoutParams
}

fun Window.setStatusBarColorMy(color: Int) {
    this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    this.setStatusBarColor(color)
}

fun getStatusBarHeight(): Int {
    var result = 0
    val resources = AppClass.app.resources
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun getNavbarHeight(): Int {
    var result = 0
    val resources = AppClass.app.resources
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun View.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        left?.run { params.leftMargin = this }
        top?.run { params.topMargin = this }
        right?.run { params.rightMargin = this }
        bottom?.run { params.bottomMargin = this }
        requestLayout()
    }
}

fun dp2pxInt(dp: Float): Int {
    return dp2px(dp).toInt()
}

fun dp2px(dp: Float): Float {
    val r = Resources.getSystem()
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.displayMetrics)
}

fun px2dp(px: Float): Float {
    val r = Resources.getSystem()
    return px / r.getDisplayMetrics().density;
}

fun Dialog.setNavigationBarColor(color: Int) {
    val window = this.window ?: return
    val metrics = DisplayMetrics()
    window.getWindowManager().getDefaultDisplay().getMetrics(metrics)
    val dimDrawable = GradientDrawable()
    val navigationBarDrawable = GradientDrawable()
    navigationBarDrawable.shape = GradientDrawable.RECTANGLE
    navigationBarDrawable.setColor(color)
    val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)
    val windowBackground = LayerDrawable(layers)
    windowBackground.setLayerInsetTop(1, metrics.heightPixels)
    window.setBackgroundDrawable(windowBackground)
}

