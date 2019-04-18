package com.example.appcervejaproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Tipo;
import com.example.appcervejaproject.rest.ServiceGenerator;
import com.example.appcervejaproject.rest.TipoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroTipoActivity extends AppCompatActivity {

    private EditText descricao;
    private EditText volume;
    private Tipo tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tipo);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Tipo");

        descricao = findViewById(R.id.editText_Descricao);
        volume = findViewById(R.id.editText_Volume);

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

        TipoService tipoService = ServiceGenerator.createService(TipoService.class);
        Call<Tipo> call;
        final String mensagem;
        if(tipo == null) {
            tipo = new Tipo();
            tipo.setDescricao(descricao.getText().toString());
            float vol = Float.valueOf(volume.getText().toString());
            tipo.setVolume(vol);
            call = tipoService.insereTipo(tipo);
            mensagem = "inserido";
        }
        else{
            tipo.setDescricao(descricao.getText().toString());
            float vol = Float.valueOf(volume.getText().toString());
            tipo.setVolume(vol);
            call = tipoService.atualizaTipo(tipo.getId_tipo(), tipo);
            mensagem = "atualizado";
        }

        call.enqueue(new Callback<Tipo>() {
            @Override
            public void onResponse(Call<Tipo> call, Response<Tipo> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Tipo " + mensagem + " com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tipo> call, Throwable t) {
                Log.e("ERRO ", t.getMessage());
            }
        });

        Intent intent = new Intent(CadastroTipoActivity.this, ListaTipoActivity.class);
        startActivity(intent);
    }

    public void excluir(View view){

        TipoService tipoService = ServiceGenerator.createService(TipoService.class);
        Call<Tipo> call = tipoService.excluiTipo(tipo.getId_tipo());
        call.enqueue(new Callback<Tipo>() {
            @Override
            public void onResponse(Call<Tipo> call, Response<Tipo> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Tipo excluído com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tipo> call, Throwable t) {
                Log.e("ERRO ", t.getMessage());
            }
        });
        Intent intent = new Intent(CadastroTipoActivity.this, ListaTipoActivity.class);
        startActivity(intent);
    }
}
