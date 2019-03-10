package com.example.appcervejaproject.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "banco.db"; /*nome do banco de dados*/
    private static  final int version = 1;  /*versao do banco de dados*/

    public Conexao(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*criando as tabelas*/
        String estabelecimentoSQL = "CREATE TABLE estabelecimento(id_estabelecimento INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                                 "nome TEXT NOT NULL);";

        String marcaSQL = "CREATE TABLE marca(id_marca INTEGER PRIMARY KEY AUTOINCREMENT," +
                                             "nome TEXT NOT NULL);";

        String tipoSQL = "CREATE TABLE tipo(id_tipo INTEGER PRIMARY KEY AUTOINCREMENT," +
                                           "descricao TEXT NOT NULL, " +
                                           "volume REAL NOT NULL);";

        String cestaSQL = "CREATE TABLE cesta(id_cesta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                             "nome TEXT NOT NULL, " +
                                             "data TEXT NOT NULL);";

        String cervejaSQL = "CREATE TABLE cerveja(id_cerveja INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                 "valor REAL NOT NULL," +
                                                 "estabelecimento TEXT NOT NULL," +
                                                 "marca TEXT NOT NULL," +
                                                 "tipo TEXT NOT NULL);";

            db.execSQL(estabelecimentoSQL);
            db.execSQL(marcaSQL);
            db.execSQL(tipoSQL);
            db.execSQL(cestaSQL);
            db.execSQL(cervejaSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String estabelecimentoSQL = "DROP TABLE IF EXISTS estabelecimento";
        String marcaSQL = "DROP TABLE IF EXISTS marca";
        String tipoSQL = "DROP TABLE IF EXISTS tipo";
        String cestaSQL = "DROP TABLE IF EXISTS cesta";
        String cervejaSQL = "DROP TABLE IF EXISTS cerveja";

        db.execSQL(estabelecimentoSQL);
        db.execSQL(marcaSQL);
        db.execSQL(tipoSQL);
        db.execSQL(cestaSQL);
        db.execSQL(cervejaSQL);

        onCreate(db);
    }
}
