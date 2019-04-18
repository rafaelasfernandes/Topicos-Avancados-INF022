package com.example.appcervejaproject.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.EditText;

import android.widget.Toast;


import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Cesta;
import com.example.appcervejaproject.rest.CestaService;
import com.example.appcervejaproject.rest.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroCestaActivity extends AppCompatActivity {

    private EditText nome; /*atributos associados com os campos de EditText*/
    private EditText data;
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

        CestaService cestaService = ServiceGenerator.createService(CestaService.class);
        Call<Cesta> call;
        final String mensagem;
        if(cesta == null){
            cesta = new Cesta(); /*criando um objeto cesta*/
            cesta.setNome(nome.getText().toString()); /*preenche os campos de cesta com os valores de EditText*/
            cesta.setData(data.getText().toString());
            call = cestaService.insereCesta(cesta);
            mensagem = "inserida";
        }
        else{
            cesta.setNome(nome.getText().toString());
            cesta.setData(data.getText().toString());
            call = cestaService.atualizaCesta(cesta.getId_cesta(), cesta);
            mensagem = "atualizada";
        }
        call.enqueue(new Callback<Cesta>() {
            @Override
            public void onResponse(Call<Cesta> call, Response<Cesta> response) {
                if(response.isSuccessful())
                    Toast.makeText(getBaseContext(), "Cesta " + mensagem +  " com sucesso!", Toast.LENGTH_SHORT).show();
                }

            @Override
            public void onFailure(Call<Cesta> call, Throwable t) {
                Log.e("ERROR ", t.getMessage());
            }
        });
        Intent intent = new Intent(CadastroCestaActivity.this, ListaCestaActivity.class);
        startActivity(intent);
    }

    public void excluir(View view){
        CestaService cestaService = ServiceGenerator.createService(CestaService.class);
        Call<Cesta> call = cestaService.excluiCesta(cesta.getId_cesta());
        call.enqueue(new Callback<Cesta>() {
            @Override
            public void onResponse(Call<Cesta> call, Response<Cesta> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Cesta excluída com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cesta> call, Throwable t) {
                Log.e("ERRO ", t.getMessage());
            }
        });
        Intent intent = new Intent(CadastroCestaActivity.this, ListaCestaActivity.class);
        startActivity(intent);
    }
}
