package com.nsimtech.rastersclient.contract

import com.nsimtech.rastersclient.data.IotDataKey
import com.nsimtech.rastersclient.data.IotQueryModel
import com.nsimtech.rastersclient.data.IotReceived
import com.nsimtech.rastersclient.data.QueryResult
import kotlinx.coroutines.Deferred
import java.util.*

interface IIotQueryOperations{
    suspend fun getByMap(mapkey : UUID): Deferred<List<IotReceived>>
    suspend fun getById(key : IotDataKey): Deferred<IotReceived>
    suspend fun queryCurrent(query : IotQueryModel): Deferred<QueryResult<IotReceived>>

}