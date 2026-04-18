package com.example.gongnyangi.network.apidata

import com.google.gson.annotations.SerializedName

//회원가입 및 CAT 테스트

//[임시]회원가입 시 응답 데이터
data class ServerResponse(
    val success: Boolean,
    val message: String
)


data class SignUpRequest(
    @SerializedName("phone") val phone : String,
    @SerializedName("user_name") val userName : String,
    @SerializedName("school_level") val schoolLevel : String,
    @SerializedName("grade_level") val gradeLevel : String,
    @SerializedName("user_score") val userScore : Int? = 0
)

data class CATResponse(
    @SerializedName("problem_id") val problemId : Int,
    @SerializedName("question") val question : String,
    @SerializedName("passage") val passage : String,
    @SerializedName("question_type") val questionType : String,
    @SerializedName("choices") val choices : List<Choice>
)

data class Choice(
    @SerializedName("choice_id") val choiceId : Int,
    @SerializedName("choice_number") val choiceNumber : Int,
    @SerializedName("content") val content : String
)

data class CATRequest(
    @SerializedName("problem_id") val problemId : Int,
    @SerializedName("choice_id") val choiceId : Int
)

//로그인

data class LoginRequest(
    @SerializedName("phone") val phone : String
)

data class LoginResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("user_id") val userId : Int
)

//홈

data class HomeRequest(
    @SerializedName("user_id") val userId : Int
)

data class HomeResponse(
    @SerializedName("user_name") val userName : String,
    @SerializedName("user_score") val userScore : Int,
    @SerializedName("roadmap") val roadmap : List<RoadMap>
)

data class RoadMap(
    @SerializedName("category_id") val categoryId : Int,
    @SerializedName("category_name") val categoryName : String,
    @SerializedName("roadmap_id") val roadmapId: Int,
    @SerializedName("roadmap_title") val roadmapTitle: String,
    @SerializedName("cover_image_index") val coverImageIndex : String,
    @SerializedName("rm_created_at") val roadMapCreatedAt: String,
)


//홈 - 로드맵 눌렀을 시

data class CallRoadMapRequest(
    @SerializedName("roadmap_id") val roadmapId: Int
)

data class CallRoadMapResponse(
    @SerializedName("item") val item: List<Item>,
)

data class Item(
    @SerializedName("item_id") val itemId : Int,
    @SerializedName("content_type") val contentType : String,
    @SerializedName("order_index") val orderIndex : Int,
    @SerializedName("kc_concept") val kcConcept : String,
    @SerializedName("is_finished") val isFinished : Boolean
)

data class ConceptBookRequest(
    @SerializedName("item_id") val itemId : Int
)

//홈 - 책 이름 수정

data class ModifyBookRequest(
    @SerializedName("roadmap_id") val roadmapId: Int,
    @SerializedName("roadmap_name") val roadmapName: String
)

//홈 - 카테고리 위치 수정

data class SelectCategoryRequest(
    @SerializedName("roadmap_id") val roadmapId: Int,
    @SerializedName("category_id") val categoryId : Int
)

//홈 -  카테고리 이름 수정

data class ModifyCategoryRequest(
    @SerializedName("category_name") val categoryName: Int,
    @SerializedName("category_id") val categoryId : Int
)


//홈 - 로드맵 - 개념서 누를 시

data class CallConceptBookRequest(
    @SerializedName("item_id") val itemId: Int
)
data class CallConceptBookResponse(
    @SerializedName("concept_book_id") val conceptBookId: Int,
    @SerializedName("pdf_file_url") val pdfFileURL: Int,
    @SerializedName("pen") val pen: List<Pen>
)

//tbPenLocation수정 필요

data class Pen(
    @SerializedName("page_number") val pageNumber: Int,
    @SerializedName("pen_type") val penType: String,
    @SerializedName("tb_pen_location") val tbPenLocation: String
)


//홈 - 로드맵 - 문제집 누를 시

data class CallWorkBookRequest(
    @SerializedName("item_id") val itemId: Int
)
data class CallWorkBookResponse(
    @SerializedName("work_book_id") val workBookId: Int,
    @SerializedName("problem") val problem:  List<Problem>,
    @SerializedName("anno") val anno: List<Anno>
)
data class Problem(
    @SerializedName("workbook_problems_id") val workbookProblemId: Int,
    @SerializedName("order_index") val orderIndex: Int,
    @SerializedName("problem_id") val problemId: Int,
    @SerializedName("question") val question: String,
    @SerializedName("passage") val passage: String,
    @SerializedName("question_type") val questionType: String,
    @SerializedName("answer") val answer: String,
    @SerializedName("explanation") val explanation: String
)
//wbPenLocation수정 필요

data class Anno(
    @SerializedName("wb_annotation_id") val wbAnnotationId: Int,
    @SerializedName("wb_pen_location") val wbPenLocation: String
)


//문제집 뷰어 - 문제 누를 시

data class callProblemRequest(
    @SerializedName("problem_id") val problemId: Int
)
data class callProblemResponse(
    @SerializedName("choice_id") val choiceId: Int,
    @SerializedName("content") val content: String,
    @SerializedName("choice_number") val choiceNumber: Int
)

//문제집 뷰어 - 채점 누를 시
//이 기능은 나중에 구현하는걸로...




// 홈 - 로드맵 생성

data class makeRoadmapRequest(
    @SerializedName("user_id") val userId : Int
)
data class makeRoadmapResponse(
    @SerializedName("temp_item") val tempItem : List<TempItem>,
    @SerializedName("temp_kc") val tempKC : List<TempKC>
)
data class TempItem(
    @SerializedName("content_type") val contentType : String,
    @SerializedName("order_index") val orderIndex : Int
)

data class TempKC(
    @SerializedName("kc_concept") val kcConcept : String,
    @SerializedName("pdf_file_url") val pdf_file_url : String
)
data class CompleteRoadmapRequest(
    @SerializedName("roadmap_name") val roadmapName : String,
    @SerializedName("category_name") val categoryName : String
)


