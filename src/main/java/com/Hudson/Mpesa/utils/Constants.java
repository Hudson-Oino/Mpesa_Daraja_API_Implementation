package com.Hudson.Mpesa.utils;

import okhttp3.MediaType;

public class Constants {
    public static final String CACHE_CONTROL_HEADER = "Cache-control";
    public static final String AUTHORIZATION_HEADER_STRING = "AUTHORIZATION";
    public static final String CACHE_CONTROL_HEADER_VALUE = "No-Cache";
    public static  final String BASIC_AUTH_STRING = "Basic";
    public static MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
}
