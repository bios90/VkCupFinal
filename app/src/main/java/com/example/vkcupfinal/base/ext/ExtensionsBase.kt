package com.example.vkcupfinal.base.ext

import android.view.View
import androidx.core.content.ContextCompat
import com.example.vkcupfinal.base.AppClass
import okhttp3.MultipartBody
import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

inline fun <T1 : Any, T2 : Any, R : Any> let2(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun Number.pxToDp() = this.toFloat().div(AppClass.app.resources.displayMetrics.density)

fun String.splitByWords() = this.split("\\s+".toRegex())

fun Float.getRoundedByRating(): Float {
    val bd = BigDecimal(this.toDouble()).setScale(1, RoundingMode.HALF_EVEN)
    return bd.toFloat()
}

fun <T> List<T>.update(item: T): List<T> {
    val itemIndex = indexOf(item)
    return if (itemIndex == -1) this.toList()
    else slice(0 until itemIndex) + item + slice(itemIndex + 1 until size)
}

fun <T> List<T>.replace(item: T, index: Int): List<T> {
    return if (index > this.size - 1) this.toList()
    else slice(0 until index) + item + slice(index + 1 until size)
}

val randomInt: Int
    get() = (1..Int.MAX_VALUE).random()

val smallRandomInt: Int
    get() = (1..1000).random()

inline fun <reified T> List<Any>.filterAndMap(): List<T> =
    this.filter { it is T }.map { it as T }

fun Toast(text: String?) =
    android.widget.Toast.makeText(AppClass.app, text ?: "NULL", android.widget.Toast.LENGTH_LONG)
        .show()

fun Date.formatToString(format: String): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this).toString()
}

fun Boolean?.safe(): Boolean = this == true

fun <T> T.toSet(): Set<T> = setOf(this)

fun Boolean.toVisibility(): Int = if (this) View.VISIBLE else View.GONE

fun getPluralsByApp(resId: Int, count: Int) = AppClass.app.resources.getQuantityString(resId, count)

fun getStringByApp(resId: Int) = AppClass.app.resources.getString(resId)
