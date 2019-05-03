package com.nsimtech.rastersclient

import android.content.Context
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest
{

    var _url : String = "https://backend.nsimtech.com:8443";
    var _username : String = "support@nsimtech.com";
    var _mobileusername : String = "supportmobile@nsimtech.com";
    var _validPin : String = "123456";
    var _password : String = "MErDQ95gKUr";
    var _mapkey : UUID = UUID.fromString("ff07a38c-d18e-48f6-85cc-1b9cf146b575");

    @Test
    fun rastersClient_AuthenticateByCredentials_ShouldReturnAccessTokenAndRefreshToken()
    {
        val rasterClient = buildRasterClient();
        var auth : AuthenticationResponse = AuthenticationResponse();

        runBlocking {
            auth =  rasterClient.authenticateFromCredentials(_username,_password);
        }

        assertTrue(auth.access_token != "" )
        assertTrue(auth.refresh_token != "")
    }


    private fun buildRasterClient(): RastersClient {
        return RastersClient(_url)
    }
}
