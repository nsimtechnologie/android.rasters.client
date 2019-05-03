package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.contract.IAccountOperations
import com.nsimtech.rastersclient.contract.IIotIngestionOperations
import com.nsimtech.rastersclient.data.*
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import retrofit2.Call

class IotIngestionOperations : IIotIngestionOperations
{
   private lateinit var _iotIngestionOperationsService : IIotIngestionOperationsService

   constructor(definitionInterface : IIotIngestionOperationsService)   {
      _iotIngestionOperationsService = definitionInterface;
   }

   override suspend fun upsertIotData(data: IotData): Deferred<Unit> {
      return upsertIotData(listOf(data));
   }

   override suspend fun upsertIotData(data: List<IotData>):  Deferred<Unit> {
      return _iotIngestionOperationsService.upsertIotData(data);
   }

   override suspend fun upsertIotReceived(data: IotReceived): Deferred<Unit> {
      return upsertIotReceived(listOf(data));
   }

   override suspend fun upsertIotReceived(data: List<IotReceived>): Deferred<Unit> {
      return _iotIngestionOperationsService.upsertIotReceived(data);
   }

   override suspend fun addAttachment(deviceKey: IotDataKey, filename: String): Deferred<FileLink> {
      return _iotIngestionOperationsService.addAttachment(deviceKey.connector_id!!,deviceKey.device_id!!,filename);
   }

   override suspend fun getAttachment(deviceKey: IotDataKey, filename: String): Deferred<FileLink> {
      return _iotIngestionOperationsService.getAttachment(deviceKey.connector_id!!,deviceKey.device_id!!,filename);
   }

}