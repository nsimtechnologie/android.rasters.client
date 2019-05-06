package com.nsimtech.rastersclient.data.Serializer

import com.nsimtech.rastersclient.data.Layer
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.json.*
import org.json.JSONObject
import java.util.*

@Serializer(forClass = Layer.Type::class)
object LayerTypeSerializer : KSerializer<Layer.Type> {
    override val descriptor: SerialDescriptor
        get() = StringDescriptor.withName("JsonObject")

    override fun serialize(output: Encoder, obj: Layer.Type) {
        output.encodeString(obj.toString())
    }

    @UseExperimental(UnstableDefault::class)
    @ImplicitReflectionSerializer
    override fun deserialize(input: Decoder): Layer.Type {
        var type : Layer.Type = Layer.Type.valueOf(input.decodeString());
        return type;

    }
}