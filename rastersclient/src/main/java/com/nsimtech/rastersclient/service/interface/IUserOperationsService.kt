package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.data.ImpersonatedUser
import com.nsimtech.rastersclient.data.User
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface IUserOperationsService
{
    @GET("users/frompin")
    fun getImpersonatedUser(@Query("pin") pin: String): Deferred<ImpersonatedUser>;
}