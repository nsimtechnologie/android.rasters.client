package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IAuthOperationsService
{
    @FormUrlEncoded
    @POST("token")
    fun authenticateFromCredentials(@Field("grant_type") grant_type: String,
                                    @Field("username") username: String,
                                    @Field("password") password: String,
                                    @Field("client_id") client_id: String,
                                    @Field("scope") scope: String): Deferred<AuthenticationResponse>;

    @FormUrlEncoded
    @POST("token")
    fun authenticateFromRefreshToken(@Field("grant_type") grant_type: String,
                                    @Field("refresh_token") refresh_token: String,
                                    @Field("client_id") client_id: String): Deferred<AuthenticationResponse>;
}