package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.contract.IIotQueryOperations
import com.nsimtech.rastersclient.contract.INavigationOperations
import com.nsimtech.rastersclient.data.*
import kotlinx.coroutines.Deferred
import java.util.*

class NavigationOperations : INavigationOperations
{
   private lateinit var _iNavigationOperationsService : INavigationOperationsService

   constructor(definitionInterface : INavigationOperationsService)   {
      _iNavigationOperationsService = definitionInterface;
   }

   override suspend fun generate(request: NavigationRequest): Deferred<Navigation> {
      return _iNavigationOperationsService.generate(request);
   }
}