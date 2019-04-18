package com.example.appcervejaproject.model;

import java.io.Serializable;

public class Tipo implements Serializable {

    private Integer id;
    private String descricao;
    private float volume;

    public Integer getId_tipo(){
        return this.id;
    }

    public void setId_tipo(Integer id){
        this.id = id;
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
