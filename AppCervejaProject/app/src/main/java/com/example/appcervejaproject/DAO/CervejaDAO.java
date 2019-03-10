package com.example.appcervejaproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appcervejaproject.model.Cerveja;

import java.util.ArrayList;
import java.util.List;

public class CervejaDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public CervejaDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Cerveja cerveja){
        ContentValues values = new ContentValues();
        values.put("estabelecimento", cerveja.getEstabelecimento());
        values.put("marca", cerveja.getMarca());
        values.put("tipo", cerveja.getTipo());
        values.put("valor", cerveja.getValor());
        return banco.insert("cerveja", null, values);
    }

    public void excluir(Cerveja cerveja){
        banco.delete("cerveja", "id_cerveja = ?", new String[]{cerveja.getId_cerveja().toString()});
    }

    public void atualizar(Cerveja cerveja){
        ContentValues values = new ContentValues();
        values.put("estabelecimento", cerveja.getEstabelecimento());
        values.put("marca", cerveja.getMarca());
        values.put("tipo", cerveja.getTipo());
        values.put("valor", cerveja.getValor());
        banco.update("cerveja", values, "id_cerveja = ?", new String[]{cerveja.getId_cerveja().toString()});
    }

    public List<Cerveja> listarCervejas(){
        List<Cerveja> cervejas = new ArrayList<>();

        Cursor cursor = banco.query("cerveja", new String[]{"id_cerveja", "valor", "estabelecimento", "marca", "tipo"},
                null, null, null, null, "valor DESC");

        while(cursor.moveToNext()){
            Cerveja cerveja = new Cerveja();
            cerveja.setId_cerveja(cursor.getInt(0));
            cerveja.setValor(cursor.getFloat(1));
            cerveja.setEstabelecimento(cursor.getString(2));
            cerveja.setMarca(cursor.getString(3));
            cerveja.setTipo(cursor.getString(4));
            cervejas.add(cerveja);
        }
        return cervejas;
    }
}
