package com.example.appcervejaproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appcervejaproject.model.Cesta;

import java.util.ArrayList;
import java.util.List;

public class CestaDAO {

    private Conexao conexao;
    private SQLiteDatabase banco; /*banco de dados que vai ser utilizado para inserir os valores*/


    public CestaDAO(Context context){ /*precisa do contexto para criar conexao*/
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase(); /*obtem um banco para escrita*/
    }

    public long inserir(Cesta cesta){
        ContentValues values = new ContentValues();/*valores que eu vou inserir na tabela cesta*/
        values.put("nome", cesta.getNome());  /*coloca valores put dentro das variaveis values*/
        values.put("data", cesta.getData());
        return banco.insert("cesta", null, values); /*insere no banco/retorna o id cadastrado*/
    }

    public void excluir(Cesta cesta){
        banco.delete("cesta", "id_cesta = ?", new String[]{cesta.getId_cesta().toString()});
    }

    public void atualizar(Cesta cesta){
        ContentValues values = new ContentValues();/*valores que eu vou atualizar na tabela cesta*/
        values.put("nome", cesta.getNome());  /*coloca valores put dentro das variaveis values*/
        values.put("data", cesta.getData());
        banco.update("cesta", values, "id_cesta = ?", new String[]{cesta.getId_cesta().toString()}); /*atualiza no banco/*/
    }

    /*método pra consultar do banco de dados*/
    public List<Cesta> listarCestas(){
        List<Cesta> cestas = new ArrayList<>(); /*lista para adicionar as cestas que foram encontrados*/

        /*criando a consulta*/
        Cursor cursor = banco.query("cesta", new String[]{"id_cesta", "nome", "data"},
                null, null, null, null, null); /*funciona como um select * from */

        while(cursor.moveToNext()){ /*verifica se consegue mover para o proximo*/
            Cesta cesta = new Cesta();
            cesta.setId_cesta(cursor.getInt(0));
            cesta.setNome(cursor.getString(1));
            cesta.setData(cursor.getString(2));
            cestas.add(cesta);   /*adiciona a cesta na lista*/
        }
        return cestas; /*retorna todas as cestas cadastradas*/
    }
}
