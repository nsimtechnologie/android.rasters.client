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
import com.squareup.moshi.Json
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable

/**
 * 
 * @param name 
 * @param theme 
 * @param type 
 * @param projection 
 * @param size 
 * @param description 
 * @param priority 
 * @param connectorId 
 */
@Serializable
data class LayerEntry (
    val name: kotlin.String,
    @ContextualSerialization
    val type: LayerEntry.Type,
    val theme: kotlin.String? = null,
    val projection: kotlin.String? = null,
    val size: kotlin.Double? = null,
    val description: kotlin.String? = null,
    val priority: kotlin.Int? = null,
    @Serializable(with = UUIDSerializer::class)
    val connectorId: java.util.UUID? = null
) {

    /**
    * 
    * Values: raster,vector,`annotation`,collect,iot
    */
    enum class Type(val value: kotlin.String){
    
        @Json(name = "Raster") raster("Raster"),
    
        @Json(name = "Vector") vector("Vector"),
    
        @Json(name = "Annotation") `annotation`("Annotation"),
    
        @Json(name = "Collect") collect("Collect"),
    
        @Json(name = "Iot") iot("Iot");
    
    }

}

