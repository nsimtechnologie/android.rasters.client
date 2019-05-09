package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.contract.IIotQueryOperations
import com.nsimtech.rastersclient.data.*
import kotlinx.coroutines.Deferred
import java.util.*

class IotQueryOperations : IIotQueryOperations
{
   private lateinit var _iIotQueryOperationsService : IIotQueryOperationsService

   constructor(definitionInterface : IIotQueryOperationsService)   {
      _iIotQueryOperationsService = definitionInterface;
   }

   override suspend fun getByMap(mapkey: UUID): Deferred<List<IotReceived>> {
      return _iIotQueryOperationsService.getByMap(mapkey);
   }

   override suspend fun getById(key: IotDataKey): Deferred<IotData> {
      return _iIotQueryOperationsService.getById(key.connectorId!!,key.deviceId!!);
   }

   override suspend fun queryCurrent(query: IotQueryModel): Deferred<QueryResult<IotReceived>> {
      return _iIotQueryOperationsService.queryCurrent(query);
   }


}