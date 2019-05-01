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

    var _url : String = "https://api.rasters.io";
    var _username : String = "{ Your username here }";
    var _mobileusername : String = "{ Your mobile username here }";
    var _validPin : String = "{ Your pin here }";
    var _password : String = "{ Your password here }";
    var _mapkey : UUID = UUID.fromString("{ Your mapkey here }");

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
