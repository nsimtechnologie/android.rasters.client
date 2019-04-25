package com.nsimtech.rastersclient.contract

import com.nsimtech.rastersclient.data.User
import kotlinx.coroutines.Deferred
import com.nsimtech.rastersclient.data.Layer
import java.util.*


interface  IMapOperations {
    suspend fun getMapLayers(mapkey: UUID): Deferred<List<Layer>>
    suspend fun getMaps(): Deferred<List<com.nsimtech.rastersclient.data.Map>>
}