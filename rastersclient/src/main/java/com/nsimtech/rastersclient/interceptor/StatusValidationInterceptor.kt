package com.nsimtech.rastersclient.interceptor

import com.nsimtech.rastersclient.exception.SimpleHttpResponseException
import okhttp3.Interceptor
import okhttp3.Response

class StatusValidationInterceptor : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request();
        val response : Response = chain.proceed(request)

        ensureSuccessStatusCodeAsync(response);

        return response;
    }

    private fun ensureSuccessStatusCodeAsync(response: Response)
    {
        if (response.isSuccessful)
            return;

        if (response.body() != null)
            response.close();

        var exception = SimpleHttpResponseException(response.code(), response.message(), response.body()!!.contentType())
        exception.requestBody = response.request().body().toString();
        throw exception;
    }
}