package com.nsimtech.rastersclient.data

import com.google.gson.annotations.SerializedName
import com.nsimtech.rastersclient.data.Serializer.JsonSerializer
import com.nsimtech.rastersclient.data.Serializer.UUIDSerializer
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import java.time.OffsetDateTime
import java.util.*

@Serializable
class User()
{
    @Serializable(with = UUIDSerializer::class)
    var id : UUID? = null;

    var  email : String = "";
    var  firstname : String = "";
    var  lastname : String = "";
    var  registrationDate : String = "";
    var  lastUpdateDate: String = "";
    var  isActive : Boolean? = null;
    var  emailConfirmed : Boolean? = null;
    var  phoneNumber : String = "";
    var  phoneNumberConfirmed : Boolean? = null;
    var  userName : String = "";
    var  loginProvider : String = "";
    var  culture : String = "";
    var  timezone : String = "";
    var  pin : String = "";
    var  claims : JsonArray? = null
    var  settings : JsonElement? = null;

    @ContextualSerialization
    var  address : Address? = null;

}