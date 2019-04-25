package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.data.Layer
import com.nsimtech.rastersclient.data.User
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface IMapOperationsService
{
    @GET("maps")
    fun getMaps(): Deferred<List<com.nsimtech.rastersclient.data.Map>>

    @GET("maps/{mapkey}/layers")
    fun getMapLayers(@Path("mapkey") mapkey: UUID): Deferred<List<Layer>>
}