package com.nsimtech.rastersclient

import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call;
import retrofit2.http.*

interface IHttpClient
{
    /**
     * Authenticate using password grant type by providing a [username] and a [password].
     */
    suspend fun authenticateFromCredentials(username: String, password: String): AuthenticationResponse;

    suspend fun authenticateFromBearerToken(bearerToken: String);

    suspend fun authenticateFromRefreshToken(refreshToken: String): AuthenticationResponse;
}