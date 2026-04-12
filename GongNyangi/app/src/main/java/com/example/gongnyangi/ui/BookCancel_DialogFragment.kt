package com.example.gongnyangi.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.gongnyangi.R


class BookCancel_DialogFragment : DialogFragment() {

    //버튼 클릭 시 동작
    var onConfirmClickListener: (() -> Unit)? = null
    var onDismissListener: (() -> Unit)? = null
    //ui
    private lateinit var cancelCancel : ImageView
    private lateinit var cancelCancel2 : TextView
    private lateinit var confirmCancel : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_cancel__dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelCancel = view.findViewById(R.id.cancelCancel)
        cancelCancel2 = view.findViewById(R.id.cancelCancel2)
        confirmCancel = view.findViewById(R.id.confirmCancel)

        cancelCancel.setOnClickListener {
            onDismissListener?.invoke()
            dismiss()
        }
        cancelCancel2.setOnClickListener {
            onDismissListener?.invoke()
            dismiss()
        }
        confirmCancel.setOnClickListener {
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