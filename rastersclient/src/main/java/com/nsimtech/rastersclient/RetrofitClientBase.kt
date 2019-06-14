package com.nsimtech.rastersclient

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import com.nsimtech.rastersclient.data.RequestHeaders
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import java.util.*
import com.nsimtech.rastersclient.exception.SimpleHttpResponseException
import com.nsimtech.rastersclient.service.*
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED


//import okhttp3.logging.HttpLoggingInterceptor



open class RetrofitClientBase : IHttpClient
{
    private lateinit var _baseUri : String;

    private lateinit var _retrofitClient : Retrofit;
    private lateinit var _authOperations : IAuthOperations;
    private var _requestHeaders : RequestHeaders = RequestHeaders();

    protected var refreshToken: String? = null
    protected var deviceRefreshToken: String? = null
    protected var impersonatePin: String? = null
    protected var getImpersonatedUser : (String) -> Pair<String, String> = { Pair("","")}

    var retrofitClient: Retrofit? = null
        get() = _retrofitClient;

    var baseUri: String = ""
        get() = _baseUri;

    var requestHeaders: RequestHeaders? = null
        get() = _requestHeaders;

    constructor(baseUri: String, organizationId: String, auth: String) : this(baseUri)
    {
        _requestHeaders.organization = organizationId;
        _requestHeaders.authorization = auth;
    }

    constructor(baseUri: String)
    {
        //var validationInterceptor = StatusValidationInterceptor();

        var clientBuilder = OkHttpClient().newBuilder()
            .addInterceptor(headersInterceptor)
            .addInterceptor(renewalInterceptor)
            //.addInterceptor(validationInterceptor)

//        if(BuildConfig.DEBUG) {
//            val logging = HttpLoggingInterceptor()
//            logging.level = HttpLoggingInterceptor.Level.BASIC;
//            clientBuilder.addInterceptor(logging);
//        }

        val contentType = MediaType.get("application/json")
        var client : OkHttpClient = clientBuilder.build();
        var JsonConverterFactory = Json(JsonConfiguration(strictMode = false, useArrayPolymorphism = true, encodeDefaults = false)).asConverterFactory(contentType);

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

    override suspend fun authenticateFromCredentials(username: String, password: String,scope:String): AuthenticationResponse
    {
        var response : AuthenticationResponse = AuthenticationResponse();

        runBlocking {
            response = _authOperations.authenticateFromCredentials("password", username, password, "rasters", scope).await();
        }
        refreshToken = response.refresh_token;

        _requestHeaders.authorization = "Bearer " + response.access_token;
        return response;
    }

    override suspend fun authenticateFromCredentials(username: String, password: String): AuthenticationResponse
    {
        var response : AuthenticationResponse = AuthenticationResponse();

        runBlocking {
            response = authenticateFromCredentials(username, password, "offline_access");
        }

        refreshToken = response.refresh_token;

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

        response.refresh_token = refreshToken;
        this.refreshToken = refreshToken;

        _requestHeaders.authorization = "Bearer " + response.access_token;

        return response
    }

    //region Interceptors
    private val headersInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder();

        if(_requestHeaders.organization != "")
            request.addHeader("X-organization", _requestHeaders.organization);

        if(_requestHeaders.authorization != "")
            request.addHeader("Authorization", _requestHeaders.authorization);

        chain.proceed(request.build());
    }

    private val renewalInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        var response = chain.proceed(originalRequest)

        val test = _requestHeaders.authorization

        if (response.code() == HTTP_UNAUTHORIZED) {
            if (!refreshToken.isNullOrEmpty()) {
                runBlocking {
                    try {
                        authenticateFromRefreshToken(refreshToken!!)
                    } catch (e: SimpleHttpResponseException) {
                        //unable to authenticate with renew token : throw unauthorized
                        ensureSuccessStatusCodeAsync(response)
                    }
                }

                //rebuild the request including the brand new access token!
                val requestBuilder = originalRequest.newBuilder()
                requestBuilder.header("Authorization", _requestHeaders.authorization)
                val newRequest = requestBuilder.build()

                //close original request
                if (response.body() != null)
                    response.close()

                //proceed with new request
                response = chain.proceed(newRequest)
            }
        }

        if (response.code() == HTTP_UNAUTHORIZED){
            if (!deviceRefreshToken.isNullOrEmpty()) {
                runBlocking {
                    try {
                        authenticateFromRefreshToken(deviceRefreshToken!!)
                    }
                    catch (e:SimpleHttpResponseException)
                    {
                        //unable to authenticate with renew token : throw unauthorized
                        ensureSuccessStatusCodeAsync(response)
                    }
                }

                val pin = impersonatePin
                if (!impersonatePin.isNullOrEmpty()) {
                    val pair = getImpersonatedUser.invoke(pin!!)
                    runBlocking {
                        authenticateFromCredentials(
                            pair.first,
                            pair.second,
                            "impersonification"
                        )
                    }
                }

                //rebuild the request including the brand new access token!
                val requestBuilder = originalRequest.newBuilder()
                requestBuilder.header("Authorization", _requestHeaders.authorization)
                val newRequest = requestBuilder.build()

                //close original request
                if (response.body() != null)
                    response.close()

                //proceed with new request
                response = chain.proceed(newRequest)
            }
        }

        ensureSuccessStatusCodeAsync(response)

        response
    }

    private fun ensureSuccessStatusCodeAsync(response: Response)
    {
        if (response.isSuccessful)
            return

        if (response.body() != null)
            response.close()

        var exception = SimpleHttpResponseException(response.code(), response.message(), response.body()!!.contentType())
        exception.requestBody = requestBodyToString(response.request())
        exception.responseBody = responseBodyToString(response)
        throw exception;
    }

    private fun requestBodyToString(request: Request): String {

        var stringBody = "";
        try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()?.writeTo(buffer)
            stringBody = buffer.readUtf8()
        } catch (e: IOException) {}
        finally {
            return stringBody
        }

    }

    private fun responseBodyToString(response: Response): String {

        var stringBody = "";
        try {
            stringBody = response.body()?.string()!!
        } catch (e: IOException) {
            var test = e;
        }
        finally {
            return stringBody
        }
    }
    //endregion Interceptors



    fun dispose()
    {
    }

}