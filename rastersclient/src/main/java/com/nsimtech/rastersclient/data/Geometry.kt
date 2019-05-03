package com.nsimtech.rastersclient.data

data class Geometry(var coordinates : List<Double>, var type: String = "Point") {
}