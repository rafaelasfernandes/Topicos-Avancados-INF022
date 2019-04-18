package com.example.appcervejaproject.rest;

import com.example.appcervejaproject.model.Estabelecimento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EstabelecimentoService {

    @GET("estabelecimentos/")
    Call<List<Estabelecimento>> getEstabelecimentos();

    @POST("estabelecimentos/")
    Call<Estabelecimento> insereEstabelecimento(@Body Estabelecimento estabelecimento);

    @PUT("estabelecimentos/{id}/")
    Call<Estabelecimento> atualizaEstabelecimento(@Path("id") Integer id, @Body Estabelecimento estabelecimento);

    @DELETE("estabelecimentos/{id}/")
    Call<Estabelecimento> excluiEstabelecimento(@Path("id") Integer id);
}
