package com.example.gongnyangi.ui

import android.content.Context
import android.content.Intent
import android.health.connect.datatypes.units.Length
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R
import com.example.gongnyangi.network.RetrofitClient
import com.example.gongnyangi.network.apidata.HomeRequest
import com.example.gongnyangi.network.apidata.RoadMap
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    //UI 담을 변수
    //private lateinit var testText : TextView
    private lateinit var gotoCreateBook : ImageView
    private lateinit var gotoMyPageLL : LinearLayout
    private lateinit var gotoSetting : LinearLayout
    private lateinit var bookListLayout : LinearLayout
    private lateinit var ghostTextView : TextView
    private lateinit var ghostImageView : ImageView
    private lateinit var roadmapBox : LinearLayout
    private lateinit var userNameTextView : TextView
    private lateinit var userScoreTextView : TextView

    //레이아웃 생성
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //(activity as? MainActivity)?.setNavVisible(true)
        //상단 상태바 높이만큼 패딩 부여
        StatusBarUtils.addStatusBarPadding(view)

        //UI 연결
        userNameTextView = view.findViewById(R.id.userNameTextView)
        userScoreTextView = view.findViewById(R.id.userScoreTextView)
        gotoCreateBook = view.findViewById(R.id.gotoCreateBook)
        gotoMyPageLL = view.findViewById(R.id.gotoMyPageLL)
        gotoSetting = view.findViewById(R.id.gotoSetting)
        //testText = view.findViewById(R.id.testText)
        bookListLayout = view.findViewById(R.id.bookListLayout)
        ghostImageView = view.findViewById(R.id.ghostImageView)
        ghostTextView = view.findViewById(R.id.ghostTextView)
        roadmapBox = view.findViewById(R.id.roadmapBox)

        //사용자 정보 받아와 화면에 뿌리기
        loadData()

        //페이지 이동 버튼 리스너 연결
        gotoMyPageLL.setOnClickListener {
            findNavController().navigate(R.id.homeToMyPage)
        }
        gotoCreateBook.setOnClickListener {
            val intent = Intent(requireContext(), CreateBookActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadData(){
        var sharedPref = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        var userId = sharedPref.getInt("USER_ID", 0)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.loadUserData(HomeRequest(userId))
                if (response.isSuccessful) {
                    val userName = response.body()?.userName ?: "-"
                    val userScore = response.body()?.userScore ?: 0
                    val roadmap = response.body()?.roadmap ?: emptyList()

                    if(!userName.isNullOrBlank()){
                        userNameTextView.setText(userName)
                        userScoreTextView.setText(userScore.toString())
                    }
                    loadRoadMap(roadmap)

                } else {
                    Toast.makeText(requireContext(), "데이터를 가져오지 못했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    val roadmap = response.body()?.roadmap ?: emptyList()
                    loadRoadMap(roadmap)
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "오류 발생. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun loadRoadMap(roadmap : List<RoadMap>){
        bookListLayout.removeAllViews()
        //로드맵 값 담을 변수
        //임의 생성
        var totalRoadMap : Int = roadmap.size
        if(totalRoadMap>0){
            //필요없는 뷰 invisible
            ghostImageView.setVisibility(View.INVISIBLE)
            ghostTextView.setVisibility(View.INVISIBLE)

            //로드맵 리스트 생성
            for(i in 0 until totalRoadMap){
                val roadmapItemView = layoutInflater.inflate(R.layout.fragment_home_roadmap_item, bookListLayout, false)
                val titleTextView = roadmapItemView.findViewById<TextView>(R.id.titleTextView)
                val dateTextView = roadmapItemView.findViewById<TextView>(R.id.dateTextView)
                val popupTextView = roadmapItemView.findViewById<TextView>(R.id.popupTextView)
                val coverImage = roadmapItemView.findViewById<ImageView>(R.id.coverImage)

                titleTextView.text = roadmap[i].roadmapTitle
                dateTextView.text = roadmap[i].roadMapCreatedAt
                when(roadmap[i].coverImageIndex){
                    "ghost" -> coverImage.setBackgroundResource(R.drawable.ghost)
                    "heart" -> coverImage.setBackgroundResource(R.drawable.h2h)
                    "cat" -> coverImage.setBackgroundResource(R.drawable.gentle)
                }

                //리스너 연결
                popupTextView.setOnClickListener { v ->
                    //객체 생성
                    val popup = PopupMenu(requireContext(), v)
                    //xml 파일 연결
                    popup.menuInflater.inflate(R.menu.popup_roadmap, popup.menu)
                    //리스너 연결
                    popup.setOnMenuItemClickListener { item ->
                        when(item.itemId){
                            R.id.item_setting -> {
                                val categoryId = roadmap[i].categoryId
                                val categoryName = roadmap[i].categoryName
                                val bottomSheet_select = BottomSheetDialogFragment_CategorySelect.newInstance(categoryId, categoryName)

                                bottomSheet_select.onCompleteSelectListener = {
                                    bottomSheet_select.dismiss()
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
                                true
                            }
                            R.id.item_modify -> {
                                val bottomSheet_bookSetting = BottomSheetDialogFragment_BookSetting.newInstance(roadmap[i].roadmapTitle)

                                bottomSheet_bookSetting.onCompleteSelectListener = {
                                    bottomSheet_bookSetting.dismiss()
                                }
                                bottomSheet_bookSetting.show(parentFragmentManager, "BookSettingSheet")
                                true
                            }
                            R.id.item_delete -> {
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }

                //선택된 책을 isSelected로 변경
                roadmapItemView.setOnClickListener { clickedView ->

                    //뷰의 현재 상태 저장
                    var wasSelected = clickedView.isSelected

                    //부모 안의 모든 자식 뷰를 isSelected=false
                    for (j in 0 until bookListLayout.childCount) {
                        bookListLayout.getChildAt(j).isSelected = false
                    }

                    if(!wasSelected) clickedView.isSelected = true
                    else clickedView.isSelected = false
                }
                bookListLayout.addView(roadmapItemView)
            }
        }
        else{
            roadmapBox.setVisibility(View.INVISIBLE)
        }
    }

}