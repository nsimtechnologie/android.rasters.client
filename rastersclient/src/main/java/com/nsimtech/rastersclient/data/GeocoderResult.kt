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

import com.nsimtech.rastersclient.data.GeocoderResultData
import com.nsimtech.rastersclient.data.Status

/**
 * 
 * @param &#x60;data&#x60; 
 * @param sourceEntry 
 * @param status 
 */
data class GeocoderResult (
    val `data`: kotlin.Array<GeocoderResultData>? = null,
    val sourceEntry: kotlin.String? = null,
    val status: Status? = null
) {

}

