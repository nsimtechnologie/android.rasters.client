package com.nsimtech.rastersclient

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nsimtech.rastersclient.service.IAuthOperations
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import com.nsimtech.rastersclient.data.RequestHeaders
import com.nsimtech.rastersclient.interceptor.StatusValidationInterceptor
import com.nsimtech.rastersclient.service.AuthOperations
import com.nsimtech.rastersclient.service.IAuthOperationsService
import kotlinx.coroutines.*
import kotlinx.serialization.json.JSON
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
//import okhttp3.logging.HttpLoggingInterceptor



open class RetrofitClientBase : IHttpClient
{
    private lateinit var _baseUri : String;

    private lateinit var _retrofitClient : Retrofit;
    private lateinit var _authOperations : IAuthOperations;
    private var _requestHeaders : RequestHeaders = RequestHeaders();

    var retrofitClient: Retrofit? = null
        get() = _retrofitClient;

    var baseUri: String = ""
        get() = _baseUri;

    var requestHeaders: RequestHeaders? = null
        get() = _requestHeaders;

    constructor(baseUri: String, organizationId: UUID, auth: String) : this(baseUri)
    {
        _requestHeaders.organization = organizationId.toString();
        _requestHeaders.authorization = auth;
    }

    constructor(baseUri: String)
    {
        var validationInterceptor = StatusValidationInterceptor();

        var clientBuilder = OkHttpClient().newBuilder()
            .addInterceptor(headersInterceptor)
            .addInterceptor(validationInterceptor);

        val contentType = MediaType.get("application/json")
        var client : OkHttpClient = clientBuilder.build();
        var JsonConverterFactory = Json.nonstrict.asConverterFactory(contentType);

        _retrofitClient = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUri)
            .addConverterFactory(JsonConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build();

        var iAuthOperations : IAuthOperationsService = _retrofitClient.create(IAuthOperationsService::class.java);
        _authOperations = AuthOperations(iAuthOperations);
        _baseUri = baseUri;
    }

    private val headersInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder();

        if(_requestHeaders.organization != "")
            request.addHeader("X-organization", _requestHeaders.organization);

        if(_requestHeaders.authorization != "")
            request.addHeader("Authorization", _requestHeaders.authorization);

        chain.proceed(request.build());
    }

    override suspend fun authenticateFromCredentials(username: String, password: String,scope:String): AuthenticationResponse
    {
        var response : AuthenticationResponse = AuthenticationResponse();

        runBlocking {
            response = _authOperations.authenticateFromCredentials("password", username, password, "rasters", scope).await();
        }

        _requestHeaders.authorization = "Bearer " + response.access_token;
        return response;
    }

    override suspend fun authenticateFromCredentials(username: String, password: String): AuthenticationResponse
    {
        var response : AuthenticationResponse = AuthenticationResponse();

        runBlocking {
            response = authenticateFromCredentials(username, password, "offline_access");
        }

        _requestHeaders.authorization = "Bearer " + response.access_token;
        return response;
    }

    override suspend fun authenticateFromBearerToken(bearerToken: String) {
        _requestHeaders.authorization = "Bearer " + bearerToken;
    }

    override suspend fun authenticateFromRefreshToken(refreshToken: String): AuthenticationResponse
    {
        var response : AuthenticationResponse = AuthenticationResponse();
        runBlocking {
            response = _authOperations.authenticateFromRefreshToken("refresh_token",refreshToken,"rasters").await();
        }

        _requestHeaders.authorization = "Bearer " + response.access_token;
        return response
    }

    fun dispose()
    {
    }

}