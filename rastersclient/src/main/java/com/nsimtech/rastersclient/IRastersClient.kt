package com.nsimtech.rastersclient

import com.nsimtech.rastersclient.contract.IAccountOperations
import com.nsimtech.rastersclient.contract.IAssetOperations
import com.nsimtech.rastersclient.contract.IMapOperations
import com.nsimtech.rastersclient.contract.IOrganizationOperations
import java.util.*

interface IRastersClient : IHttpClient
{
    var account: IAccountOperations;
    var organizations: IOrganizationOperations;
    fun asOrganization(orgId:UUID) : IRastersClient;
    var assets: IAssetOperations;
    var maps: IMapOperations;
//    var layers: ILayerOperations;

//    var Events: IEventOperations;
//    var Connectors: IConnectorOperations;
//    var Geocoding: IGeocodingOperations;
//    var IotIngestion: IIotIngestionOperations;
//    var IotQuery: IIotQueryOperations;
//
//
//    fun AsImpersonatedUser(pin:String) : IRastersClient;

}