package com.example.validacion.ui.slideshow;

import retrofit2.http.POST;
import retrofit2.Call;

public interface validarApi {
    String API_ROUTE = "/validar";

    @POST(API_ROUTE)
    Call<validar> getElemets();
}
