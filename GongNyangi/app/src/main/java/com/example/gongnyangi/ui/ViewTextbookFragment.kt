package com.example.gongnyangi.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.gongnyangi.R


class ViewTextbookFragment : Fragment() {

    private lateinit var gotoBookcaseImageView : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_textbook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gotoBookcaseImageView = view.findViewById(R.id.gotoBookcase)
        gotoBookcaseImageView.setOnClickListener {

            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("targetFragment", "Bookcase")
            startActivity(intent)
        }

    }

}