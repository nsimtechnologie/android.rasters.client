package com.nsimtech.rastersclient.contract

import com.nsimtech.rastersclient.data.*
import kotlinx.coroutines.Deferred
import retrofit2.Call

interface IIotIngestionOperations{

    suspend fun upsertIotData(data : IotData): Deferred<Unit>
    suspend fun upsertIotData(data : List<IotData>): Deferred<Unit>

    suspend fun upsertIotReceived(data : IotReceived): Deferred<Unit>
    suspend fun upsertIotReceived(data : List<IotReceived>): Deferred<Unit>

    suspend fun addAttachment(deviceKey : IotDataKey, filename:String): Deferred<FileLink>
    suspend fun getAttachment(deviceKey : IotDataKey, filename:String): Deferred<FileLink>
}