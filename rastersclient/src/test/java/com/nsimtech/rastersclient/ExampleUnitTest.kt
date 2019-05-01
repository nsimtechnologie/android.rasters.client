package com.nsimtech.rastersclient

import com.nsimtech.rastersclient.data.*
import com.nsimtech.rastersclient.data.Map
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {

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

    @Test
    fun rastersClient_AuthenticateFromRefreshToken()
    {
        val rasterClient = buildRasterClient();
        var auth : AuthenticationResponse = AuthenticationResponse();
        var auth2 : AuthenticationResponse = AuthenticationResponse();

        runBlocking {
            auth = rasterClient.authenticateFromCredentials(_username,_password);
        }

        runBlocking {
            auth2 = rasterClient.authenticateFromRefreshToken(auth.refresh_token);
        }

        assertTrue(auth2.access_token != "" )
    }

    @Test
    fun rastersClient_Account_getMe()
    {
        val client = buildAuthRasterClient();

        var user : User? = null;

        runBlocking {
            user = client.account.getMe().await();
        }

        assertTrue(user != null);
        assertEquals(user!!.userName,_username);
    }

    @Test
    fun rastersClient_Asset_getAssets()
    {
        val client = buildAuthRasterClient();

        var asset : List<Asset>? = null;

        runBlocking {
            asset = client.assets.getAssets().await();
        }

        assertTrue(asset != null);
    }


    @Test
    fun rastersClient_Map_getMaps()
    {
        val client = buildAuthRasterClient();

        var maps : List<Map>? = null;

        runBlocking {
            maps = client.maps.getMaps().await();
        }

        assertTrue(maps != null);
    }


    @Test
    fun rastersClient_Map_getMapLayers()
    {
        val client = buildAuthRasterClient();

        var layers : List<Layer>? = null;

        runBlocking {
            layers = client.maps.getMapLayers(_mapkey).await();
        }

        assertTrue(layers != null);
    }


    @Test
    fun rastersClient_Map_getOrganization()
    {
        val client = buildAuthRasterClient();

        var organizations : List<Organization>? = null;

        runBlocking {
            organizations = client.organizations.getOrganizations().await();
        }

        assertTrue(organizations != null);
    }

    @Test
    fun rastersClient_UsersOperations_WithExistingPin_ShouldReturnUserInfoAndTempPassword()
    {
        val client = buildRasterClient();

        runBlocking {
            client.authenticateFromCredentials(_mobileusername,_password);
        }

        var impersonatedUser:ImpersonatedUser? = null;

        runBlocking {
            impersonatedUser = client.users.getImpersonatedUser(_validPin).await();
        }

        assertTrue(impersonatedUser != null);
    }

    @Test
    fun rastersClient_AsImpersonatedUser()
    {
        var client = buildRasterClient();

        runBlocking {
            client.authenticateFromCredentials(_mobileusername,_password);
        }

        val imperclient = client.asImpersonatedUser(_validPin);

        var user : User? = null;

        runBlocking {
            user = imperclient.account.getMe().await();
        }

        assertTrue(user != null);
        assertEquals(user!!.pin,_validPin);
    }

    private fun buildRasterClient(): RastersClient {
        return RastersClient(_url)
    }

    private fun buildAuthRasterClient(): RastersClient {
        var rastersClient = RastersClient(_url)

        runBlocking {
            rastersClient.authenticateFromCredentials(_username,_password);
        }

        return rastersClient;
    }
}