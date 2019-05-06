package com.nsimtech.rastersclient.data

import kotlinx.serialization.Serializable

@Serializable
data class ImpersonatedUser (
    val user: User? = null,
    val tempPassword: String? = null
){}