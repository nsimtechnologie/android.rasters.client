package com.nsimtech.rastersclient.contract

import com.nsimtech.rastersclient.data.User
import kotlinx.coroutines.Deferred
import com.nsimtech.rastersclient.data.Asset



interface  IAssetOperations {
    suspend fun getAssets(): Deferred<List<Asset>>
}