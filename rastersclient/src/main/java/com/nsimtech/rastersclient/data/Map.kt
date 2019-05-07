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

import com.nsimtech.rastersclient.data.Serializer.DateSerializer
import com.nsimtech.rastersclient.data.Serializer.UUIDSerializer
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import java.util.*


/**
 * 
 * @param mapKey 
 * @param organizationId 
 * @param name 
 * @param creationDate 
 * @param updatedDate 
 * @param isShared 
 * @param layerCount 
 * @param description 
 * @param mapLayers 
 */
@Serializable
data class Map (
    val name: kotlin.String?,
    @Serializable(with = UUIDSerializer::class)
    val mapKey: java.util.UUID? = null,
    @Serializable(with = UUIDSerializer::class)
    val organizationId: java.util.UUID? = null,

    @Serializable(with = DateSerializer::class)
    val creationDate: Date? = null,

    @Serializable(with = DateSerializer::class)
    val updatedDate: Date? = null,

    val isShared: kotlin.Boolean? = null,
    val layerCount: kotlin.Int? = null,
    val description: kotlin.String? = null,
    val mapLayers: kotlin.Array<MapLayer>? = null
) {

}

