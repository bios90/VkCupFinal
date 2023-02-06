package com.example.vkcupfinal.data

import java.util.*

data class ModelSubscriptionItem(
    val id: Long,
    val text: String,
    val minSubscriptionDuration: Long?,
    val subscribersCount: Int,
    val userSubscriptionDate: Date?
)

fun ModelSubscriptionItem.isUserSubscribed() = userSubscriptionDate != null

fun ModelSubscriptionItem.isContentAvailable(): Boolean {
    return if (userSubscriptionDate == null) {
        false
    } else if (minSubscriptionDuration == null) {
        true
    } else {
        val diff = Date().time - userSubscriptionDate.time
        diff >= minSubscriptionDuration
    }
}

fun ModelSubscriptionItem.getDurationToAvailable(): Long? {
    minSubscriptionDuration ?: return 0
    userSubscriptionDate ?: return 0
    val diff = Date().time - userSubscriptionDate.time
    return minSubscriptionDuration - diff
}