package com.example.vkcupfinal.utils

import com.example.vkcupfinal.R
import com.example.vkcupfinal.base.ext.randomInt
import com.example.vkcupfinal.base.ext.smallRandomInt
import com.example.vkcupfinal.data.*
import com.google.common.primitives.Ints.max
import java.util.*
import kotlin.math.min

object MockHelper {
    fun getReactionItems(): List<ModelReactionItem> = (1..10).map {
        val likes1 = smallRandomInt
        val likes2 = smallRandomInt
        ModelReactionItem(
            id = randomInt.toLong(),
            reactions = getReactions().shuffled().take((1..10).random()),
            userReaction = null,
            likesCount = max(likes1, likes2),
            dislikeCount = min(likes1, likes2)
        )
    }

    fun getQuoteCommentItems(): List<ModelQuoteCommentItem> = listOf(
        ModelQuoteCommentItem(
            id = randomInt.toLong(),
            text = "Японцы вещи делают?",
            isCommentAllowed = false,
            likesCount = smallRandomInt,
            dislikeCount = smallRandomInt
        ).applyLikesDislikes(),
        ModelQuoteCommentItem(
            id = randomInt.toLong(),
            text = "Или все таки немцы?",
            likesCount = smallRandomInt,
            dislikeCount = smallRandomInt
        ).applyLikesDislikes(),
        ModelQuoteCommentItem(
            id = randomInt.toLong(),
            text = "За дзеном будущее нашей планеты. Согласны?",
            isCommentAllowed = false,
            likesCount = smallRandomInt,
            dislikeCount = smallRandomInt
        ).applyLikesDislikes(),
        ModelQuoteCommentItem(
            id = randomInt.toLong(),
            text = "Подписывайтесь ставьте лайки, пишите в комментариях какой момент вам понравился больше всего.",
            likesCount = smallRandomInt,
            dislikeCount = smallRandomInt
        ).applyLikesDislikes(),
        ModelQuoteCommentItem(
            id = randomInt.toLong(),
            text = "Я в своем познании настолько преисполнился, что я как будто бы уже сто триллионов миллиардов лет проживаю на триллионах и триллионах таких же планет, как эта Земля, мне этот мир абсолютно понятен, и я здесь ищу только одного - покоя, умиротворения и вот этой гармонии,",
            isLikeAllowed = false,
            likesCount = smallRandomInt,
            dislikeCount = smallRandomInt
        ).applyLikesDislikes(),
        ModelQuoteCommentItem(
            id = randomInt.toLong(),
            text = "Самая вкусная кухня итальянская. Согласны? Какое ваше любимое блюдо?",
            likesCount = smallRandomInt,
            dislikeCount = smallRandomInt
        ).applyLikesDislikes(),
    )

    fun getUserName(): String =
        listOf("Чебурашка", "СуперМэн", "ВинниПух", "Фродо", "Гена", "Буратино").random()

    fun getModelSubscriptionItems() = listOf(
        ModelSubscriptionItem(
            id = randomInt.toLong(),
            text = "Как похудеть на 20кг за неделю? Нужно всего лишь\nпробежать 100км, отжаться 1500 раз, присесть 1200 раз, отжать пресс 3000 раз.",
            minSubscriptionDuration = null,
            subscribersCount = smallRandomInt,
            userSubscriptionDate = null
        ),
        ModelSubscriptionItem(
            id = randomInt.toLong(),
            text = "В этой стране самые большие зарплаты.\nЭта страна - Вьетнам. Средняя зарплата 6000000 донг. Да да, целых 6 миллионов. Но если переводить на рубли то это около 17000 рублей.",
            minSubscriptionDuration = 1000 * 10,
            subscribersCount = smallRandomInt,
            userSubscriptionDate = null
        ),
        ModelSubscriptionItem(
            id = randomInt.toLong(),
            text = "Не выходи из комнаты, не совершай ошибку.\n" +
                    "Зачем тебе Солнце, если ты куришь Шипку?\n" +
                    "За дверью бессмысленно все, особенно — возглас счастья.\n" +
                    "Только в уборную — и сразу же возвращайся.",
            minSubscriptionDuration = 1000 * 90,
            subscribersCount = smallRandomInt,
            userSubscriptionDate = null
        ),
        ModelSubscriptionItem(
            id = randomInt.toLong(),
            text = "Вот скажи мне, американец, в чём сила? Разве в деньгах? Вот и брат говорит, что в деньгах. У тебя много денег, и чего? Я вот думаю, что сила в правде: у кого правда, тот и сильней! Вот ты обманул кого-то, денег нажил, и чего — ты сильней стал? Нет, не стал, потому что правды за тобой нету! А тот, кого обманул, за ним правда! Значит, он сильней!",
            minSubscriptionDuration = 1000 * 10,
            subscribersCount = smallRandomInt,
            userSubscriptionDate = null
        ),
        ModelSubscriptionItem(
            id = randomInt.toLong(),
            text = "Робот не может причинить вред человеку или своим бездействием допустить, чтобы человеку был причинён вред\n" +
                    "Робот должен повиноваться всем приказам, которые даёт человек, кроме тех случаев, когда эти приказы противоречат Первому Закону\n" +
                    "Робот должен заботиться о своей безопасности в той мере, в которой это не противоречит Первому или Второму Законам",
            minSubscriptionDuration = 1000 * 30,
            subscribersCount = smallRandomInt,
            userSubscriptionDate = null,
        ),
        ModelSubscriptionItem(
            id = randomInt.toLong(),
            text = "Мы покупаем вещи которые нам не нужны,\nза деньги которых у нас нет, чтобы впечатлить людей, которые нам не нравятся",
            minSubscriptionDuration = 1000 * 30,
            subscribersCount = smallRandomInt,
            userSubscriptionDate = null,
        )
    )
}

private fun getReactions(): List<ModelReaction> = listOf(
    ModelReaction(
        id = randomInt.toLong(),
        video = ObjectWithVideoUrl.fromString("file:///android_asset/about_alcohol.mp4"),
        preview = ImagePreview(resId = R.drawable.about_alcohol_preview, url = null),
        userName = "Инженер",
        like = TypeLike.LIKE,
        emoji = TypeEmoji.HUG
    ),
    ModelReaction(
        id = randomInt.toLong(),
        video = ObjectWithVideoUrl.fromString("file:///android_asset/about_work.mp4"),
        preview = ImagePreview(resId = R.drawable.about_work_preview, url = null),
        userName = "Инженер",
        like = TypeLike.DISLIKE,
        emoji = TypeEmoji.TEAR
    ),
    ModelReaction(
        id = randomInt.toLong(),
        video = ObjectWithVideoUrl.fromString("file:///android_asset/chips.mp4"),
        preview = ImagePreview(resId = R.drawable.chips_preview, url = null),
        userName = "RaybackTv",
        like = TypeLike.DISLIKE,
        emoji = TypeEmoji.WOW
    ),
    ModelReaction(
        id = randomInt.toLong(),
        video = ObjectWithVideoUrl.fromString("file:///android_asset/dr.mp4"),
        preview = ImagePreview(resId = R.drawable.dr_preview, url = null),
        userName = "Катамаранов",
        like = TypeLike.LIKE,
        emoji = TypeEmoji.LAUGH
    ),
    ModelReaction(
        id = randomInt.toLong(),
        video = ObjectWithVideoUrl.fromString("file:///android_asset/man_beach.mp4"),
        preview = ImagePreview(resId = R.drawable.man_beach_preview, url = null),
        userName = "Иван",
        like = TypeLike.DISLIKE,
        emoji = TypeEmoji.TEAR
    ),
)

private fun ModelQuoteCommentItem.applyLikesDislikes(): ModelQuoteCommentItem {
    val likes1 = smallRandomInt
    val likes2 = smallRandomInt
    return this.copy(
        likesCount = max(likes1, likes2),
        dislikeCount = min(likes1, likes2)
    )
}
