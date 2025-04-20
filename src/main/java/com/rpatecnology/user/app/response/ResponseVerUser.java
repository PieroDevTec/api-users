package com.rpatecnology.user.app.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseVerUser<T> extends ResponseGeneral{
    private T base64;
}
