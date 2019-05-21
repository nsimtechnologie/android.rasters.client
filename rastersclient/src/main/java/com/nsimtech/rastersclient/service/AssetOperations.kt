package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.contract.IAccountOperations
import com.nsimtech.rastersclient.contract.IAssetOperations
import com.nsimtech.rastersclient.data.Asset
import com.nsimtech.rastersclient.data.IotDataKey
import com.nsimtech.rastersclient.data.User
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import java.util.*

class AssetOperations : IAssetOperations
{
   private lateinit var _assetOperationsService : IAssetOperationsService
   constructor(definitionInterface : IAssetOperationsService)   {
      _assetOperationsService = definitionInterface;
   }

   override suspend fun getAssets(): Deferred<List<Asset>> {
      return _assetOperationsService.getAssets();
   }

   override suspend fun getAssetFromDeviceKey(deviceId: IotDataKey): Deferred<Asset> {
      return _assetOperationsService.getAssetFromDeviceKey(deviceId.connectorId!!, deviceId.deviceId!!)
   }

   override suspend fun addAssetDevice(assetId: UUID, deviceId: IotDataKey): Deferred<Unit> {
      return _assetOperationsService.addAssetDevice(assetId, deviceId)
   }

   override suspend fun removeDeviceFromAsset(assetId: UUID, deviceId: IotDataKey): Deferred<Unit> {
      return _assetOperationsService.removeDeviceFromAsset(assetId,deviceId)
   }
}