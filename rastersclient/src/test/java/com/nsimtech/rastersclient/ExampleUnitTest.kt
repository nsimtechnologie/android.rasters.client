package com.nsimtech.rastersclient

import android.os.Build
import com.nsimtech.rastersclient.data.*
import com.nsimtech.rastersclient.data.Map
import com.nsimtech.rastersclient.dto.AuthenticationResponse
import com.nsimtech.rastersclient.exception.SimpleHttpResponseException
import kotlinx.coroutines.*
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.*
import kotlinx.serialization.parse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {

//    var _url : String = "http://192.168.111.81:8080";
//
//    var _username : String = "jbedard@nsimtech.com";
//    var _password : String = "jbedard01";
//    var _annotationLayerId : UUID = UUID.fromString("fbc0f761-0987-42fb-b2b6-372c211c8fe4");

    var _url : String = "https://backend.nsimtech.com:8443";
    var _password : String = "MErDQ95gKUr";
    var _username : String = "support@nsimtech.com";
    var _annotationLayerId : UUID = UUID.fromString("cab75d73-5b0c-4fe8-aa43-4adc26553b1a");

    var _mobileusername : String = "supportmobile@nsimtech.com";
    var _validPin : String = "123456";
    var _mapkey : UUID = UUID.fromString("ff07a38c-d18e-48f6-85cc-1b9cf146b575");
    var _organization : UUID = UUID.fromString("085c62bc-0769-4554-81ef-a1ba7ddcd552");
    var _iotLayerId : UUID = UUID.fromString("ef22eb20-b712-4167-b601-ebc425f968d1");
    var _connectorId : String = "13bb4a69-9759-473e-a7e0-d86f26851ff9";

    //Not Working Road until fix
//    var _roadId : String = "6abb4f841e77f322817d7ebf1655fd1e";
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
            Thread.sleep(2000)
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
            Thread.sleep(2000)
            user = imperclient.account.getMe().await();
        }

        assertTrue(user != null);
    }

    @Test
    fun rastersClient_renewToken()
    {
        val rasterClient = buildRasterClient();
        var auth : AuthenticationResponse = AuthenticationResponse();

        runBlocking {
            auth = rasterClient.authenticateFromCredentials(_username,_password);
        }


        var user : User? = null;
        runBlocking {
            Thread.sleep(70* 1000)
            user = rasterClient.account.getMe().await();
        }
        assertTrue(user != null);
    }

    @Test
    fun rastersClient_renewToken_asImpersonated()
    {
        var rasterClient : IRastersClient = RastersClient(_url)

        var deviceUser : User? = null;
        var user : User? = null;
        var renewUser : User? = null;

        runBlocking {
            rasterClient.authenticateFromCredentials(_mobileusername,_password);
            Thread.sleep(2000)
            deviceUser = rasterClient.account.getMe().await();
            rasterClient = rasterClient.asImpersonatedUser(_validPin);
            Thread.sleep(2000)
            user = rasterClient.account.getMe().await();
        }

        assertTrue(deviceUser!!.userName != user!!.userName)

        runBlocking {
            Thread.sleep(70 * 1000)
            renewUser = rasterClient.account.getMe().await();
        }

        assertTrue(user!!.userName == renewUser!!.userName)
    }

//    @Test
//    fun rastersClient_renewToken_asImpersonated3()
//    {
//        var rasterClient1 : IRastersClient = ExposedClient(_url)
//        var rasterClient2 : IRastersClient = ExposedClient(_url)
//        var rasterClient3  : IRastersClient = ExposedClient(_url)
//        var rasterClient4 : IRastersClient = ExposedClient(_url)
//
//        var deviceUser : User? = null;
//        var user1 : User? = null;
//        var user2 : User? = null;
//        var user3 : User? = null;
//        var user4 : User? = null;
//        var renewUser1 : User? = null;
//        var renewUser2 : User? = null;
//        var renewUser3 : User? = null;
//        var renewUser4 : User? = null;
//
//        runBlocking {
//            rasterClient1.authenticateFromCredentials("device","Device1");
//            deviceUser = rasterClient1.account.getMe().await();
//            rasterClient1 = rasterClient1.asImpersonatedUser("34568");
//            Thread.sleep(2000)
//            user1 = rasterClient1.account.getMe().await();
//
//            Thread.sleep(2000)
//            rasterClient2.authenticateFromCredentials("device","Device1");
//            rasterClient2 = rasterClient2.asImpersonatedUser("34569");
//            user2 = rasterClient2.account.getMe().await();
//
//            Thread.sleep(2000)
//            rasterClient3.authenticateFromCredentials("device","Device1");
//            rasterClient3 = rasterClient3.asImpersonatedUser("345670");
//            user3 = rasterClient3.account.getMe().await();
//
//            Thread.sleep(2000)
//            rasterClient4.authenticateFromCredentials("device","Device1");
//            rasterClient4 = rasterClient4.asImpersonatedUser("345671");
//            user4 = rasterClient4.account.getMe().await();
//        }
//
//        assertTrue(deviceUser!!.userName != user1!!.userName)
//        assertTrue(deviceUser!!.userName != user2!!.userName)
//        assertTrue(deviceUser!!.userName != user3!!.userName)
//        assertTrue(deviceUser!!.userName != user4!!.userName)
//
//        val loop1 = GlobalScope.async {
//            for (a in 1..10000) {
//                println("try: [$a]")
//
//                runBlocking {
//                    Thread.sleep(41 * 1000)
//                    try {
//                        renewUser1 = rasterClient1.account.getMe().await();
//                        assertTrue(user1!!.userName == renewUser1!!.userName)
//                    } catch (e: Exception) {
//                        println(e.message.toString())
//                    }
//                }
//            }
//        }
//
//        val loop2 = GlobalScope.async {
//            for (a in 1..10000) {
//                println("try: [$a]")
//
//                runBlocking {
//                    Thread.sleep(42 * 1000)
//                    try {
//                        renewUser2 = rasterClient2.account.getMe().await();
//                        assertTrue(user2!!.userName == renewUser2!!.userName)
//                    } catch (e: Exception) {
//                        println(e.message.toString())
//                    }
//                }
//            }
//        }
//
//        val loop3 = GlobalScope.async {
//            for (a in 1..10000) {
//                println("try: [$a]")
//
//                runBlocking {
//                    Thread.sleep(43 * 1000)
//                    try {
//                        renewUser3 = rasterClient3.account.getMe().await();
//                        assertTrue(user3!!.userName == renewUser3!!.userName)
//                    } catch (e: Exception) {
//                        println(e.message.toString())
//                    }
//                }
//            }
//        }
//
//        val loop4 = GlobalScope.async {
//            for (a in 1..10000) {
//                println("try: [$a]")
//
//                runBlocking {
//                    Thread.sleep(44 * 1000)
//                    try {
//                        renewUser4 = rasterClient4.account.getMe().await();
//                        assertTrue(user4!!.userName == renewUser4!!.userName)
//                    } catch (e: Exception) {
//                        println(e.message.toString())
//                    }
//                }
//            }
//        }
//
//        runBlocking {
//            loop1.await()
//            loop2.await()
//            loop3.await()
//            loop4.await()
//        }
//
//    }

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
    fun rastersClient_IotQueryOperations_IotData_GetById()
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
    fun rastersClient_IotQueryOperations_IotReceived_GetById()
    {
        val client = buildAuthRasterClient();

        var iotReceived : IotReceived? = null;
        var dataKey = IotDataKey("8030",UUID.fromString("e84ad208-4116-4288-a751-93a5164c933d"));
        var dataKeyWithStyle = IotByLayerKey(dataKey,_annotationLayerId);

        runBlocking {
            iotReceived = client.iotQuery.getById(dataKeyWithStyle).await();
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
    fun rastersClient_IotIngestionOperations_UpsertSingleIotDataWithNullGeometry()
    {
        var client = buildAuthRasterClient();

        var iotData = CreatePoint("my point1",-71.254028, 46.829853);


        runBlocking {
            var success = client.iotIngestion.upsertIotData(iotData).await();
        }

        iotData.geometry = null
        runBlocking {
            var success = client.iotIngestion.upsertIotData(iotData).await();
        }

        var iotReceived : IotData? = null;
        runBlocking {
            iotReceived = client.iotQuery.getById(iotData.id!!).await();
        }

        assertTrue(iotReceived!!.geometry != null);
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
        var progressList : List<JsonElement> = listOf(
            JsonLiteral(0),JsonLiteral("#00FF00"),
            JsonLiteral(0.5-0.1),JsonLiteral("#00FF00"),
            JsonLiteral(0.5),JsonLiteral("#FF0000"),
            JsonLiteral(1),JsonLiteral("#FF0000")
        )

        var currentStyle = (json { "gradient" to JsonArray(progressList)})
            .plus(json { "shape" to JsonLiteral("line-gradient")})

        iotReceived.style = JsonObject(currentStyle)
        iotReceived.key = IotByLayerKey(iotData1.id,iotData1.id!!.connectorId!!)

        runBlocking {
            client.iotIngestion.upsertIotReceived(iotReceived).await();
        }
    }

    @ImplicitReflectionSerializer
    @Test
    fun rastersClient_IotIngestionOperations_UpsertIotReceived_withNullGeometry() {

        var client = buildAuthRasterClient();

        var iotData1 :IotData = CreatePoint("my point2", -71.254028, 46.829853);

        var iotReceived : IotReceived = IotReceived();
        iotReceived.data = iotData1;
        iotReceived.key = IotByLayerKey(iotData1.id,_annotationLayerId);
        //iotReceived.style = json{"icon" to JsonLiteral("circle")};
        var progressList : List<JsonElement> = listOf(
            JsonLiteral(0),JsonLiteral("#00FF00"),
            JsonLiteral(0.5-0.1),JsonLiteral("#00FF00"),
            JsonLiteral(0.5),JsonLiteral("#FF0000"),
            JsonLiteral(1),JsonLiteral("#FF0000")
        )

        var currentStyle = (json { "gradient" to JsonArray(progressList)})
            .plus(json { "shape" to JsonLiteral("line-gradient")})

        iotReceived.style = JsonObject(currentStyle)
        iotReceived.key = IotByLayerKey(iotData1.id,iotData1.id!!.connectorId!!)

        val map: MutableList<Pair<String, JsonElement>> = mutableListOf()
        map.add(Pair("Name",JsonLiteral("Test")))

        iotReceived.data!!.data = JsonObject(map.toMap())
        iotReceived.data!!.metadata = JsonObject(map.toMap())

        runBlocking {
            client.iotIngestion.upsertIotReceived(iotReceived).await();
        }


        iotReceived.data!!.geometry = null
        iotReceived.data!!.data = null
        iotReceived.data!!.metadata = null

        runBlocking {
            client.iotIngestion.upsertIotReceived(iotReceived).await();
        }

        var iotReceived2 : IotReceived? = null;
        runBlocking {
            iotReceived2 = client.iotQuery.getById(IotByLayerKey(iotData1.id,_annotationLayerId)).await();
        }

        assertTrue(iotReceived2!!.data!!.geometry != null);
        assertTrue(iotReceived2!!.data!!.data != null);
        assertTrue(iotReceived2!!.data!!.metadata != null);
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

        var request : NavigationRequest = NavigationRequest(dataKey,_roadsLayerId, null,_roadLayerAttribute);
        var navigation : Navigation? = null;

        runBlocking {
            Thread.sleep(2000)
            navigation = client.navigation.generate(request).await();
        }

        assertTrue(navigation != null);
    }

    @Test
    fun rastersClient_Navigation_Generate2()
    {
        var client = buildAuthRasterClient();

        var link : FileLink? = null;
        var getLink : FileLink? = null;
        var dataKey = IotDataKey(_roadId,_annotationLayerId);

        var request : NavigationRequest = NavigationRequest(dataKey,_roadsLayerId, _roadsLayerId,_roadLayerAttribute);
        var navigation : Navigation? = null;

        runBlocking {
            Thread.sleep(2000)
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
            asset = client.assets.getAssetFromDeviceKey(IotDataKey("38a2bd13-2d9b-4454-9c36-d72a46c56d60",UUID.fromString(_connectorId))).await()
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
            val iotDataKey =IotDataKey("38a2bd13-2d9b-4454-9c36-d72a46c56d60",UUID.fromString(_connectorId))

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

    @Test
    fun rastersClient_ScopeSettingsFullCrud(){
        val rastersClient = buildAuthRasterClient()

        val maps = runBlocking {rastersClient.maps.getMaps().await()}

        var layer: Layer? = null
        maps.listIterator().forEach { map ->
            val layers = runBlocking { rastersClient.maps.getMapLayers(map.mapKey!!).await() }

            val l = layers.firstOrNull() { l -> l.type == Layer.Type.Iot }
            if (layer == null)
                layer = l
        }
        val settingKey1 = ScopeKey(Scope(Scope.ScopeLevel.Layer, layer!!.id!!), "unit-test-1");
        val settingKey2 = ScopeKey(Scope(Scope.ScopeLevel.Layer, layer!!.id!!), "unit-test-2");

        val map: MutableList<Pair<String, JsonElement>> = mutableListOf()
        map.add(Pair("key1",JsonLiteral("value1")))
        map.add(Pair("key2",JsonLiteral("value2")))
        val jPayload1 = JsonObject(map.toMap())

        map.add(Pair("key2",JsonLiteral("value2")))
        val jPayload2 = JsonObject(map.toMap())

        val setting1 = runBlocking { rastersClient.scopedSetting.setById(settingKey1, jPayload1).await()}
        assertTrue(setting1.id!!.key == settingKey1.key)

        val setting2 = runBlocking { rastersClient.scopedSetting.setById(settingKey2, jPayload2).await()}
        assertTrue(setting2.id!!.key == settingKey2.key)

        val readSetting1 =  runBlocking { rastersClient.scopedSetting.getById(settingKey1).await()}
        assertTrue(readSetting1.value.toString() == jPayload1.toString())

        var settings =  runBlocking { rastersClient.scopedSetting.getByScope(settingKey1.scope!!).await()}
        assertTrue(settings.count() == 2)

        runBlocking { rastersClient.scopedSetting.deleteById(settingKey1).await()}
        runBlocking { rastersClient.scopedSetting.deleteById(settingKey2).await()}

        settings =  runBlocking { rastersClient.scopedSetting.getByScope(settingKey1.scope!!).await()}
        assertTrue(settings.count() == 0)
    }

    @Test
    fun rastersClient_UpsertGpsPosition(){
        val connectorId = _connectorId
        val deviceId = UUID.randomUUID().toString()

        val deviceName =  "rastersClient_UpsertGpsPosition"

        val userId = _username
        val name = "name"

        val routeId = _roadId
        val routeName = "routeName"

        if (connectorId.isEmpty() ||
            deviceId.isEmpty() ||
            userId.isEmpty() ||
            name.isEmpty())
            return

        val map: MutableList<Pair<String, JsonElement>> = mutableListOf()
        map.add(Pair("Name",JsonLiteral(name)))

        map.add(Pair("UserName",JsonLiteral("Johnny Test")))
        map.add(Pair("UserId",JsonLiteral(userId)))


            val route: MutableList<Pair<String, JsonElement>> = mutableListOf()
            route.add(Pair("device_id",JsonLiteral(deviceId)))
            route.add(Pair("connector_id",JsonLiteral(_roadsLayerId.toString())))

            map.add(Pair("Route",JsonLiteral(routeName)))
            map.add(Pair("RouteId",JsonObject(route.toMap())))


        map.add(Pair("Altitude",JsonLiteral(Double.NaN)))
        map.add(Pair("Course",JsonLiteral(0f)))
        map.add(Pair("Speed",JsonLiteral(1000 * 3.6f * 100)) )
        map.add(Pair("Timestamp",JsonLiteral(Date().time)))
        map.add(Pair("HorizontalAccuracy",JsonLiteral(Float.NaN)))

        map.add(Pair("VerticalAccuracy",JsonLiteral(Float.NaN)))

        val jPayload = JsonObject(map.toMap())


        //var geometry = Geometry(coordinates = listOf(location.longitude, location.latitude), type = "Point")

        val point: MutableList<Pair<String, JsonElement>> = mutableListOf()
        point.add(Pair("type",JsonLiteral("Point")))
        point.add(Pair("coordinates",JsonArray(listOf(JsonLiteral(-71.288947f), JsonLiteral(46.831285f)))))
        val jGeometry = JsonObject(point.toMap())

        val dataKey = IotDataKey(deviceId, UUID.fromString(connectorId))

        val iotData = IotData(
            type = "GPS",
            id = dataKey,
            isFeature = false,
            isLocked = true,
            isShared = true,
            data = jPayload,
            date = Date(),
            deviceName = deviceName,
            geometry = jGeometry
        )

        val style: MutableList<Pair<String, JsonElement>> = mutableListOf()
        style.add(Pair("icon",JsonLiteral("circle")))
        style.add(Pair("icon-color",JsonLiteral("#283593")))
        style.add(Pair("icon-opacity",JsonLiteral(1.0f)))
        val jStyle = JsonObject(style.toMap())

        val iotReceived = IotReceived(
            data=iotData,
            key = IotByLayerKey(dataKey, UUID.fromString(connectorId)),
            style = jStyle
        )

        var rasterClient : IRastersClient = RastersClient(_url)
        runBlocking {
            rasterClient.authenticateFromCredentials(_mobileusername, _password);
        }
        rasterClient = rasterClient.asImpersonatedUser(_validPin);

        for (i in 1..30){
            Thread.sleep(2 * 1000)
            println("\uD83D\uDE80 [" + i + " of 30]")
            runBlocking {
                rasterClient.iotIngestion.upsertIotReceived(iotReceived).await();
            }
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