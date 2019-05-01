package com.nsimtech.rastersclient.contract

import com.nsimtech.rastersclient.data.ImpersonatedUser
import com.nsimtech.rastersclient.data.User
import kotlinx.coroutines.Deferred

interface IUserOperations {
    suspend fun getImpersonatedUser(pin:String): Deferred<ImpersonatedUser>;
}