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

import com.nsimtech.rastersclient.data.IotDataKey

import com.squareup.moshi.Json
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable

/**
 * 
 * @param id 
 * @param filename 
 * @param status 
 * @param size 
 * @param lastUpdate 
 * @param snapshot 
 */
@Serializable
data class Attachment (
    val id: IotDataKey? = null,
    val filename: kotlin.String? = null,
    @ContextualSerialization
    val status: Attachment.Status? = null,
    val size: kotlin.Double? = null,
    val lastUpdate: String? = null,
    val snapshot: kotlin.String? = null
) {

    /**
    * 
    * Values: notReceived,received
    */
    enum class Status(val value: kotlin.String){
    
        @Json(name = "NotReceived") notReceived("NotReceived"),
    
        @Json(name = "Received") received("Received");
    
    }

}

