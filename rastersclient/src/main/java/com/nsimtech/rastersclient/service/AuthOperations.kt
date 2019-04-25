package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred

class AuthOperations : IAuthOperations
{
   private lateinit var _authOperationsService : IAuthOperationsService
   constructor(definitionInterface : IAuthOperationsService){
      _authOperationsService = definitionInterface;
   }

   override fun authenticateFromCredentials(grant_type: String,username: String,password: String,client_id: String,scope: String): Deferred<AuthenticationResponse> {
       return _authOperationsService.authenticateFromCredentials(grant_type,username,password,client_id,scope);
   }

   override fun authenticateFromRefreshToken(grant_type: String,refresh_token: String,client_id: String): Deferred<AuthenticationResponse> {
      return _authOperationsService.authenticateFromRefreshToken(grant_type,refresh_token,client_id);
   }

}