package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.contract.IAccountOperations
import com.nsimtech.rastersclient.data.User
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class AccountOperations : IAccountOperations
{
   private lateinit var _accountOperationsService : IAccountOperationsService
   constructor(definitionInterface : IAccountOperationsService)   {
      _accountOperationsService = definitionInterface;
   }

   override suspend fun getMe(): Deferred<User>   {
      return _accountOperationsService.getMe();
   }
}