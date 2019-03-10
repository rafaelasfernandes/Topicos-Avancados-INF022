package com.example.appcervejaproject.model;

import java.io.Serializable;

public class Cesta implements Serializable {

    private Integer id_cesta;
    private String nome;
    private String data;

    public Integer getId_cesta(){
        return this.id_cesta;
    }

    public void setId_cesta(Integer id_cesta){
        this.id_cesta = id_cesta;
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
