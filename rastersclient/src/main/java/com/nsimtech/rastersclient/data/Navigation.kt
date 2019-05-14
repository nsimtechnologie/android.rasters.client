package com.nsimtech.rastersclient.data

import com.google.gson.JsonElement
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import java.util.*

@Serializable
data class Navigation(var route: JsonObject? = null, var specificity: Specificity? = null ) {
}