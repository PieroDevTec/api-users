package com.rpatecnology.user.app.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseGeneralBD<T> {
    private String codResp;
    private String mensaje;
    private List<T> pocur;
    private String file64;
}
