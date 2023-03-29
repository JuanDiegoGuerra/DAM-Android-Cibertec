package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;


import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.POST;

public interface ServiceRevista {
    @POST("revista")
    public abstract Call<Revista> inserRevista(@Body Revista list);
}
