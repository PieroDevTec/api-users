package com.rpatecnology.user.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class UserControllers {
    @RequestMapping(value = "guardar_usua",method = RequestMethod.POST)
    public String guardar_usua(){
        return "save";
    }
}
