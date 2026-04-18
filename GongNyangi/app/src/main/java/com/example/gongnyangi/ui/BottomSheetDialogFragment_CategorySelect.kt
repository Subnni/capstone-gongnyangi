package com.example.gongnyangi.ui

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.gongnyangi.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetDialogFragment_CategorySelect : BottomSheetDialogFragment() {

    //이름 변수
    private var categoryId : Int = -1
    private var categoryName : String =""
    //리스너
    var onCompleteSelectListener: (() -> Unit)? = null
    var onSettingSelectListener: (() -> Unit)? = null
    //UI 변수
    private lateinit var selectBtn : LinearLayout
    private lateinit var cancelBtn : ImageView
    private lateinit var gotoCategorySettingButton : LinearLayout

    //static함수(객체 생성 시 데이터 담는 역할
    companion object {
        fun newInstance(cId : Int, cName : String) : BottomSheetDialogFragment_CategorySelect {
            val fragment = BottomSheetDialogFragment_CategorySelect()
            val args = Bundle()
            args.putInt("ID", cId)
            args.putString("NAME", cName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog__category__select_list_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryId = arguments?.getInt("ID") ?: 0
        val categoryName = arguments?.getString("NAME") ?: ""

        selectBtn = view.findViewById(R.id.selectBtn)
        cancelBtn = view.findViewById(R.id.cancelBtn)
        gotoCategorySettingButton = view.findViewById(R.id.gotoCategorySettingButton)

        selectBtn.setOnClickListener {
            onCompleteSelectListener?.invoke()//부모 프래그먼트(BookCompleteFragment)가 정의한 동작 실행
        }
        cancelBtn.setOnClickListener {
            dismiss() //닫기
        }
        gotoCategorySettingButton.setOnClickListener {
            onSettingSelectListener?.invoke()
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