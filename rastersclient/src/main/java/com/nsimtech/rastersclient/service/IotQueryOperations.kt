package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.contract.IIotQueryOperations
import com.nsimtech.rastersclient.data.IotDataKey
import com.nsimtech.rastersclient.data.IotQueryModel
import com.nsimtech.rastersclient.data.IotReceived
import com.nsimtech.rastersclient.data.QueryResult
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

   override suspend fun getById(key: IotDataKey): Deferred<IotReceived> {
      return _iIotQueryOperationsService.getById(key.connector_id!!,key.device_id!!);
   }

   override suspend fun queryCurrent(query: IotQueryModel): Deferred<QueryResult<IotReceived>> {
      return _iIotQueryOperationsService.queryCurrent(query);
   }


}