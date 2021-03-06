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

import com.nsimtech.rastersclient.data.IotReceived
import com.nsimtech.rastersclient.data.RuleExecutionStateId

/**
 * 
 * @param id 
 * @param lastExecutionState 
 * @param lastExecution 
 * @param extra 
 */
data class RuleExecutionState (
    val id: RuleExecutionStateId? = null,
    val lastExecutionState: kotlin.Boolean? = null,
    val lastExecution: String? = null,
    val extra: kotlin.Array<IotReceived>? = null
) {

}

