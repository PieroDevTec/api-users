package com.rpatecnology.user.app.controllers;

import com.rpatecnology.user.app.response.ResponseGeneral;
import com.rpatecnology.user.app.response.ResponseVerUser;
import com.rpatecnology.user.app.services.IUserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin("*")
public class UserControllers {
    @Autowired
    IUserService userService;

    @RequestMapping(value = "guardar_usua",method = RequestMethod.POST)
    public ResponseEntity<ResponseGeneral> guardar_usua(@RequestParam Number codUser, @RequestParam String nombUser, @RequestParam MultipartFile file){
        ResponseGeneral resp = userService.guardarUser(codUser,nombUser,file);
        try {
            return ResponseEntity.status(resp.getCodEstatus()).body(resp);
        } catch (Exception e) {
            e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @RequestMapping(value = "ver_photo/{codUser}",method = RequestMethod.POST)
    public ResponseEntity<ResponseVerUser> verPhotoUser(@PathVariable("codUser")Integer codUser){
        ResponseVerUser respUser = userService.verPhotoUser(codUser);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(respUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respUser);
        }
    }

    @RequestMapping(value = "listar_users",method = RequestMethod.GET)
    public ResponseEntity<ResponseGeneral> listarUsers(){
        ResponseGeneral resp = userService.listarUsers();
        try{
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }





}
