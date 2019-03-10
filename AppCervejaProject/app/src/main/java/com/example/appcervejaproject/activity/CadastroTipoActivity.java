package com.example.appcervejaproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.appcervejaproject.DAO.TipoDAO;
import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Tipo;

public class CadastroTipoActivity extends AppCompatActivity {

    private EditText descricao;
    private EditText volume;
    public TipoDAO tipoDao;
    private Tipo tipo = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tipo);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Tipo");

        descricao = findViewById(R.id.editText_Descricao);
        volume = findViewById(R.id.editText_Volume);
        tipoDao = new TipoDAO(this);


        Intent intent = getIntent();
        if(intent.hasExtra("tipo")){
            tipo = (Tipo) intent.getSerializableExtra("tipo");
            descricao.setText(tipo.getDescricao());
            String vol = String.valueOf(tipo.getVolume());
            volume.setText(vol);
        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, ListaTipoActivity.class));
        finish();
    }

    public void cadastrar(View view){
        if(tipo == null) {
            Tipo tipo = new Tipo();
            tipo.setDescricao(descricao.getText().toString());
            float vol = Float.valueOf(volume.getText().toString());
            tipo.setVolume(vol);
            tipoDao.inserir(tipo);
        }
        else{
            tipo.setDescricao(descricao.getText().toString());
            float vol = Float.valueOf(volume.getText().toString());
            tipo.setVolume(vol);
            tipoDao.atualizar(tipo);
        }
        Intent intent = new Intent(CadastroTipoActivity.this, ListaTipoActivity.class);
        startActivity(intent);
    }
}
