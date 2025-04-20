package com.rpatecnology.user.app.services.impl;

import com.rpatecnology.user.app.repository.IUserRepository;
import com.rpatecnology.user.app.response.ResponseGeneral;
import com.rpatecnology.user.app.response.ResponseGeneralBD;
import com.rpatecnology.user.app.response.ResponseVerUser;
import com.rpatecnology.user.app.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;
    @Override
    public ResponseGeneral guardarUser(Number codUser, String nombUser, MultipartFile file) {
        ResponseGeneral resp = new ResponseGeneral();
        ResponseGeneralBD respBd = new ResponseGeneralBD();
        String nombFile = file.getOriginalFilename();
        String extFile = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
        try {
            byte[] byteFile = file.getBytes();
            respBd = userRepository.guardarUser(codUser, nombUser, byteFile, nombFile, extFile);
            if("1".equals(respBd.getCodResp())){
                resp.setCodEstatus(HttpStatus.CONFLICT.value());
                resp.setMensaje(respBd.getMensaje());
                return resp;
            }
            if ("2".equals(respBd.getCodResp())) {
                resp.setCodEstatus(HttpStatus.CONFLICT.value());
                resp.setMensaje("ERROR::PROMOCION SOCIAL::SERVDB0001 - No se ha podido guardar la imagen, intente otra vez. Si el error persiste comunicarque con el soporte tecnico.");
                resp.setDetailsBd(respBd);
                return resp;
            }
            resp.setCodEstatus(HttpStatus.CREATED.value());
            resp.setMensaje(respBd.getMensaje());
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            resp.setCodEstatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resp.setMensaje("ERROR::PROMOCION SOCIAL::SERV0001-> No se ha podido guardar la imagen, intente otra vez. Si el error persiste comunicarque con el soporte tecnico.");
            resp.setDetailsApi("Ha ocurrido un error en el servicio para guardar imagenes -> \n"+e.getMessage() + "->" + e.getCause());
            return resp;
        }
    }

    @Override
    public ResponseVerUser verPhotoUser(Integer codUser) {
        ResponseVerUser respUser = new ResponseVerUser();
        ResponseGeneralBD respBd = userRepository.verPhotUser(codUser);
        try{
            if ("2".equals(respBd.getCodResp())){
                respUser.setCodEstatus(HttpStatus.CONFLICT.value());
                respUser.setMensaje("\"ERROR::PROMOCION SOCIAL::SERVDB0002 - No se ha podido guardar la imagen, intente otra vez. Si el error persiste comunicarque con el soporte tecnico.");
                respUser.setDetailsBd(respBd);
                return respUser;
            }

            respUser.setCodEstatus(HttpStatus.OK.value());
            respUser.setMensaje(respBd.getMensaje());
            respUser.setBase64(respBd.getFile64());

        } catch (Exception e) {
            e.printStackTrace();
            respUser.setCodEstatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respUser.setMensaje("ERROR::PROMOCION SOCIAL::SERV0002-> No se puede visualizar la imagen, intente otra vez. Si el error persiste comunicarque con el soporte tecnico.");
            respUser.setDetailsApi("Ha ocurrido un error en el servicio para visualizar imagenes -> \n"+e.getMessage());
        }
        return respUser;
    }

    @Override
    public ResponseGeneral listarUsers() {
        ResponseGeneralBD respBd = new ResponseGeneralBD();
        ResponseGeneral resp = new ResponseGeneral();
        try{
            respBd = userRepository.listUsers();
            if("2".equals(respBd.getCodResp())){
                resp.setCodEstatus(HttpStatus.CONFLICT.value());
                resp.setMensaje("\"ERROR::PROMOCION SOCIAL::SERVDB0003 - Por el momento no se puede listar los usuarios, intente otra vez. Si el error persiste comunicarque con el soporte tecnico.");
                resp.setDetailsBd(respBd);
                return resp;
            }
            resp.setCodEstatus(HttpStatus.OK.value());
            resp.setMensaje(respBd.getMensaje());
            resp.setData(respBd.getPocur());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setCodEstatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resp.setMensaje("ERROR::PROMOCION SOCIAL::SERV0003->  Por el momento no se puede listar los usuarios, intente otra vez. Si el error persiste comunicarque con el soporte tecnico.");
            resp.setDetailsApi("Ha ocurrido un error en el servicio para listar usuarios -> \n"+e.getMessage());
        }
        return resp;
    }
}
