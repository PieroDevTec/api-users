package com.rpatecnology.user.app.repository;

import com.rpatecnology.user.app.response.ResponseGeneralBD;
import com.rpatecnology.user.app.response.ResponseVerUser;
import org.springframework.web.multipart.MultipartFile;

public interface IUserRepository {
    public ResponseGeneralBD guardarUser(Number codUser, String nombUser, byte[] fileUser,String nombFile,String extFile);
    public ResponseGeneralBD verPhotUser(Integer codUser);
    public ResponseGeneralBD listUsers();
}
