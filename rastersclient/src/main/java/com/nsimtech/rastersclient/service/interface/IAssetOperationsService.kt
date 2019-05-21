package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.data.Asset
import com.nsimtech.rastersclient.data.IotDataKey
import com.nsimtech.rastersclient.data.User
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface IAssetOperationsService
{
    @GET("assets")
    fun getAssets(): Deferred<List<Asset>>

    @GET("assets/{connectorId}/{deviceId}")
    fun getAssetFromDeviceKey(@Path("connectorId") connectorId: UUID, @Path("deviceId") deviceId: String ): Deferred<Asset>

    //Retrofit by default doesn't like delete with body. (validation crash)
    //@DELETE("assets/{assetId}/devices")
    @HTTP(method = "DELETE", path = "assets/{assetId}/devices", hasBody = true)
    fun removeDeviceFromAsset(@Path("assetId") assetId: UUID, @Body deviceId:IotDataKey): Deferred<Unit>

    @PUT("assets/{assetId}/devices")
    fun addAssetDevice(@Path("assetId") assetId: UUID, @Body deviceId:IotDataKey): Deferred<Unit>

}
