package com.example.appcervejaproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.appcervejaproject.DAO.MarcaDAO;
import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Marca;

public class CadastroMarcaActivity extends AppCompatActivity {

    private EditText nome;
    private MarcaDAO marcaDao;
    private Marca marca = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_marca);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Marca");

        nome = findViewById(R.id.editText_NomeMarca);
        marcaDao = new MarcaDAO(this);

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
        if(marca == null) {
            Marca marca = new Marca();
            marca.setNome(nome.getText().toString());
            marcaDao.inserir(marca);
        }
        else{
            marca.setNome(nome.getText().toString());
            marcaDao.atualizar(marca);
        }
        Intent intent = new Intent(CadastroMarcaActivity.this, ListaMarcaActivity.class);
        startActivity(intent);
    }

}
