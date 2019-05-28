package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.data.*
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface IIotQueryOperationsService
{
    @GET("IotStatuses/map/{mapkey}")
    fun getByMap(@Path("mapkey") mapkey: UUID): Deferred<List<IotReceived>>

    @GET("IotStatuses/{connectorId}/{deviceId}")
    fun getById(@Path("connectorId") connectorId: UUID,@Path("deviceId") deviceId: String ): Deferred<IotData>

    @GET("IotStatuses/{connectorId}/{deviceId}/{layerId}")
    fun getById(@Path("connectorId") connectorId: UUID,@Path("deviceId") deviceId: String,@Path("layerId") layerId: UUID  ): Deferred<IotReceived>

    @POST("IotStatuses/query/status")
    fun queryCurrent(@Body query : IotQueryModel): Deferred<QueryResult<IotReceived>>
}