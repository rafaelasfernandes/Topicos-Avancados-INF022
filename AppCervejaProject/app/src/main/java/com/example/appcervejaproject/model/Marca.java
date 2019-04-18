package com.example.appcervejaproject.model;

import java.io.Serializable;

public class Marca implements Serializable {

    private Integer id;
    private String nome;

    public Integer getId_marca(){
        return this.id;
    }

    public void setId_marca(Integer id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

}
