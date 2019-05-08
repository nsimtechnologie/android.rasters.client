package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.data.IotQueryModel
import com.nsimtech.rastersclient.data.Navigation
import com.nsimtech.rastersclient.data.NavigationRequest
import com.nsimtech.rastersclient.data.User
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface INavigationOperationsService
{
    @POST("navigation")
    fun generate(@Body request : NavigationRequest): Deferred<Navigation>;
}