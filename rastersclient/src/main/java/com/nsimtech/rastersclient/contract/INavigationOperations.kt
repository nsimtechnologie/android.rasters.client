package com.nsimtech.rastersclient.contract

import com.nsimtech.rastersclient.data.Navigation
import com.nsimtech.rastersclient.data.NavigationRequest
import kotlinx.coroutines.Deferred
import okhttp3.Route

interface INavigationOperations {
    suspend fun generate(request : NavigationRequest): Deferred<Navigation>;
}