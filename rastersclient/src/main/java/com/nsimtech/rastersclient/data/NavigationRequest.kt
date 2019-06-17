package com.nsimtech.rastersclient.data

import com.nsimtech.rastersclient.data.Serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class NavigationRequest(var itineraryId : IotDataKey? = null,

                             @Serializable(with = UUIDSerializer::class)
                             var roadLayerId: UUID? = null,
                             @Serializable(with = UUIDSerializer::class)
                             var particularityLayerId: UUID? = null,
                             var roadAttribute: String? = null) {}