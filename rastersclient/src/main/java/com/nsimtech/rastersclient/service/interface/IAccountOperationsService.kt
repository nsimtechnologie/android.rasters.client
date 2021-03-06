package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.data.User
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface IAccountOperationsService
{
    @GET("account/info")
    fun getMe(): Deferred<User>;
}