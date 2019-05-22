package com.nsimtech.rastersclient.data

import com.nsimtech.rastersclient.data.Serializer.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param scope
 * @param key
 */
@Serializable
data class ScopeKey (
    @SerialName("scope")
    var scope:Scope? =null,
    @SerialName("key")
    var key: String? = null
) {

}