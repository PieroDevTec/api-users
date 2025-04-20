package com.rpatecnology.user.app.services;

import com.rpatecnology.user.app.response.ResponseGeneral;
import com.rpatecnology.user.app.response.ResponseVerUser;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
    public ResponseGeneral guardarUser(Number codUser, String nombUser, MultipartFile file);
    public ResponseVerUser verPhotoUser(Integer codUser);
    public ResponseGeneral listarUsers();
}
