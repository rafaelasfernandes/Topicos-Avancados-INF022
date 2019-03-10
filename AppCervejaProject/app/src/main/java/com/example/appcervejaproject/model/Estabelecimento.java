package com.example.appcervejaproject.model;

import java.io.Serializable;

public class Estabelecimento implements Serializable {

    private Integer id_estabelecimento;
    private String nome;

    public Integer getId_estabelecimento(){
        return this.id_estabelecimento;
    }

    public void setId_estabelecimento(Integer id_estabelecimento){
        this.id_estabelecimento = id_estabelecimento;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

}
