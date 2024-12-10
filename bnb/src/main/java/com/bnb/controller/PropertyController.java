package com.bnb.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/property")
public class PropertyController {

    @PostMapping("/addProperty")
    public String addProperty(){
        return "Property Added Successfully!";
    }

}
