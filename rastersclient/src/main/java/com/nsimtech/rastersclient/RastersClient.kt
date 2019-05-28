package com.nsimtech.rastersclient

import com.nsimtech.rastersclient.contract.*
import com.nsimtech.rastersclient.data.ImpersonatedUser
import com.nsimtech.rastersclient.service.*
import kotlinx.coroutines.runBlocking
import java.util.*

class RastersClient: RetrofitClientBase, IRastersClient {

    private var _account: IAccountOperations;
    private var _asset: IAssetOperations;
    private var _map: IMapOperations;
    private var _organization: IOrganizationOperations;
    private var _user: IUserOperations;
    private var _iotQuery: IIotQueryOperations;
    private var _iotIngestion: IIotIngestionOperations;
    private var _navigation: INavigationOperations;
    private var _scopedSetting: IScopedSettingOperations;

    constructor(baseUri: String, organizationId: UUID, authToken: String) : this(baseUri) {}

    constructor(baseUri: String) : super(baseUri) {
        val iAccountOperations: IAccountOperationsService =
            retrofitClient!!.create(IAccountOperationsService::class.java);
        val iAssetOperations: IAssetOperationsService = retrofitClient!!.create(IAssetOperationsService::class.java);
        val iMapOperations: IMapOperationsService = retrofitClient!!.create(IMapOperationsService::class.java);
        val iOrganizationOperations: IOrganizationOperationsService =
            retrofitClient!!.create(IOrganizationOperationsService::class.java);
        val iUserOperations: IUserOperationsService = retrofitClient!!.create(IUserOperationsService::class.java);
        val iIotQueryOperations: IIotQueryOperationsService =
            retrofitClient!!.create(IIotQueryOperationsService::class.java);
        val iIotIngestionOperations: IIotIngestionOperationsService =
            retrofitClient!!.create(IIotIngestionOperationsService::class.java);
        val iNavigation: INavigationOperationsService =
            retrofitClient!!.create(INavigationOperationsService::class.java);
        val iSettingOperations: IScopedSettingOperationsService =
            retrofitClient!!.create(IScopedSettingOperationsService::class.java);

        _account = AccountOperations(iAccountOperations);
        _asset = AssetOperations(iAssetOperations);
        _map = MapOperations(iMapOperations);
        _organization = OrganizationOperations(iOrganizationOperations);
        _user = UserOperations(iUserOperations);
        _iotQuery = IotQueryOperations(iIotQueryOperations);
        _iotIngestion = IotIngestionOperations(iIotIngestionOperations);
        _navigation = NavigationOperations(iNavigation);
        _scopedSetting = ScopedSettingOperations(iSettingOperations);
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

    override var users: IUserOperations
        get() = _user
        set(value) {}

    override var iotQuery: IIotQueryOperations
        get() = _iotQuery
        set(value) {}

    override var iotIngestion: IIotIngestionOperations
        get() = _iotIngestion
        set(value) {}

    override var navigation: INavigationOperations
        get() = _navigation
        set(value) {}

    override var scopedSetting: IScopedSettingOperations
        get() = _scopedSetting
        set(value) {}


    override fun asOrganization(orgId: UUID): IRastersClient {
        return RastersClient(baseUri, orgId, requestHeaders!!.authorization);
    }

    override fun asImpersonatedUser(pin: String): IRastersClient {
        var impersonatedUser: ImpersonatedUser? = null;
        runBlocking {
            impersonatedUser = _user.getImpersonatedUser(pin).await();
        }
        val client: RastersClient = RastersClient(baseUri);

        if (impersonatedUser != null) {
            runBlocking {
                var response = client.authenticateFromCredentials(
                    impersonatedUser!!.user!!.userName,
                    impersonatedUser!!.tempPassword!!,
                    "impersonification"
                );
            }
        }
        return client;
    }
}