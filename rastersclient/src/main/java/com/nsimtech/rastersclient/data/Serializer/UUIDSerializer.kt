package com.nsimtech.rastersclient.data.Serializer

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.util.*

@Serializer(forClass = UUID::class)
object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor
        get() = StringDescriptor.withName("UUID")

    override fun serialize(output: Encoder, obj: UUID) {
        output.encodeString(obj.toString())
    }

    @ImplicitReflectionSerializer
    override fun deserialize(input: Decoder): UUID {
        return UUID.fromString(input.decode())
    }
}