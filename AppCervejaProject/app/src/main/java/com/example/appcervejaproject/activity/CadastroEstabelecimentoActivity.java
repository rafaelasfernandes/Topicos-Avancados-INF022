package com.example.appcervejaproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.appcervejaproject.DAO.EstabelecimentoDAO;
import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Estabelecimento;

public class CadastroEstabelecimentoActivity extends AppCompatActivity {

    private EditText nome;
    private EstabelecimentoDAO estabelecimentoDao;
    private Estabelecimento estabelecimento = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estabelecimento);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Estabelecimento");

        nome = findViewById(R.id.editText_NomeEstab);
        estabelecimentoDao = new EstabelecimentoDAO(this);

        Intent intent = getIntent();
        if(intent.hasExtra("estabelecimento")){
            estabelecimento = (Estabelecimento) intent.getSerializableExtra("estabelecimento");
            nome.setText(estabelecimento.getNome());
        }

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, ListaEstabelecimentoActivity.class));
        finish();
    }


    public void cadastrar(View view){

        if(estabelecimento == null) {
            Estabelecimento estabelecimento = new Estabelecimento();
            estabelecimento.setNome(nome.getText().toString());
            estabelecimentoDao.inserir(estabelecimento);
        }
        else{
            estabelecimento.setNome(nome.getText().toString());
            estabelecimentoDao.atualizar(estabelecimento);
        }
        Intent intent = new Intent(CadastroEstabelecimentoActivity.this, ListaEstabelecimentoActivity.class);
        startActivity(intent);
    }
}
