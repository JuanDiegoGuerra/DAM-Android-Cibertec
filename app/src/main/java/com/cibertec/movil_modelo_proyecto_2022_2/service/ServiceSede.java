package com.cibertec.movil_modelo_proyecto_2022_2.service;

import java.util.List;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sede;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceSede {

    @GET("util/listaSedes")
    public abstract Call<List<Sede>> listaSedes();
}
