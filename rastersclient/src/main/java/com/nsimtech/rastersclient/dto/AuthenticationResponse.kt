package com.nsimtech.rastersclient.dto

import kotlinx.serialization.Serializable

@Serializable
public class AuthenticationResponse
{
    var token_type: String = "";
    var access_token: String = "";
    var expires_in: String = "";
    var refresh_token: String = "";
}