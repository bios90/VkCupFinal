package com.example.vkcupfinal.utils

import com.example.vkcupfinal.R
import com.example.vkcupfinal.base.Consts.SPACE
import com.example.vkcupfinal.base.ext.getPluralsByApp
import java.util.concurrent.TimeUnit

object DateManager {
    val FORMAT_FILE_NAME = "yyyy-MM-dd-HH-mm-ss-SSS"

    fun formatDurationToString(duration: Long, isInCase: Boolean): String {
        val days = TimeUnit.MILLISECONDS.toDays(duration)
        val hours = TimeUnit.MILLISECONDS.toHours(duration) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60

        val daysStr = "$days ${
            getPluralsByApp(
                resId = if (isInCase) R.plurals.days_r else R.plurals.days,
                count = days.toInt()
            )
        }"
        val hoursStr = "$hours ${
            getPluralsByApp(
                resId = if (isInCase) R.plurals.hours_r else R.plurals.hours,
                count = hours.toInt()
            )
        }"
        val minutesStr = "$minutes ${
            getPluralsByApp(
                resId = if (isInCase) R.plurals.minutes_r else R.plurals.minutes,
                count = minutes.toInt()
            )
        }"
        val secondsStr = "$seconds ${
            getPluralsByApp(
                resId = if (isInCase) R.plurals.seconds_r else R.plurals.seconds,
                count = seconds.toInt()
            )
        }"

        return buildString {
            if (days > 0) {
                append(daysStr)
                append(SPACE)
                append(hoursStr)
            } else if (hours > 0) {
                append(hoursStr)
                append(SPACE)
                append(minutesStr)
            } else if (minutes > 0) {
                append(minutesStr)
                append(SPACE)
                append(secondsStr)
            } else {
                append(secondsStr)
            }
        }
    }
}