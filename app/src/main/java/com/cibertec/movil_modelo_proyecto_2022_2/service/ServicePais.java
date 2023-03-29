package com.cibertec.movil_modelo_proyecto_2022_2.service;

import java.util.List;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicePais {

    @GET("util/listaPais")
    public abstract Call<List<Pais>> listaPais();
}
