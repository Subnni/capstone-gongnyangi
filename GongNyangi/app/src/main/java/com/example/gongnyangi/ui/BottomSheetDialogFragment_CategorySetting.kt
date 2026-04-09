package com.example.gongnyangi.ui

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.gongnyangi.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

// MyBottomSheetFragment.kt
class BottomSheetDialogFragment_CategorySetting : BottomSheetDialogFragment() {

    var onCompleteSettingListener: (() -> Unit)? = null

    private lateinit var settingCompleteButton : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog__category__setting_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingCompleteButton = view.findViewById(R.id.settingCompleteButton)
        settingCompleteButton.setOnClickListener {
            //부모 프래그먼트가 정의한 동작 실행
            onCompleteSettingListener?.invoke()
        }
    }

    //다이얼로그 생성 시 시스템 세팅 변경
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                it.setBackgroundColor(Color.TRANSPARENT) //배경색 투명으로
                it.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT //태블릿에서의 너비 제한 방지
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED //펼침 상태로
            }
        }
        return dialog
    }
}