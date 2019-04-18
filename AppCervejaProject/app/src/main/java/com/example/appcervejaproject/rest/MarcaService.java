package com.example.appcervejaproject.rest;

import com.example.appcervejaproject.model.Marca;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MarcaService {

    @GET("marcas/")
    Call<List<Marca>> getMarcas();

    @POST("marcas/")
    Call<Marca> insereMarca(@Body Marca marca);

    @PUT("marcas/{id}/")
    Call<Marca> atualizaMarca(@Path("id") Integer id, @Body Marca marca);

    @DELETE("marcas/{id}/")
    Call<Marca> excluiMarca(@Path("id") Integer id);
}

