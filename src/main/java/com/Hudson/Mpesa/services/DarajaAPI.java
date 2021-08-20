package com.Hudson.Mpesa.services;

import com.Hudson.Mpesa.dtos.AccessTokenResponse;
import com.Hudson.Mpesa.dtos.RegisterUrlRequest;
import com.Hudson.Mpesa.dtos.RegisterUrlResponse;

public interface DarajaAPI {
    AccessTokenResponse getAccessToken();

    RegisterUrlResponse registerUrl();
}
