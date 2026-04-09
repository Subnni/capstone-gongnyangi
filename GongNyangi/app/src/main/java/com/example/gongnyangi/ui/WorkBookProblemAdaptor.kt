package com.example.gongnyangi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gongnyangi.R

class WorkBookProblemAdaptor(private val itemList : List<String>) : RecyclerView.Adapter<WorkBookProblemAdaptor.WorkBookProblemViewHolder>(){

    //현재 선택된 번호 저장 변수
    private var selectedPosition = 0
    //뷰 바구니
    class WorkBookProblemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val textView : TextView = view.findViewById(R.id.problemButtonTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkBookProblemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_workbook_problem_button, parent, false) // 여기에 만든 XML 이름 넣기
        return WorkBookProblemViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkBookProblemViewHolder, position: Int) {
        holder.textView.text = itemList[position]
        holder.textView.isSelected = (position == selectedPosition)

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition //이전 위치 저장
            selectedPosition = position
            //변경된 부분 다시 그리기
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            // 클릭 시 다른 동작 여기에 추가하기
            //마지막 문제는 버튼 추가

        }
    }

    override fun getItemCount() = itemList.size

}