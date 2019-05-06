package com.nsimtech.rastersclient.data.Serializer

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.json.*
import org.json.JSONObject
import java.util.*

@Serializer(forClass = JsonElement::class)
object JsonSerializer : KSerializer<JsonElement> {
    override val descriptor: SerialDescriptor
        get() = StringDescriptor.withName("JsonObject")

    override fun serialize(output: Encoder, obj: JsonElement) {
        output.encodeString(obj.toString())
    }

    @UseExperimental(UnstableDefault::class)
    @ImplicitReflectionSerializer
    override fun deserialize(input: Decoder): JsonElement {
        var json = input.decode<JsonElement>();
        var test = Json.nonstrict.parseJson(json.toString());

        return test;

    }
}