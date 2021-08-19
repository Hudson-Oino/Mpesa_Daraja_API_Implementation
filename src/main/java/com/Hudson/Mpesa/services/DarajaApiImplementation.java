package com.Hudson.Mpesa.services;

import com.Hudson.Mpesa.config.MpesaConfiguration;
import com.Hudson.Mpesa.dtos.AccessTokenResponse;
import com.Hudson.Mpesa.utils.HelperUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import com.Hudson.Mpesa.utils.Constants.*;

import java.io.IOException;

import static com.Hudson.Mpesa.utils.Constants.*;

@Service
@Slf4j
public class DarajaApiImplementation implements DarajaAPI{

    private final MpesaConfiguration mpesaConfiguration;
    private final OkHttpClient OkHttpClient;
    private final ObjectMapper objectMapper;

    public DarajaApiImplementation(MpesaConfiguration mpesaConfiguration,  okhttp3.OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.mpesaConfiguration = mpesaConfiguration;
        OkHttpClient = okHttpClient;

        this.objectMapper = objectMapper;
    }

    @Override
    public AccessTokenResponse getAccessToken() {
//        get the Base64 representation of teh consumerkey and consumer secret
        String encodeCredentials = HelperUtility.toBase64String(String.format("%s:%s",mpesaConfiguration.getConsumerKey(),mpesaConfiguration.getConsumerSecret()));

        Request request = new Request.Builder()
                .url(String.format("%s?grant_type=%s", mpesaConfiguration.getOauthEndpoint(),mpesaConfiguration.getGrantType()))
                .get()
                .addHeader(AUTHORIZATION_HEADER_STRING,String.format("%s %s", BASIC_AUTH_STRING))
                .addHeader(CACHE_CONTROL_HEADER, CACHE_CONTROL_HEADER_VALUE)
                .build();

        try{
            Response response = OkHttpClient.newCall(request).execute();
            assert response.body() != null;

            return objectMapper.readValue(response.body().toString(), AccessTokenResponse.class);


        } catch (IOException e) {
            log.error(String.format("Couldn't get access token. -> %s ", e.getLocalizedMessage()));
            return null;
        }
    }
}
