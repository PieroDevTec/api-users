package com.rpatecnology.user.app.response;

import com.rpatecnology.user.app.utils.ErrorApi;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseGeneral<T> {
    private Integer codEstatus;
    private String nombreSistema;
    private String mensaje;
    private String detailsApi;
    private T detailsBd;
    private List<T> data;
    public String getNombreSistema() {
        if(this.nombreSistema == null){
            this.nombreSistema = "WebApp User";
        }
        return this.nombreSistema;
    }

    public void setNombreSistema(String nombreSistema) {
        this.nombreSistema = nombreSistema;
    }

}
