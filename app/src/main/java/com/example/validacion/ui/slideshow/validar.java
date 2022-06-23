package com.example.validacion.ui.slideshow;

public class validar {
    public String email;
    public String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public validar(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
