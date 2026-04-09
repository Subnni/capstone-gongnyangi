package com.example.gongnyangi.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gongnyangi.R


class ViewWorkbookFragment : Fragment() {

    private lateinit var gotoBookcase : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_workbook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gotoBookcase = view.findViewById(R.id.gotoBookcase)
        gotoBookcase.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("targetFragment", "Bookcase")
            startActivity(intent)
        }


        //리사이클러뷰
        val problemList = mutableListOf<String>()
        for (i in 1..10) { //문제 10개 생성
            problemList.add(i.toString())
        }
        val adapter = WorkBookProblemAdaptor(problemList)
        val recyclerView = view.findViewById<RecyclerView>(R.id.problemButtonRecyclerView)
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

}