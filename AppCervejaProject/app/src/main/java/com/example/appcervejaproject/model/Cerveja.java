package com.example.appcervejaproject.model;

import java.io.Serializable;

public class Cerveja implements Serializable {

    private Integer id_cerveja;
    private String estabelecimento;
    private String marca;
    private String tipo;
    private float valor;

    public Integer getId_cerveja(){
        return this.id_cerveja;
    }

    public void setId_cerveja(Integer id_cerveja){
        this.id_cerveja = id_cerveja;
    }

    public String getEstabelecimento(){
        return this.estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento){
        this.estabelecimento = estabelecimento;
    }

    public String getMarca(){
        return this.marca;
    }

    public void setMarca(String marca){
        this.marca = marca;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public float getValor(){
        return this.valor;
    }

    public void setValor(float valor){
        this.valor = valor;
    }
}
