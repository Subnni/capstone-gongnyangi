package com.example.gongnyangi.network.apidata

import com.google.gson.annotations.SerializedName

//회원가입 및 CAT 테스트

data class SignUpRequest(
    @SerializedName("phone") val phone : String,
    @SerializedName("user_name") val userName : String,
    @SerializedName("school_level") val schoolLevel : String,
    @SerializedName("grade_level") val gradeLevel : Int
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
    @SerializedName("roadmap_name") val roadmapName: String,
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