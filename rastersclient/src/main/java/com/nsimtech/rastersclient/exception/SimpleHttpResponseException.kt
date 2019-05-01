package com.nsimtech.rastersclient.exception

import okhttp3.MediaType
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class SimpleHttpResponseException(content: String?) : IOException(content)
{
    var statusCode : Int? = null;
    var error : JSONObject? = null;

    constructor(statusCode:Int, content:String,contentType : MediaType?) : this(content) {
        this.statusCode = statusCode;

        if(contentType != null && contentType.type().contains("json",true))
        {
            error = JSONObject(content);
        }
        else
        {
            val jsonArrayContent = JSONObject();
            jsonArrayContent.put("Code","GenericError");
            jsonArrayContent.put("Description",content);

            val jsonArray = JSONArray();
            jsonArray.put(jsonArrayContent);

            error = JSONObject();
            error!!.put("Errors",jsonArray);
        }

    }
}