package com.example.appcervejaproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cesta implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("data")
    private String data;

    public int getId_cesta(){
        return this.id;
    }

    public void setId_cesta(int id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getData(){
        return this.data;
    }

    public void setData(String data){
        this.data = data;
    }

}
