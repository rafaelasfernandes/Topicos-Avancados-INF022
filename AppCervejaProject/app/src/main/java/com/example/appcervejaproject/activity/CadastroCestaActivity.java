package com.example.appcervejaproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appcervejaproject.DAO.CestaDAO;
import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Cesta;

public class CadastroCestaActivity extends AppCompatActivity {

    private EditText nome; /*atributos associados com os campos de EditText*/
    private EditText data;
    private CestaDAO cestaDao; /*atributo do tipo cestaDAO*/
    private Cesta cesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cesta);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Cesta");

        nome = findViewById(R.id.editText_NomeCesta); /*associa o xml com os atributos do java*/
        data = findViewById(R.id.editText_Data);
        cestaDao = new CestaDAO(this);

        Intent intent = getIntent();
        if(intent.hasExtra("cesta")){
            cesta = (Cesta) intent.getSerializableExtra("cesta");
            nome.setText(cesta.getNome());
            data.setText(cesta.getData());
        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, ListaCestaActivity.class));
        finish();
    }


    public void cadastrar(View view){

        if(cesta == null) {
            Cesta cesta = new Cesta(); /*criando um objeto cesta*/
            cesta.setNome(nome.getText().toString()); /*preenche os campos de cesta com os valores de EditText*/
            cesta.setData(data.getText().toString());
            cestaDao.inserir(cesta); /*insere a cesta no banco*/
        }
        else{
            cesta.setNome(nome.getText().toString());
            cesta.setData(data.getText().toString());
            cestaDao.atualizar(cesta);
        }
            Intent intent = new Intent(CadastroCestaActivity.this, ListaCestaActivity.class);
            startActivity(intent);
    }
}
