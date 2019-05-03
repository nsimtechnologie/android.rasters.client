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
import io.data2viz.geojson.jackson.Point
import kotlinx.serialization.json.Json
import org.json.JSONObject

/**
 * 
 * @param id 
 * @param is_feature 
 * @param type 
 * @param device_name 
 * @param geometry 
 * @param date 
 * @param &#x60;data&#x60; 
 * @param bindings 
 */
data class IotData (
    var id: IotDataKey? = null,
    var is_feature: kotlin.Boolean? = null,
    var type: kotlin.String? = null,
    var device_name: kotlin.String? = null,
    var geometry: Geometry? = null,
    var date: String? = null,
    var `data`: JSONObject? = null,
    var bindings: String? = null
) {

}

