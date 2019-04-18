package com.example.appcervejaproject.rest;

import com.example.appcervejaproject.model.Cerveja;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CervejaService {

    @GET("cervejas/")
    Call<List<Cerveja>> getCervejas();

    @POST("cervejas/")
    Call<Cerveja> insereCerveja(@Body Cerveja cerveja);

    @PUT("cervejas/{id}/")
    Call<Cerveja> atualizaCerveja(@Path("id") Integer id, @Body Cerveja cerveja);

    @DELETE("cervejas/{id}/")
    Call<Cerveja> excluiCerveja(@Path("id") Integer id);
}
