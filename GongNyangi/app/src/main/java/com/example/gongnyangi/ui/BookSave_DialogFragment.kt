package com.example.gongnyangi.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.gongnyangi.R


class BookSave_DialogFragment : DialogFragment() {

    // 버튼 클릭 시 동작을 정의할 변수
    var onConfirmClickListener: (() -> Unit)? = null
    private lateinit var closeDialogTextView : LinearLayout
    private lateinit var closeDialogImageView : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_book_save, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 확인 버튼 클릭 시
        closeDialogTextView = view.findViewById(R.id.closeDialogTextView)
        closeDialogTextView.setOnClickListener {
            onConfirmClickListener?.invoke()
            dismiss()
        }
        //닫기 버튼 클릭 시
        closeDialogImageView = view.findViewById(R.id.closeDialogImageView)
        closeDialogImageView.setOnClickListener {
            onConfirmClickListener?.invoke()
            dismiss()
        }
    }
    //다이얼로그 생성 시 시스템 세팅 변경
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // 다이얼로그가 생성될 때 배경을 투명하게 설정
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

}