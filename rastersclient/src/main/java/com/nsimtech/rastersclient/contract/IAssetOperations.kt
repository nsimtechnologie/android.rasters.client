package com.nsimtech.rastersclient.contract

import com.nsimtech.rastersclient.data.User
import kotlinx.coroutines.Deferred
import com.nsimtech.rastersclient.data.Asset
import com.nsimtech.rastersclient.data.IotDataKey
import kotlinx.serialization.PrimitiveKind
import java.util.*


interface  IAssetOperations {
    suspend fun getAssets(): Deferred<List<Asset>>
    suspend fun getAssetFromDeviceKey(deviceId: IotDataKey): Deferred<Asset>
    suspend fun removeDeviceFromAsset(assetId : UUID, deviceId: IotDataKey): Deferred<Unit>
    suspend fun addAssetDevice(assetId:UUID, deviceId: IotDataKey): Deferred<Unit>
}