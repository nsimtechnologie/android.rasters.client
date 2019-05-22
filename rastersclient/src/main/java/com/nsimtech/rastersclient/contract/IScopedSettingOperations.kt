package com.nsimtech.rastersclient.contract

import com.nsimtech.rastersclient.data.Scope
import com.nsimtech.rastersclient.data.ScopeKey
import com.nsimtech.rastersclient.data.ScopedSetting
import kotlinx.coroutines.Deferred
import kotlinx.serialization.json.JsonObject
import java.util.*

interface IScopedSettingOperations {

    suspend fun getById(scopeKey: ScopeKey): Deferred<ScopedSetting>
    suspend fun setById(scopeKey: ScopeKey, value: JsonObject): Deferred<ScopedSetting>
    suspend fun deleteById(scopeKey: ScopeKey): Deferred<Unit>
    suspend fun getByScope(scope: Scope): Deferred<List<ScopedSetting>>

}