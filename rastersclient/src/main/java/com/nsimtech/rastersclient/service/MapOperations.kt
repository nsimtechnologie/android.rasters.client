package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.contract.IAccountOperations
import com.nsimtech.rastersclient.contract.IMapOperations
import com.nsimtech.rastersclient.data.Layer
import com.nsimtech.rastersclient.data.Map
import com.nsimtech.rastersclient.data.User
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import java.util.*

class MapOperations : IMapOperations
{
   private lateinit var _mapOperationsService : IMapOperationsService
   constructor(definitionInterface : IMapOperationsService){
      _mapOperationsService = definitionInterface;
   }

   override suspend fun getMapLayers(mapkey: UUID): Deferred<List<Layer>> {
      return _mapOperationsService.getMapLayers(mapkey);
   }

   override suspend fun getMaps(): Deferred<List<Map>> {
      return _mapOperationsService.getMaps();
   }
}