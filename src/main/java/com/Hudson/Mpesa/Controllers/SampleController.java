package com.Hudson.Mpesa.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SampleController {
    @GetMapping( produces = "application/json")
    public String getResponse(){
        return "Server is up and running";
    }

}
