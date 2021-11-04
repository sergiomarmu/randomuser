package com.sermarmu.data.source.network.userrandom

import com.sermarmu.data.source.network.userrandom.io.UserListOutput
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserRandomApi {

    // region user paging
    @GET("api/")
    suspend fun retrieveUsers(
        @Query("results") results: Int = 40
    ): Response<UserListOutput>
    // endregion user paging

}