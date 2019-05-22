package com.nsimtech.rastersclient.data

import com.nsimtech.rastersclient.data.Serializer.UUIDSerializer
import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectSerializer
import org.json.JSONObject
import java.util.*

//		[BsonId, JsonRequired]
//		public ScopeKey Id { get; set; }
//
//		public Guid OrganizationId { get; set; }
//
//		[JsonRequired, BsonSerializer(typeof(JObjectSerializer))]
//		public JObject Value { get; set; }


/**
 *
 * @param scopeKey
 * @param organizationId
 * @param value
 */
@Serializable
data class ScopedSetting (
    @SerialName("id")
    var id: ScopeKey? = null,
    @SerialName("organizationId")
    @Serializable(with = UUIDSerializer::class)
    var organizationId : UUID? = null,
    @SerialName("value")
    @Serializable(with = JsonObjectSerializer::class)
    var value : JsonObject? = null
) {



}


