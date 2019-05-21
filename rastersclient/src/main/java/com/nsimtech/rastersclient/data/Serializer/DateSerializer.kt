package com.nsimtech.rastersclient.data.Serializer

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.json.*
import org.json.JSONObject
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date?> {

    private val formatArray : List<SimpleDateFormat> = listOf(
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'+00:00'"));

    override val descriptor: SerialDescriptor
        get() = StringDescriptor.withName("Date")

    override fun serialize(output: Encoder, obj: Date?) {
        formatArray[0].timeZone = TimeZone.getTimeZone("UTC")
        output.encodeString(formatArray[0].format(obj))
    }

    @UseExperimental(UnstableDefault::class)
    @ImplicitReflectionSerializer
    override fun deserialize(input: Decoder): Date? {
        var deserializedDate : Date? = null;
        var stringToDecode = input.decodeString();

        for(dateformat in formatArray)
        {
            dateformat.timeZone = TimeZone.getTimeZone("UTC")
            try {
                deserializedDate= dateformat.parse(stringToDecode);
            }
            catch(e:Exception){}
            if(deserializedDate != null)
                break;

        }

        return deserializedDate;
    }
}