package com.sermarmu.data.source.network

import com.sermarmu.data.source.network.io.UserListOutput
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    // region user paging
    @GET("api/")
    suspend fun retrieveUsers(
        @Query("results") results: Int = 40
    ): Response<UserListOutput>
    // endregion user paging

}