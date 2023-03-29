package com.cibertec.movil_modelo_proyecto_2022_2.service;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceSala {
    @POST("sala")
    public Call<Sala> insertaSala(@Body Sala obj);

    @GET("sala")
    public Call<List<Sala>> listaSala();

    @GET("sala/porNumero/{numero}")
    public Call<List<Sala>> listaSalaPorNumero (@Path("numero") String numero);

    @PUT("sala")
    public abstract Call<Sala> actualizaSala(@Body Sala obj);

    @DELETE("sala/{id}")
    public abstract Call<Sala> eliminaSala(@Path("id")int id);

}
