package com.example.githubuser.retrofit


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiPoint {
    @GET("search/users")
    @Headers("Authorization: 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 token github")
    fun getUserSearch(@Query("q") query: String): Call<UserArray>


    @GET("users/{username}")
    @Headers("Authorization: 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 token github")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetail>


    @GET("users/{username}/followers")
    @Headers("Authorization: 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 token github")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>


    @GET("users/{username}/following")
    @Headers("Authorization: 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 token github")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>
}