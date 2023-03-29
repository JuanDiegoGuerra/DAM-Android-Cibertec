package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceCategoria {

    @GET("util/listaCategoria")
    public Call<List<Categoria>> listaTodos();
}
