package com.example.appcervejaproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appcervejaproject.model.Estabelecimento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EstabelecimentoDAO implements Serializable{
    private Conexao conexao;
    private SQLiteDatabase banco;

    public EstabelecimentoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Estabelecimento estabelecimento){
        ContentValues values = new ContentValues();
        values.put("nome", estabelecimento.getNome());
        return banco.insert("estabelecimento", null, values);
    }

    public void excluir(Estabelecimento estabelecimento){
        banco.delete("estabelecimento", "id_estabelecimento = ?",
                new String[]{estabelecimento.getId_estabelecimento().toString()});
    }

    public void atualizar(Estabelecimento estabelecimento){
        ContentValues values = new ContentValues();
        values.put("nome", estabelecimento.getNome());
        banco.update("estabelecimento", values, "id_estabelecimento = ?",
                new String[]{estabelecimento.getId_estabelecimento().toString()});
    }

    public List<Estabelecimento> listarEstabelecimentos(){
        List<Estabelecimento> estabelecimentos = new ArrayList<>();
        Cursor cursor = banco.query("estabelecimento", new String[]{"id_estabelecimento", "nome"},
                null, null, null, null, null);

        while(cursor.moveToNext()){
            Estabelecimento estabelecimento = new Estabelecimento();
            estabelecimento.setId_estabelecimento(cursor.getInt(0));
            estabelecimento.setNome(cursor.getString(1));
            estabelecimentos.add(estabelecimento);
        }
        return estabelecimentos;
    }
}
