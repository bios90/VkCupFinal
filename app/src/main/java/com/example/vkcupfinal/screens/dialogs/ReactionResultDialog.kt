package com.example.vkcupfinal.screens.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Outline
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.vkcupfinal.R
import com.example.vkcupfinal.base.ext.*
import com.example.vkcupfinal.data.ModelReaction
import com.example.vkcupfinal.databinding.LaReactionResultDialogBinding
import com.example.vkcupfinal.ui.subviews.ReactionView
import com.example.vkcupfinal.utils.setDebounceClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReactionResultDialog(
    val reaction: ModelReaction,
    val onSaveClicked: (ModelReaction) -> Unit,
    val onCancelClicked: () -> Unit,
) : DialogFragment() {

    companion object {
        const val TAG = "ReactionResultDialog"
        fun show(
            fm: FragmentManager,
            reaction: ModelReaction,
            onSaveClicked: (ModelReaction) -> Unit,
            onCancelClicked: () -> Unit,
        ) {
            val dialog = ReactionResultDialog(
                reaction = reaction,
                onSaveClicked = onSaveClicked,
                onCancelClicked = onCancelClicked
            )
            dialog.show(fm, TAG)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancelClicked.invoke()
    }

    private lateinit var bndReactionResult: LaReactionResultDialogBinding
    private val reactionView by lazy {
        ReactionView(
            layoutInflater,
            bndReactionResult.lafForReaction
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bndReactionResult = LaReactionResultDialogBinding.inflate(inflater, container, false)
        bndReactionResult.lafForReaction.addView(reactionView.getRootView())
        return bndReactionResult.root
    }

    override fun onResume() {
        super.onResume()
        val color = ContextCompat.getColor(requireContext(), android.R.color.transparent)
        this.dialog?.makeFullScreen()
        this.dialog?.window?.setNavigationBarColor(color)
        this.dialog?.window?.setStatusBarColorMy(color)
        bndReactionResult.card.setMargins(
            bottom = getStatusBarHeight() + dp2pxInt(24f)
        )
        bndReactionResult.card.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(p0: View?, p1: Outline?) {
                p1?.setRoundRect(0, 0, p0?.width ?: 0, p0?.height ?: 0, dp2px(16f))
            }
        }
        bndReactionResult.card.clipToOutline = true
        reactionView.prepare()
        reactionView.play()
    }

    override fun onPause() {
        super.onPause()
        reactionView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        reactionView.stop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reactionView.setData(reaction)
        bndReactionResult.btnOk.setDebounceClickListener {
            onSaveClicked.invoke(reaction)
            dismiss()
        }
        bndReactionResult.btnCancel.setDebounceClickListener {
            onCancelClicked.invoke()
            dismiss()
        }
    }

}