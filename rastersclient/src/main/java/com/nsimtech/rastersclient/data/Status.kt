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

import kotlinx.serialization.Serializable


/**
 * 
 * @param success 
 * @param message 
 */
@Serializable
data class Status (
    val success: kotlin.Boolean? = null,
    val message: kotlin.String? = null
) {

}

