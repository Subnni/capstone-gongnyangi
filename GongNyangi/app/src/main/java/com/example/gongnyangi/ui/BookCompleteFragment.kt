package com.example.gongnyangi.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gongnyangi.R


class BookCompleteFragment : Fragment() {

    private lateinit var textbookSaveButton : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_complete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //상단 상태바 높이만큼 패딩 부여
        StatusBarUtils.addStatusBarPadding(view)

        textbookSaveButton = view.findViewById(R.id.textbookSaveButton)

        textbookSaveButton.setOnClickListener {

            //이름 지정하는 바텀시트
            val bottomSheet_setting = BottomSheetDialogFragment_BookSetting()

            bottomSheet_setting.onCompleteSelectListener = {

                //첫 번째 바텀시트 닫기
                bottomSheet_setting.dismiss()
                //개념서의 카테고리를 지정하는 바텀시트
                val bottomSheet_select = BottomSheetDialogFragment_CategorySelect()

                bottomSheet_select.onCompleteSelectListener = {

                    // 두 번째 바텀시트 닫기
                    bottomSheet_select.dismiss()
                    //다이얼로그 팝업 띄우기
                    val dialog = BookSave_DialogFragment()
                    dialog.onConfirmClickListener = {
                        // 메인 액티비티 - 서재로 이동
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                    }
                    dialog.show(parentFragmentManager, "SaveDialog")

                }

                //카테고리 관리 버튼 누를 시 새 바텀시트를 위에 띄움
                bottomSheet_select.onSettingSelectListener = {
                    val settingSheet = BottomSheetDialogFragment_CategorySetting()

                    //바텀시트 안의 버튼 동작 정의
                    settingSheet.onCompleteSettingListener = {
                        settingSheet.dismiss()
                        //bottomSheet_select.refreshData() //데이터 리프레쉬
                    }

                    settingSheet.show(parentFragmentManager, "CategorySettingSheet")
                }

                bottomSheet_select.show(parentFragmentManager, "CategorySelectSheet")
            }
            bottomSheet_setting.show(parentFragmentManager, "CategorySelectSheet")
        }

    }
}