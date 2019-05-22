package com.nsimtech.rastersclient.service


import com.nsimtech.rastersclient.contract.IScopedSettingOperations
import com.nsimtech.rastersclient.data.Scope
import com.nsimtech.rastersclient.data.ScopeKey
import com.nsimtech.rastersclient.data.ScopedSetting
import kotlinx.coroutines.Deferred
import kotlinx.serialization.json.JsonObject

class ScopedSettingOperations : IScopedSettingOperations
{
    private lateinit var _scopedSettingOperationsService : IScopedSettingOperationsService
    constructor(definitionInterface : IScopedSettingOperationsService){
        _scopedSettingOperationsService = definitionInterface;
    }

    override suspend fun getById(scopeKey: ScopeKey): Deferred<ScopedSetting> {
        return _scopedSettingOperationsService.getById(scopeKey.scope!!.level!!,scopeKey.scope!!.value!!, scopeKey.key!!)
    }

    override suspend fun setById(scopeKey: ScopeKey, value: JsonObject): Deferred<ScopedSetting> {
        return _scopedSettingOperationsService.setById(scopeKey.scope!!.level!!,scopeKey.scope!!.value!!, scopeKey.key!!, value)
    }

    override suspend fun deleteById(scopeKey: ScopeKey): Deferred<Unit> {
        return _scopedSettingOperationsService.deleteById(scopeKey.scope!!.level!!,scopeKey.scope!!.value!!, scopeKey.key!!)
    }

    override suspend fun getByScope(scope: Scope): Deferred<List<ScopedSetting>> {
        return _scopedSettingOperationsService.getByScope(scope.level!!, scope.value!!)
    }
}