package com.sermarmu.data.source.network.io

import com.google.gson.annotations.SerializedName

data class UserListOutput(
    @SerializedName("results")
    val users: List<UserOutput>
)