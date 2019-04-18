package com.example.appcervejaproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Marca;
import com.example.appcervejaproject.rest.MarcaService;
import com.example.appcervejaproject.rest.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroMarcaActivity extends AppCompatActivity {

    private EditText nome;
    private Marca marca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_marca);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Marca");

        nome = findViewById(R.id.editText_NomeMarca);

        Intent intent = getIntent();
        if(intent.hasExtra("marca")){
            marca = (Marca) intent.getSerializableExtra("marca");
            nome.setText(marca.getNome());
        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, ListaMarcaActivity.class));
        finish();
    }

    public void cadastrar(View view){

        MarcaService marcaService = ServiceGenerator.createService(MarcaService.class);
        Call<Marca> call;
        final String mensagem;
        if(marca == null) {
            marca = new Marca();
            marca.setNome(nome.getText().toString());
            call = marcaService.insereMarca(marca);
            mensagem = "inserida";
        }
        else{
            marca.setNome(nome.getText().toString());
            call = marcaService.atualizaMarca(marca.getId_marca(), marca);
            mensagem = "atualizada";
        }

        call.enqueue(new Callback<Marca>() {
            @Override
            public void onResponse(Call<Marca> call, Response<Marca> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Marca " + mensagem + " com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Marca> call, Throwable t) {
                Log.e("ERRO ", t.getMessage());
            }
        });
        Intent intent = new Intent(CadastroMarcaActivity.this, ListaMarcaActivity.class);
        startActivity(intent);
    }

    public void excluir(View view){

        MarcaService marcaService = ServiceGenerator.createService(MarcaService.class);
        Call<Marca> call = marcaService.excluiMarca(marca.getId_marca());
        call.enqueue(new Callback<Marca>() {
            @Override
            public void onResponse(Call<Marca> call, Response<Marca> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Marca excluída com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Marca> call, Throwable t) {
                Log.e("ERRO ", t.getMessage());
            }
        });
        Intent intent = new Intent(CadastroMarcaActivity.this, ListaMarcaActivity.class);
        startActivity(intent);
    }

}
