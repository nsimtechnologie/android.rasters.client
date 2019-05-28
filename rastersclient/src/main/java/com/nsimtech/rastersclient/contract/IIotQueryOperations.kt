package com.nsimtech.rastersclient.contract

import com.nsimtech.rastersclient.data.*
import kotlinx.coroutines.Deferred
import java.util.*

interface IIotQueryOperations{
    suspend fun getByMap(mapkey : UUID): Deferred<List<IotReceived>>
    suspend fun getById(key : IotDataKey): Deferred<IotData>
    suspend fun getById(key : IotByLayerKey): Deferred<IotReceived>
    suspend fun queryCurrent(query : IotQueryModel): Deferred<QueryResult<IotReceived>>

}