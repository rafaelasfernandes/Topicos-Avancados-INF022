package com.example.appcervejaproject.rest;

import com.example.appcervejaproject.model.Tipo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TipoService {

    @GET("tipos/")
    Call<List<Tipo>> getTipos();

    @POST("tipos/")
    Call<Tipo> insereTipo(@Body Tipo tipo);

    @PUT("tipos/{id}/")
    Call<Tipo> atualizaTipo(@Path("id") Integer id, @Body Tipo tipo);

    @DELETE("tipos/{id}/")
    Call<Tipo> excluiTipo(@Path("id") Integer id);
}
