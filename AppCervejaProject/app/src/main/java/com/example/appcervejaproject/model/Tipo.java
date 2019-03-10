package com.example.appcervejaproject.model;

import java.io.Serializable;

public class Tipo implements Serializable {

    private Integer id_tipo;
    private String descricao;
    private float volume;

    public Integer getId_tipo(){
        return this.id_tipo;
    }

    public void setId_tipo(Integer id_tipo){
        this.id_tipo = id_tipo;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public float getVolume(){
        return this.volume;
    }

    public void setVolume(float volume){
        this.volume = volume;
    }

    @Override
    public String toString(){
        return this.getDescricao() + "\n" + this.getVolume();
    }
}
