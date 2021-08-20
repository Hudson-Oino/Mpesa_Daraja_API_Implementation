package com.Hudson.Mpesa.services;

import com.Hudson.Mpesa.config.MpesaConfiguration;
import com.Hudson.Mpesa.dtos.AccessTokenResponse;
import com.Hudson.Mpesa.dtos.RegisterUrlRequest;
import com.Hudson.Mpesa.dtos.RegisterUrlResponse;
import com.Hudson.Mpesa.utils.HelperUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.Objects;

import static com.Hudson.Mpesa.utils.Constants.*;

@Service
@Slf4j
public class DarajaApiImplementation implements DarajaAPI{

    private final MpesaConfiguration mpesaConfiguration;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public DarajaApiImplementation(MpesaConfiguration mpesaConfiguration,  OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.mpesaConfiguration = mpesaConfiguration;
         this.okHttpClient = okHttpClient;

        this.objectMapper = objectMapper;
    }

    @Override
    public AccessTokenResponse getAccessToken() {
//        get the Base64 representation of teh consumerkey and consumer secret
        String encodedCredentials = HelperUtility.toBase64String(String.format("%s:%s",mpesaConfiguration.getConsumerKey(),mpesaConfiguration.getConsumerSecret()));

        Request request = new Request.Builder()
                .url(String.format("%s?grant_type=%s", mpesaConfiguration.getOauthEndpoint(),mpesaConfiguration.getGrantType()))
                .get()
                .addHeader(AUTHORIZATION_HEADER_STRING,String.format("%s %s", BASIC_AUTH_STRING,encodedCredentials))
                .addHeader(CACHE_CONTROL_HEADER, CACHE_CONTROL_HEADER_VALUE)
                .build();

        try{
            Response response = okHttpClient.newCall(request).execute();
            assert response.body()!= null;

            return objectMapper.readValue(response.body().string(), AccessTokenResponse.class);


        } catch (IOException e) {
            log.error(String.format("Couldn't get access token. -> %s ", e.getLocalizedMessage()));
            return null;
        }
    }

    @Override
    public RegisterUrlResponse registerUrl() {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        RegisterUrlRequest registerUrlRequest = new RegisterUrlRequest();
        registerUrlRequest.setConfirmationUrl(mpesaConfiguration.getConfirmationUrl());
        registerUrlRequest.setResponseType(mpesaConfiguration.getResponseType());
        registerUrlRequest.setValidationUrl(mpesaConfiguration.getValidationUrl());
        registerUrlRequest.setShortCode(mpesaConfiguration.getShortCode());

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, Objects.requireNonNull(HelperUtility.toJson(registerUrlRequest)));

        Request request = new Request.Builder()
                .url(mpesaConfiguration.getRegisterUrlEndpoint())
                .post(body)
                .addHeader("Authorization","Bearer "+accessTokenResponse.getAccessToken())
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() !=null;

            return objectMapper.readValue(response.body().string(), RegisterUrlResponse.class);

        } catch (IOException e) {
            log.error("Could not register url -> "+e.getLocalizedMessage());
            return null;
        }

    }
}
