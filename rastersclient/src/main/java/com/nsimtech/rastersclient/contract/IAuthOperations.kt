package com.nsimtech.rastersclient.service

import android.os.AsyncTask
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

interface IAuthOperations
{
    fun authenticateFromCredentials(grant_type: String,username: String,password: String,client_id: String,scope: String): Deferred<AuthenticationResponse>;

    fun authenticateFromRefreshToken(grant_type: String,refresh_token: String,client_id: String): Deferred<AuthenticationResponse>;
}