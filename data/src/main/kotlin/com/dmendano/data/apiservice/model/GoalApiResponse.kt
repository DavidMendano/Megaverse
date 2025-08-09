package com.dmendano.data.apiservice.model

import com.google.gson.annotations.SerializedName

data class GoalApiResponse(

    @field:SerializedName("goal")
    val goal: List<List<String?>?>? = null
)
