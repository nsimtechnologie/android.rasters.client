package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.data.Asset
import com.nsimtech.rastersclient.data.Scope
import com.nsimtech.rastersclient.data.ScopeKey
import com.nsimtech.rastersclient.data.ScopedSetting
import kotlinx.coroutines.Deferred
import kotlinx.serialization.json.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface IScopedSettingOperationsService {

    @GET("settings/{level}/{value}/{key}")
    fun getById(@Path ("level") level: Scope.ScopeLevel, @Path ("value") value: UUID, @Path ("key") key :String): Deferred<ScopedSetting>

    @PUT("settings/{level}/{scopeValue}/{key}")
    fun setById(@Path ("level") level: Scope.ScopeLevel, @Path ("scopeValue") value: UUID, @Path ("key") key :String, @Body settingValue: JsonObject): Deferred<ScopedSetting>

    @DELETE("settings/{level}/{value}/{key}")
    fun deleteById(@Path ("level") level: Scope.ScopeLevel, @Path ("value") value: UUID, @Path ("key") key :String ): Deferred<Unit>

    @GET("settings/{level}/{value}")
    fun getByScope(@Path ("level") level: Scope.ScopeLevel, @Path ("value") value: UUID): Deferred<List<ScopedSetting>>

}