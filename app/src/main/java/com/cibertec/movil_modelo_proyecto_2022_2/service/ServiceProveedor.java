package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceProveedor {

    @POST("proveedor")
    public abstract Call<Proveedor> registraProveedor(@Body Proveedor obj);

    @GET("proveedor/porRazonSocial/{razSoc}")
    public Call<List<Proveedor>> consulta (@Path("razSoc") String razSoc);

    @PUT("proveedor")
    public abstract Call<Proveedor> actualizaProveedor(@Body Proveedor obj);

    @DELETE("proveedor/{id}")
    public abstract Call<Proveedor> eliminaProveedor(@Path("id")int id);
}


