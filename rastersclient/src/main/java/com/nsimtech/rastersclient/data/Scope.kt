package com.nsimtech.rastersclient.data

import com.nsimtech.rastersclient.data.Serializer.UUIDSerializer
import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

/**
 *
 * @param scopeLevel
 * @param value
 */
@Serializable
data class Scope (
    @SerialName("level")
    val level: ScopeLevel? = null,
    @SerialName("value")
    @Serializable(with = UUIDSerializer::class)
    val value: UUID? =  null
) {

    override fun toString(): String {
        return "$level=$value";
    }

    /**
     *
     * Values: Organization,Map,Layer
     */
    enum class ScopeLevel(val value: kotlin.String){

        @Json(name = "Organization") Organization("Organization"),

        @Json(name = "Map") Map("Map"),

        @Json(name = "Layer") Layer("Layer"),

    }
}

