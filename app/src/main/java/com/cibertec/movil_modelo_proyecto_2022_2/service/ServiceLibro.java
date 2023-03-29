package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceLibro {
    @POST("libro")
    public abstract Call<Libro> registrarLibro(@Body Libro objLib);
    @GET("libro")
    public  Call<List<Libro>> listaLibro();
    @GET("libro/porTitulo/{titulo}")
    public  Call<List<Libro>> listaLibroPorTitulo(@Path("titulo") String titulo);

    @PUT("libro")
    public Call<Libro> actualizaLibro(@Body Libro obj);
}
