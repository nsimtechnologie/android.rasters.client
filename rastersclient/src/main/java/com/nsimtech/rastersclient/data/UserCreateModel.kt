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


import com.squareup.moshi.Json
/**
 * 
 * @param email 
 * @param userName 
 * @param firstname 
 * @param lastname 
 * @param role 
 * @param password 
 * @param properties 
 */
data class UserCreateModel (
    val userName: kotlin.String,
    val firstname: kotlin.String,
    val lastname: kotlin.String,
    val role: UserCreateModel.Role,
    val email: kotlin.String? = null,
    val password: kotlin.String? = null,
    val properties: kotlin.collections.Map<kotlin.String, kotlin.String>? = null
) {

    /**
    * 
    * Values: none,`operator`,viewer,editor,device,admin,base,apikey,system
    */
    enum class Role(val value: kotlin.String){
    
        @Json(name = "None") none("None"),
    
        @Json(name = "Operator") `operator`("Operator"),
    
        @Json(name = "Viewer") viewer("Viewer"),
    
        @Json(name = "Editor") editor("Editor"),
    
        @Json(name = "Device") device("Device"),
    
        @Json(name = "Admin") admin("Admin"),
    
        @Json(name = "Base") base("Base"),
    
        @Json(name = "Apikey") apikey("Apikey"),
    
        @Json(name = "System") system("System");
    
    }

}

