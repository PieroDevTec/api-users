package com.rpatecnology.user.app.mappers;

import com.rpatecnology.user.app.models.PhotoUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoUserMapper implements RowMapper<PhotoUser> {

    @Override
    public PhotoUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        PhotoUser user = new PhotoUser();
        Blob blob = rs.getBlob("PHOTO");
        if(blob != null){
            byte[] bytes = blob.getBytes(1,(int) blob.length());
            user.setPhotUser(bytes);
        }
        return user;
    }
}
