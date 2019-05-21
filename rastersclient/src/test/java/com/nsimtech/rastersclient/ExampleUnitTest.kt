package com.nsimtech.rastersclient

import com.google.gson.Gson
import com.nsimtech.rastersclient.data.*
import com.nsimtech.rastersclient.data.Geometry
import com.nsimtech.rastersclient.data.Map
import com.nsimtech.rastersclient.data.Serializer.DateSerializer
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import com.nsimtech.rastersclient.exception.SimpleHttpResponseException
import io.data2viz.geojson.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.*
import kotlinx.serialization.parse
import okhttp3.Request
import okhttp3.RequestBody
import org.junit.Assert
import org.junit.Test
import org.json.JSONObject
import org.json.JSONString

import org.junit.Assert.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {

    var _url : String = "https://backend.nsimtech.com:8443";
    //var _url : String = "http://192.168.111.57:8080";
    var _username : String = "support@nsimtech.com";
    var _mobileusername : String = "supportmobile@nsimtech.com";
    var _validPin : String = "123456";
    var _password : String = "MErDQ95gKUr";
    var _mapkey : UUID = UUID.fromString("ff07a38c-d18e-48f6-85cc-1b9cf146b575");
    var _organization : UUID = UUID.fromString("085c62bc-0769-4554-81ef-a1ba7ddcd552");
    var _iotLayerId : UUID = UUID.fromString("ef22eb20-b712-4167-b601-ebc425f968d1");
    var _annotationLayerId : UUID = UUID.fromString("cab75d73-5b0c-4fe8-aa43-4adc26553b1a");
    var _roadId : String = "1a13aa8859336cf2d74c5c038d46c3c1";
    var _roadsLayerId : UUID = UUID.fromString("bc4d97d0-0f3a-4e58-91d4-f806fbdc8d01");
    var _roadLayerAttribute : String = "NOM";

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
        assertTrue(auth2.refresh_token != "")
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
    }

    @Test
    fun rastersClient_IotQueryOperations_GetByMap()
    {
        val client = buildAuthRasterClient();

        var maps : List<Map>? = null;

        runBlocking {
            maps = client.maps.getMaps().await();
        }

        var map : Map? = maps!!.firstOrNull();

        var iotReceived : List<IotReceived>? = null;

        runBlocking {
            iotReceived = client.iotQuery.getByMap(map!!.mapKey!!).await();
        }

        assertTrue(iotReceived != null);
        assertTrue(iotReceived!!.count() > 0);
    }

    @Test
    fun rastersClient_IotQueryOperations_GetById()
    {
        var client = buildAuthRasterClient();

        var iotReceived : IotData? = null;
        var dataKey = IotDataKey("8030",UUID.fromString("e84ad208-4116-4288-a751-93a5164c933d"));

        runBlocking {
            iotReceived = client.iotQuery.getById(dataKey).await();
        }

        assertTrue(iotReceived != null);
    }

    @Test
    fun rastersClient_IotQueryOperations_QueryCurrent()
    {
        var client = buildAuthRasterClient();

        var queryResult : QueryResult<IotReceived>? = null;

        var query = IotQueryModel();
        query.limit = 5;
        query.filters = json{"data.current_status" to json{"\$eq" to JsonLiteral(2)}};  //JSONObject("{ 'data.current_status' : {\$eq : 2} }");
        query.layerId = _iotLayerId;

        runBlocking {
            queryResult = client.iotQuery.queryCurrent(query).await();
        }

        assertTrue(queryResult != null);
    }

    @ImplicitReflectionSerializer
    @Test
    fun rastersClient_IotIngestionOperations_UpsertSingleIotData()
    {
        var client = buildAuthRasterClient();

        var iotData = CreatePoint("my point1",-71.254028, 46.829853);
        runBlocking {
            var success = client.iotIngestion.upsertIotData(iotData).await();
        }

        var iotReceived : IotData? = null;
        runBlocking {
            iotReceived = client.iotQuery.getById(iotData.id!!).await();
        }

        assertTrue(iotReceived != null);
    }

    @ImplicitReflectionSerializer
    @Test
    fun rastersClient_IotIngestionOperations_UpsertMultipleIotData()
    {
        var client = buildAuthRasterClient();

        var iotData1 :IotData = CreatePoint("my point1",-71.254028, 46.829823);
        var iotData2 :IotData= CreatePoint("my point2",-71.254028, 46.829853);
        var iotList : List<IotData> = listOf(iotData1,iotData2);

        runBlocking {
            client.iotIngestion.upsertIotData(iotList).await();
        }

        var iot1Received : IotData? = null;
        var iot2Received : IotData? = null;
        runBlocking {
            iot1Received = client.iotQuery.getById(iotData1.id!!).await();
            iot2Received = client.iotQuery.getById(iotData2.id!!).await();
        }

        assertTrue(iot1Received != null);
        assertTrue(iot2Received != null);
    }


    @ImplicitReflectionSerializer
    @Test
    fun rastersClient_IotIngestionOperations_UpsertIotReceived() {

        var client = buildAuthRasterClient();

        var iotData1 :IotData = CreatePoint("my point2", -71.254028, 46.829853);

        var iotReceived : IotReceived = IotReceived();
        iotReceived.data = iotData1;
        iotReceived.key = IotByLayerKey(iotData1.id,_annotationLayerId);
        //iotReceived.style = json{"icon" to JsonLiteral("circle")};

        runBlocking {
            client.iotIngestion.upsertIotReceived(iotReceived).await();
        }
    }

    @Test
    fun rastersClient_IotIngestionOperations_AddGetAttachment()
    {
        var client = buildAuthRasterClient();

        var link : FileLink? = null;
        var getLink : FileLink? = null;
        var dataKey = IotDataKey("5123",UUID.fromString("e84ad208-4116-4288-a751-93a5164c933d"));

        runBlocking {
            link = client.iotIngestion.addAttachment(dataKey,"unit-test.png").await();
        }

        runBlocking {
            getLink = client.iotIngestion.getAttachment(dataKey,"unit-test.png").await();
        }

        assertTrue(link != null);
        assertTrue(getLink != null);
    }

    @Test
    fun rastersClient_Navigation_Generate()
    {
        var client = buildAuthRasterClient();

        var link : FileLink? = null;
        var getLink : FileLink? = null;
        var dataKey = IotDataKey(_roadId,_annotationLayerId);

        var request : NavigationRequest = NavigationRequest(dataKey,_roadsLayerId,_roadLayerAttribute);
        var navigation : Navigation? = null;

        runBlocking {
            navigation = client.navigation.generate(request).await();
        }

        assertTrue(navigation != null);
    }

    @ImplicitReflectionSerializer
    @Test
    fun rastersClient_Date_serialisation()
    {
        val date = Date()
        val iotData1 :IotData = CreatePoint("my point2", -71.254028, 46.829853);
        iotData1.date = date

        val simple = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        simple.timeZone = TimeZone.getTimeZone("UTC")
        val result = simple.format(date)

        val serialized =  Json.stringify(IotData.serializer(), iotData1)

        assertTrue(serialized.contains(result))
    }

    @Test
    fun rastersClient_getAssetFromDeviceKey()
    {
        val client = buildAuthRasterClient();
        var asset:Asset? = null
        runBlocking {
            asset = client.assets.getAssetFromDeviceKey(IotDataKey("1506",UUID.fromString("1438a1b0-4b80-42cc-9d8a-ba9398c56627"))).await()
        }
        assertTrue(asset != null)
    }

    @Test
    fun rastersClient_getRemoveAddAssetFromDeviceKey()
    {
        val client = buildAuthRasterClient();
        var asset:Asset? = null
        runBlocking {
            val assetId = UUID.fromString("c41f775d-3275-4024-8924-bbaea975fb3e")
            val iotDataKey =IotDataKey("1506",UUID.fromString("1438a1b0-4b80-42cc-9d8a-ba9398c56627"))

            try {
                client.assets.addAssetDevice(assetId, iotDataKey).await()
            }
            catch(e: SimpleHttpResponseException)
            {
                client.assets.removeDeviceFromAsset(assetId, iotDataKey).await()
            }

            client.assets.addAssetDevice(assetId, iotDataKey).await()
        }
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

    @ImplicitReflectionSerializer
    private fun CreatePoint(id:String, Lon:Double, Lat:Double ) : IotData
    {
        var iotData = IotData();
        iotData.id = IotDataKey(id,_annotationLayerId);
        iotData.isFeature = true;
        iotData.type = "unit-test";
        iotData.deviceName = id;
        iotData.geometry = Json.parse("{\"coordinates\" : [$Lon,$Lat], \"type\": \"Point\"}");
        iotData.date = GregorianCalendar(2019,4,3).time;
        iotData.`data` = json{"prop" to JsonLiteral("1234")}; //JSONObject("{'prop':1234}");
        iotData.bindings = null

        return iotData;
    }
}