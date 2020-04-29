package com.drdlee.mviexercise_livedata.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @SerializedName("username")
    var username: String?,

    @Expose
    @SerializedName("email")
    var email: String?,

    @Expose
    @SerializedName("image")
    var image: String?
)