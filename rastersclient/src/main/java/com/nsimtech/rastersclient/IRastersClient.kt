package com.nsimtech.rastersclient

import com.nsimtech.rastersclient.contract.*
import java.util.*

interface IRastersClient : IHttpClient
{
    fun asOrganization(orgId:UUID) : IRastersClient;
    fun asImpersonatedUser(pin:String) : IRastersClient;

    var account: IAccountOperations;
    var organizations: IOrganizationOperations;
    var assets: IAssetOperations;
    var maps: IMapOperations;
    var users: IUserOperations;

//    var layers: ILayerOperations;

//    var Events: IEventOperations;
//    var Connectors: IConnectorOperations;
//    var Geocoding: IGeocodingOperations;
//    var IotIngestion: IIotIngestionOperations;
//    var IotQuery: IIotQueryOperations;
//
//

}