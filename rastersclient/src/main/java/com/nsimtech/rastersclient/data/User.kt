package com.nsimtech.rastersclient.data

import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject
import java.time.OffsetDateTime
import java.util.*

class User()
{
    @SerializedName("id")
    var id : UUID? = null;

    var  email : String = "";
    var  firstname : String = "";
    var  lastname : String = "";
    var  registrationDate : String = "";
    var  lastUpdateDate: String = "";
    var  isActive : Boolean? = null;
    var  emailConfirmede : Boolean? = null;
    var  phoneNumber : String = "";
    var  phoneNumberConfirmed : Boolean? = null;
    var  userName : String = "";
    var  settings : JSONObject  = JSONObject();
    var  loginProvider : String = "";
    var  address : Address? = null;
    var  culture : String = "";
    var  timezone : String = "";
    var  pin : String = "";
    var  claims : Collection<JSONObject>? = null

}