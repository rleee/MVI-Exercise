package com.drdlee.mviexercise_livedata.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Blog(

    @Expose
    @SerializedName("pk")
    var pk: Int?,

    @Expose
    @SerializedName("title")
    var title: String?,

    @Expose
    @SerializedName("image")
    var image: String?,

    @Expose
    @SerializedName("category")
    var category: String?
)