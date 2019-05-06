package com.nsimtech.rastersclient.data.Serializer

import com.nsimtech.rastersclient.data.DataSet
import com.nsimtech.rastersclient.data.Layer
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.json.*
import org.json.JSONObject
import java.util.*

@Serializer(forClass = DataSet.Status::class)
object DatasetStatusSerializer : KSerializer<DataSet.Status> {
    override val descriptor: SerialDescriptor
        get() = StringDescriptor.withName("JsonObject")

    override fun serialize(output: Encoder, obj: DataSet.Status) {
        output.encodeString(obj.toString())
    }

    @UseExperimental(UnstableDefault::class)
    @ImplicitReflectionSerializer
    override fun deserialize(input: Decoder): DataSet.Status {
        var stringStatus = input.decodeString();

        if(stringStatus == null || stringStatus == "")
            return DataSet.Status.Error;
        else
            return DataSet.Status.valueOf(stringStatus);

    }
}