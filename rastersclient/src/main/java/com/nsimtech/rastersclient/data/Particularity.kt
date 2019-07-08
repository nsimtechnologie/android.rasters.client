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

import com.nsimtech.rastersclient.data.Serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import java.util.*


/**
 * 
 * @param phoneNumber 
 * @param email 
 */
@Serializable
data class Particularity (
    @Serializable(with = UUIDSerializer::class)
    var id: UUID = UUID.randomUUID(),
    var location: List<Double>? = null,
    var text: String? = null,
    var distanceFromStart : Double? = null,
    var data: JsonObject? = null
) {

}

