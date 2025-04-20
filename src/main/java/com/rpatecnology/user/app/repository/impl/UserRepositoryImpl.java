package com.rpatecnology.user.app.repository.impl;

import com.rpatecnology.user.app.mappers.PhotoUserMapper;
import com.rpatecnology.user.app.models.ListUser;
import com.rpatecnology.user.app.models.PhotoUser;
import com.rpatecnology.user.app.repository.IUserRepository;
import com.rpatecnology.user.app.response.ResponseGeneralBD;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl extends JdbcDaoSupport implements IUserRepository {
    private static Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
    @Autowired
    private ApplicationContext context;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void DataSource(DataSource setDataSource){
        setDataSource(setDataSource);
        jdbcTemplate = context.getBean("db_desa_user",JdbcTemplate.class);
    }
    @Override
    public ResponseGeneralBD guardarUser(Number codUser, String nombUser, byte[] fileUser,String nombFile,String extFile) {
        ResponseGeneralBD respBd = new ResponseGeneralBD();
        try{
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withSchemaName("hr")
                    .withCatalogName("PKG_SERV_USERS")
                    .withoutProcedureColumnMetaDataAccess()
                    .withProcedureName("SP_GUAR_INFO_USER")
                    .declareParameters(
                            new SqlParameter("PIN_CO_USER", OracleTypes.NUMBER),
                            new SqlParameter("PIV_NO_USER",OracleTypes.VARCHAR),
                            new SqlParameter("PIB_USER",OracleTypes.BINARY),
                            new SqlParameter("PIV_NO_FILE",OracleTypes.VARCHAR),
                            new SqlParameter("PIV_TI_EXT",OracleTypes.VARCHAR),
                            new SqlOutParameter("POV_CO_RESP",OracleTypes.VARCHAR),
                            new SqlOutParameter("POV_MSG",OracleTypes.VARCHAR)
                    );
            SqlParameterSource inputParameters = new MapSqlParameterSource()
                    .addValue("PIN_CO_USER",codUser)
                    .addValue("PIV_NO_USER",nombUser)
                    .addValue("PIB_USER",fileUser)
                    .addValue("PIV_NO_FILE",nombFile)
                    .addValue("PIV_TI_EXT",extFile);
            Map<String,Object> out = simpleJdbcCall.execute(inputParameters);
            respBd.setCodResp((String) out.get("POV_CO_RESP"));
            respBd.setMensaje((String) out.get("POV_MSG"));
            return respBd;
        } catch (Exception e) {
            e.printStackTrace();
            respBd.setCodResp("2");
            respBd.setMensaje(e.getMessage());
            return respBd;
        }
    }

    @Override
    public ResponseGeneralBD verPhotUser(Integer codUser) {
        ResponseGeneralBD respBd = new ResponseGeneralBD();
        String photoUser64 = null;
        try{
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withSchemaName("hr")
                    .withCatalogName("PKG_SERV_USERS")
                    .withoutProcedureColumnMetaDataAccess()
                    .withProcedureName("SP_VER_IMG_USER")
                    .declareParameters(
                            new SqlParameter("PIN_CO_USER",OracleTypes.INTEGER),
                            new SqlOutParameter("POV_CO_RESP",OracleTypes.VARCHAR),
                            new SqlOutParameter("POV_MSG",OracleTypes.VARCHAR),
                            new SqlOutParameter("POCUR",OracleTypes.REF_CURSOR)
                    )
                    .returningResultSet("POCUR", new PhotoUserMapper());

            SqlParameterSource inputParameters = new MapSqlParameterSource()
                    .addValue("PIN_CO_USER",codUser);
            Map<String,Object> out = simpleJdbcCall.execute(inputParameters);
            List<PhotoUser> users = (List<PhotoUser>) out.get("POCUR");

            if(!users.isEmpty() && users != null){
                PhotoUser photoUser = users.get(0);
                photoUser64 = Base64Utils.encodeToString(photoUser.getPhotUser());
            }else{
                photoUser64 = "No hay imagen.";
            }


            respBd.setCodResp((String) out.get("POV_CO_RESP"));
            respBd.setMensaje((String) out.get("POV_MSG"));
            respBd.setFile64(photoUser64);
        } catch (Exception e) {
            e.printStackTrace();
            respBd.setCodResp("2");
            respBd.setMensaje(e.getMessage()+"-"+e.getCause());
        }
        return respBd;
    }

    @Override
    public ResponseGeneralBD listUsers() {
        ResponseGeneralBD respBd = new ResponseGeneralBD();
        try{
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withSchemaName("hr")
                    .withCatalogName("PKG_SERV_USERS")
                    .withoutProcedureColumnMetaDataAccess()
                    .withProcedureName("SP_LIST_USERS")
                    .declareParameters(
                            new SqlOutParameter("POV_CO_RESP",OracleTypes.VARCHAR),
                            new SqlOutParameter("POV_MSG",OracleTypes.VARCHAR),
                            new SqlOutParameter("POCUR",OracleTypes.REF_CURSOR)
                    )
                    .returningResultSet("POCUR",BeanPropertyRowMapper.newInstance(ListUser.class));

            Map<String,Object> out = simpleJdbcCall.execute();
            List<ListUser> users = (List<ListUser>) out.get("POCUR");
            respBd.setCodResp((String) out.get("POV_CO_RESP"));
            respBd.setMensaje((String) out.get("POV_MSG"));
            respBd.setPocur(users);
        } catch (Exception e) {
            e.printStackTrace();
            respBd.setCodResp("2");
            respBd.setMensaje(e.getMessage()+"-"+e.getCause());
        }
        return respBd;
    }
}
