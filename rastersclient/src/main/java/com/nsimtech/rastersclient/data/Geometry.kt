package com.nsimtech.rastersclient.data

import kotlinx.serialization.Serializable

@Serializable
data class Geometry(var coordinates : List<Double>, var type: String = "Point") {
}