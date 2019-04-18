package com.example.appcervejaproject.rest;

import com.example.appcervejaproject.model.Cesta;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CestaService {

    @GET("cestas/")
    Call<List<Cesta>> getCestas();

    @POST("cestas/")
    Call<Cesta> insereCesta(@Body Cesta cesta);

    @PUT("cestas/{id}/")
    Call<Cesta> atualizaCesta(@Path("id") Integer id, @Body Cesta cesta);

    @DELETE("cestas/{id}/")
    Call<Cesta> excluiCesta(@Path("id") Integer id);

}
