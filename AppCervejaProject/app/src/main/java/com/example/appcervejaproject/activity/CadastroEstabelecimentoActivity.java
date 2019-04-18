package com.example.appcervejaproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Estabelecimento;
import com.example.appcervejaproject.rest.EstabelecimentoService;
import com.example.appcervejaproject.rest.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroEstabelecimentoActivity extends AppCompatActivity {

    private EditText nome;
    private Estabelecimento estabelecimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estabelecimento);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Estabelecimento");

        nome = findViewById(R.id.editText_NomeEstab);

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

        EstabelecimentoService estabelecimentoService = ServiceGenerator.createService(EstabelecimentoService.class);
        Call<Estabelecimento> call;
        final String mensagem;
        if(estabelecimento == null) {
            estabelecimento = new Estabelecimento();
            estabelecimento.setNome(nome.getText().toString());
            call = estabelecimentoService.insereEstabelecimento(estabelecimento);
            mensagem = "inserido";
        }
        else{
            estabelecimento.setNome(nome.getText().toString());
            call = estabelecimentoService.atualizaEstabelecimento(estabelecimento.getId_estabelecimento(), estabelecimento);
            mensagem = "atualizado";
        }
        call.enqueue(new Callback<Estabelecimento>() {
            @Override
            public void onResponse(Call<Estabelecimento> call, Response<Estabelecimento> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Estabelecimento " + mensagem + " com sucesso!", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<Estabelecimento> call, Throwable t) {
                Log.e("ERROR ", t.getMessage());
            }
        });
        Intent intent = new Intent(CadastroEstabelecimentoActivity.this, ListaEstabelecimentoActivity.class);
        startActivity(intent);
    }

    public void excluir(View view){
        EstabelecimentoService estabelecimentoService = ServiceGenerator.createService(EstabelecimentoService.class);
        Call<Estabelecimento> call = estabelecimentoService.excluiEstabelecimento(estabelecimento.getId_estabelecimento());
        call.enqueue(new Callback<Estabelecimento>() {
            @Override
            public void onResponse(Call<Estabelecimento> call, Response<Estabelecimento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Estabelecimento excluído com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Estabelecimento> call, Throwable t) {
                Log.e("ERRO ", t.getMessage());
            }
        });
        Intent intent = new Intent(CadastroEstabelecimentoActivity.this, ListaEstabelecimentoActivity.class);
        startActivity(intent);
    }
}
