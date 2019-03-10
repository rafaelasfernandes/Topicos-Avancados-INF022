package com.example.appcervejaproject.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appcervejaproject.model.Tipo;

import java.util.ArrayList;
import java.util.List;

public class TipoDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public TipoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public void inserir(Tipo tipo) {
        ContentValues values = new ContentValues();
        values.put("descricao", tipo.getDescricao());
        values.put("volume", tipo.getVolume());
        banco.insert("tipo", null, values);
    }

    public void atualizar(Tipo tipo){
        ContentValues values = new ContentValues();
        values.put("descricao", tipo.getDescricao());
        values.put("volume", tipo.getVolume());
        banco.update("tipo", values, "id_tipo = ?", new String[]{tipo.getId_tipo().toString()});
    }

    public List<Tipo> listarTipos(){
        List<Tipo> tipos = new ArrayList<>();
        Cursor cursor = banco.query("tipo", new String[]{"id_tipo", "descricao", "volume"},
                null, null, null, null, null);

        while(cursor.moveToNext()){
            Tipo tipo = new Tipo();
            tipo.setId_tipo(cursor.getInt(0));
            tipo.setDescricao(cursor.getString(1));
            tipo.setVolume(cursor.getFloat(2));
            tipos.add(tipo);
        }
        return tipos;
    }

    public boolean excluir(Tipo tipo){
        try {
            banco.delete("tipo", "id_tipo = ?", new String[]{tipo.getId_tipo().toString()});
            Log.e("INFO", "ERRO REMOVIDO COM SUCESSO");
        }
        catch (Exception e){
            Log.e("INFO", "ERRO AO REMOVER TIPO " + e.getMessage());
            return false;
        }
        return true;
    }
}
