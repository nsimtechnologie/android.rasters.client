package com.nsimtech.rastersclient.data

data class ImpersonatedUser (
    val user: User? = null,
    val tempPassword: String? = null
){}