package com.example.gongnyangi.ui

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("user_id") val userId : Int,
    @SerializedName("phone") val phone : String,
    @SerializedName("user_name") val userName : String,
    @SerializedName("grade_level") val gradeLevel : String,
    @SerializedName("user_score") val userScore : Int,
    @SerializedName("created_at") val createdAt : String,
)

