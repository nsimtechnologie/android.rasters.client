package com.nsimtech.rastersclient

import com.nsimtech.rastersclient.contract.IAccountOperations
import com.nsimtech.rastersclient.contract.IAssetOperations
import com.nsimtech.rastersclient.contract.IMapOperations
import com.nsimtech.rastersclient.contract.IOrganizationOperations
import com.nsimtech.rastersclient.service.*
import java.util.*

class RastersClient: RetrofitClientBase, IRastersClient  {

    private var _account : IAccountOperations;
    private var _asset : IAssetOperations;
    private var _map : IMapOperations;
    private var _organization : IOrganizationOperations;

    constructor(baseUri: String, organizationId: UUID, authToken: String) : this(baseUri){}

    constructor(baseUri: String) : super (baseUri)
    {
        var iAccountOperations : IAccountOperationsService = retrofitClient!!.create(IAccountOperationsService::class.java);
        var iAssetOperations : IAssetOperationsService = retrofitClient!!.create(IAssetOperationsService::class.java);
        var iMapOperations : IMapOperationsService = retrofitClient!!.create(IMapOperationsService::class.java);
        var iOrganizationOperations : IOrganizationOperationsService = retrofitClient!!.create(IOrganizationOperationsService::class.java);

        _account = AccountOperations(iAccountOperations);
        _asset = AssetOperations(iAssetOperations);
        _map = MapOperations(iMapOperations);
        _organization = OrganizationOperations(iOrganizationOperations);
    }

    override var account: IAccountOperations
        get() = _account
        set(value) {}

    override var assets: IAssetOperations
        get() = _asset
        set(value) {}


    override var maps: IMapOperations
        get() = _map
        set(value) {}

    override var organizations: IOrganizationOperations
        get() = _organization
        set(value) {}

    override fun asOrganization(orgId: UUID): IRastersClient {
        return RastersClient(baseUri,orgId,requestHeaders!!.authorization);
    }


}