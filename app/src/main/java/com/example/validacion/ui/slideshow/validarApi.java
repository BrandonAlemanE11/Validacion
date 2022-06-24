package com.example.validacion.ui.slideshow;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.Call;

public interface validarApi {
    @FormUrlEncoded
    @POST("validar")
    Call<validar> VALIDAR_CALL(
            @Field("email") String email,
            @Field("code") String code
    );
}
