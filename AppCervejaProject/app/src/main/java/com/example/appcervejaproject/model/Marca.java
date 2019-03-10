package com.example.appcervejaproject.model;

import java.io.Serializable;

public class Marca implements Serializable {

    private Integer id_marca;
    private String nome;

    public Integer getId_marca(){
        return this.id_marca;
    }

    public void setId_marca(Integer id_marca){
        this.id_marca = id_marca;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

}
