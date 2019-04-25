package com.nsimtech.rastersclient.contract

import com.nsimtech.rastersclient.data.Organization
import com.nsimtech.rastersclient.data.User
import kotlinx.coroutines.Deferred

interface  IOrganizationOperations {
    suspend fun getOrganizations(): Deferred<List<Organization>>
}