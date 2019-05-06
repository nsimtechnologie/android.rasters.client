package com.nsimtech.rastersclient.data

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    var address1 : String? = null,
    var address2: String? = null,
    var country: String? = null,
    var state: String? = null,
    var city: String? = null,
    var zipCode: String? = null)
{

}