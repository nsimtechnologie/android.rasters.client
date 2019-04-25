package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.contract.IAccountOperations
import com.nsimtech.rastersclient.contract.IOrganizationOperations
import com.nsimtech.rastersclient.data.Organization
import com.nsimtech.rastersclient.data.User
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class OrganizationOperations : IOrganizationOperations
{
   private lateinit var _organizationOperationsService : IOrganizationOperationsService
   constructor(definitionInterface : IOrganizationOperationsService){
      _organizationOperationsService = definitionInterface;
   }

   override suspend fun getOrganizations(): Deferred<List<Organization>> {
      return _organizationOperationsService.getOrganizations();
   }

}