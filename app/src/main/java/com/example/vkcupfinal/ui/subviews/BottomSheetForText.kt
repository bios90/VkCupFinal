package com.example.vkcupfinal.ui.subviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.FragmentManager
import com.example.vkcupfinal.R
import com.example.vkcupfinal.base.ext.getArgs
import com.example.vkcupfinal.base.ext.putArgs
import com.example.vkcupfinal.base.ext.setNavigationBarColor
import com.example.vkcupfinal.ui.theme.AppTheme
import com.example.vkcupformats.ui.common.alignStart
import com.example.vkcupformats.ui.common.appClickable
import com.example.vkcupformats.ui.common.top
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable

class BottomSheetForText : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "BottomSheetForText"

        fun show(fm: FragmentManager, args: Args) {
            BottomSheetForText()
                .apply {
                    putArgs(args)
                    this.show(fm, TAG)
                }
        }
    }

    data class Args(
        val title: String,
        val text: String
    ) : Serializable

    private val args by lazy { requireNotNull(getArgs<Args>()) }

    private lateinit var composeView: ComposeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onStart() {
        super.onStart()
        dialog?.setNavigationBarColor(AppTheme.color.Surface.toArgb())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        composeView = ComposeView(inflater.context)
        return composeView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        composeView.setContent {
            TextDialog(args = args, onDismissClick = { dismiss() })
        }
    }
}

@Composable
private fun TextDialog(args: BottomSheetForText.Args, onDismissClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(AppTheme.dimens.x4).top)
            .fillMaxWidth()
            .background(AppTheme.color.Surface)
            .padding(horizontal = AppTheme.dimens.x4)
    ) {
        AppSpacer(height = AppTheme.dimens.x3)
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = args.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                style = AppTheme.typography.SemiBoldXl
            )
            Image(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape)
                    .size(AppTheme.dimens.x7)
                    .appClickable(onClick = onDismissClick)
                    .padding(AppTheme.dimens.x1),
                painter = painterResource(id = R.drawable.ic_times),
                contentDescription = "ic_close",
                colorFilter = ColorFilter.tint(AppTheme.color.TextPrimary)
            )
        }
        AppSpacer(height = AppTheme.dimens.x3)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = args.text,
            style = AppTheme.typography.RegM.alignStart()
        )
        AppSpacer(
            modifier = Modifier.navigationBarsPadding(),
            height = AppTheme.dimens.x3
        )
    }
}
//
//@Preview
//@Composable
//private fun Preview() {
//    TextDialog(
//        args = BottomSheetForText.Args(
//            title = "Title",
//            text = "Some text description"
//        ),
//        onDismissClick = {}
//    )
//}