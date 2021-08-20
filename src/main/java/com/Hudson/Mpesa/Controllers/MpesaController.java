package com.Hudson.Mpesa.Controllers;

import com.Hudson.Mpesa.dtos.AccessTokenResponse;
import com.Hudson.Mpesa.dtos.RegisterUrlResponse;
import com.Hudson.Mpesa.services.DarajaAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mobile-money")
public class MpesaController {
    private final DarajaAPI darajaAPI;

    public MpesaController(DarajaAPI darajaAPI) {
        this.darajaAPI = darajaAPI;

    }
    @GetMapping(path="/token", produces= "application/json")
    public ResponseEntity<AccessTokenResponse> getAccessToken(){
        return ResponseEntity.ok(darajaAPI.getAccessToken());
    }

    @PostMapping(path="/register-url",produces = "application/json")
    public ResponseEntity<RegisterUrlResponse> registerUrl(){
        return ResponseEntity.ok(darajaAPI.registerUrl());
    }
}
