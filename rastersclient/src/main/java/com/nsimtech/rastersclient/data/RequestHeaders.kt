package com.nsimtech.rastersclient.data

import kotlinx.serialization.Serializable

@Serializable
class RequestHeaders
{
    var authorization: String = ""
    var organization: String = ""

}