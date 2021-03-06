/**
* Rasters API
* No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
*
* OpenAPI spec version: v2
* 
*
* NOTE: This class is auto generated by the swagger code generator program.
* https://github.com/swagger-api/swagger-codegen.git
* Do not edit the class manually.
*/
package com.nsimtech.rastersclient.data

import com.google.gson.Gson
import com.nsimtech.rastersclient.data.Serializer.UUIDSerializer
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject
import org.json.JSONStringer


/**
 * 
 * @param layerId 
 * @param limit 
 * @param filters 
 */
@Serializable
data class IotQueryModel (
    @Serializable(with = UUIDSerializer::class)
    var layerId: java.util.UUID? = null,
    var limit: kotlin.Int? = null,
    var filters: JsonObject? = null
) {

}

