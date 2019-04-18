package com.example.appcervejaproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.appcervejaproject.R;

import com.example.appcervejaproject.model.Cerveja;
import com.example.appcervejaproject.rest.CervejaService;
import com.example.appcervejaproject.rest.ServiceGenerator;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CadastroCervejaActivity extends AppCompatActivity {

    private TextView cesta;
    private AutoCompleteTextView estabelecimento;
    private AutoCompleteTextView marca;
    private AutoCompleteTextView tipo;
    private EditText valor;
    private Cerveja cerveja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cerveja);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Informações da Cerveja");

        cesta = findViewById(R.id.textView_Cesta);
        estabelecimento = findViewById(R.id.autoComplete_Estabelecimento);
        marca = findViewById(R.id.autoComplete_Marca);
        tipo = findViewById(R.id.autoComplete_Tipo);
        valor = findViewById(R.id.editText_Preco);

        Intent intent = getIntent();
        if(intent.hasExtra("cerveja")){
            cerveja = (Cerveja) intent.getSerializableExtra("cerveja");
            estabelecimento.setText(cerveja.getEstabelecimento());
            marca.setText(cerveja.getMarca());
            tipo.setText(cerveja.getTipo());
            String val = String.valueOf(cerveja.getValor());
            valor.setText(val);
        }

    }

    public void cadastrar(View view){

        CervejaService cervejaService = ServiceGenerator.createService(CervejaService.class);
        Call<Cerveja> call;
        final String mensagem;
        if(cerveja == null){
            cerveja = new Cerveja();
            cerveja.setEstabelecimento(estabelecimento.getText().toString());
            cerveja.setMarca(marca.getText().toString());
            cerveja.setTipo(tipo.getText().toString());
            float val = Float.valueOf(valor.getText().toString());
            cerveja.setValor(val);
            call = cervejaService.insereCerveja(cerveja);
            mensagem = "inserida";
        }
        else{
            cerveja.setEstabelecimento(estabelecimento.getText().toString());
            cerveja.setMarca(marca.getText().toString());
            cerveja.setTipo(tipo.getText().toString());
            float val = Float.valueOf(valor.getText().toString());
            cerveja.setValor(val);
            call = cervejaService.atualizaCerveja(cerveja.getId_cerveja(), cerveja);
            mensagem = "atualizada";
        }
        call.enqueue(new Callback<Cerveja>() {
            @Override
            public void onResponse(Call<Cerveja> call, Response<Cerveja> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Cerveja " + mensagem +  " com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cerveja> call, Throwable t) {
                Log.e("ERROR ", t.getMessage());
            }
        });
    }

    public void excluir(View view){
        CervejaService cervejaService = ServiceGenerator.createService(CervejaService.class);
        Call<Cerveja> call = cervejaService.excluiCerveja(cerveja.getId_cerveja());
        call.enqueue(new Callback<Cerveja>() {
            @Override
            public void onResponse(Call<Cerveja> call, Response<Cerveja> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Cerveja excluída com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cerveja> call, Throwable t) {
                Log.e("ERRO ", t.getMessage());
            }
        });
        Intent intent = new Intent(CadastroCervejaActivity.this, ListaDetalhesCestaActivity.class);
        startActivity(intent);
    }

    public void sair(View view){
        Intent intent = new Intent(CadastroCervejaActivity.this, ListaDetalhesCestaActivity.class);
        startActivity(intent);
    }
}
