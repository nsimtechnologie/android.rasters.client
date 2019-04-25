package com.nsimtech.rastersclient.contract

import com.nsimtech.rastersclient.data.User
import kotlinx.coroutines.Deferred

interface  ILayerOperations {
    suspend fun getMe(): Deferred<User>;
}