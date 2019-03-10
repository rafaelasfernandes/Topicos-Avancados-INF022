package com.example.appcervejaproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appcervejaproject.model.Marca;

import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public MarcaDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public void inserir(Marca marca){
        ContentValues values = new ContentValues();
        values.put("nome", marca.getNome());
        banco.insert("marca", null, values);
    }

    public void excluir(Marca marca){
        banco.delete("marca", "id_marca = ?",
                new String[]{marca.getId_marca().toString()});
    }

    public void atualizar(Marca marca){
        ContentValues values = new ContentValues();
        values.put("nome", marca.getNome());
        banco.update("marca", values, "id_marca = ?",
                new String[]{marca.getId_marca().toString()});
    }

    public List<Marca> listarMarcas(){
        List<Marca> marcas = new ArrayList<>();
        Cursor cursor = banco.query("marca", new String[]{"id_marca", "nome"},
                null, null, null, null, null);
        while(cursor.moveToNext()){
            Marca marca = new Marca();
            marca.setId_marca(cursor.getInt(0));
            marca.setNome(cursor.getString(1));
            marcas.add(marca);
        }
        return marcas;
    }
}
