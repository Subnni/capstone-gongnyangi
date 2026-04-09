package com.example.gongnyangi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.gongnyangi.R


class BookCancel_DialogFragment : DialogFragment() {

    //버튼 클릭 시 동작
    var onConfirmClickListener: (() -> Unit)? = null
    var onDismissListener: (() -> Unit)? = null
    //ui
    private lateinit var cancelCancel : ImageView
    private lateinit var confirmCancel : LinearLayout

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
        confirmCancel = view.findViewById(R.id.confirmCancel)

        cancelCancel.setOnClickListener {
            onDismissListener?.invoke()
            dismiss()
        }
        confirmCancel.setOnClickListener {
            onConfirmClickListener?.invoke()
            dismiss()
        }

    }

}