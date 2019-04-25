package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.contract.IAccountOperations
import com.nsimtech.rastersclient.contract.IAssetOperations
import com.nsimtech.rastersclient.data.Asset
import com.nsimtech.rastersclient.data.User
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class AssetOperations : IAssetOperations
{
   private lateinit var _assetOperationsService : IAssetOperationsService
   constructor(definitionInterface : IAssetOperationsService)   {
      _assetOperationsService = definitionInterface;
   }

   override suspend fun getAssets(): Deferred<List<Asset>> {
      return _assetOperationsService.getAssets();
   }
}