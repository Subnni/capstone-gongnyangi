package com.example.gongnyangi.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R


class LoginFragment : Fragment() {

    private lateinit var gotoSignUpTextView: TextView
    private lateinit var gotoMainLayout : LinearLayout
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    
        gotoSignUpTextView = view.findViewById(R.id.gotoSignUpTextView)
        gotoMainLayout = view.findViewById(R.id.gotoMainLayout)
        
        gotoSignUpTextView.setOnClickListener {
            findNavController().navigate(R.id.gotoSignUp)
        }

        gotoMainLayout.setOnClickListener{
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }
}