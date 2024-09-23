package com.example.login

import LoginRequest
import LoginResponse
import SignupRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("signup")
    fun signup(@Body signupRequest: SignupRequest): Call<SignupResponse>

    @POST("/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("/auth/check-token")
    fun checkToken(@Header("Authorization") token: String): Call<String>

    @DELETE("comments/delete/{commentId}")
    fun deleteComment(
        @Path("commentId") commentId: Long,
        @Header("Authorization") token: String // 인증 토큰 추가
    ): Call<Void>

}

data class SignupResponse(
    val message: String
)