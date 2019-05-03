package com.nsimtech.rastersclient.service
import com.nsimtech.rastersclient.data.*
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface IIotIngestionOperationsService
{
    @PUT("IotStatuses")
    fun upsertIotData(@Body data : List<IotData>): Deferred<Unit>

    @PUT("IotStatuses/withstyle")
    fun upsertIotReceived(@Body data : List<IotReceived>): Deferred<Unit>

    @PUT("IotStatuses/{connectorId}/{deviceId}/attachment/{filename}")
    fun addAttachment(@Path("connectorId") connectorId: UUID,@Path("deviceId") deviceId: String,@Path("filename") filename:String): Deferred<FileLink>

    @GET("IotStatuses/{connectorId}/{deviceId}/attachment/{filename}")
    fun getAttachment(@Path("connectorId") connectorId: UUID,@Path("deviceId") deviceId: String,@Path("filename") filename:String): Deferred<FileLink>
}