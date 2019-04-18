package com.example.appcervejaproject.model;

import java.io.Serializable;

public class Estabelecimento implements Serializable {

    private Integer id;
    private String nome;

    public Integer getId_estabelecimento(){
        return this.id;
    }

    public void setId_estabelecimento(Integer id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

}
