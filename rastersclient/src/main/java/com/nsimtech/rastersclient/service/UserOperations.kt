package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.contract.IAccountOperations
import com.nsimtech.rastersclient.contract.IUserOperations
import com.nsimtech.rastersclient.data.ImpersonatedUser
import com.nsimtech.rastersclient.data.User
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class UserOperations : IUserOperations
{
   private lateinit var _userOperationsService : IUserOperationsService
   constructor(definitionInterface : IUserOperationsService)   {
      _userOperationsService = definitionInterface;
   }

   override suspend fun getImpersonatedUser(pin:String): Deferred<ImpersonatedUser>
   {
      return _userOperationsService.getImpersonatedUser(pin);
   }
}