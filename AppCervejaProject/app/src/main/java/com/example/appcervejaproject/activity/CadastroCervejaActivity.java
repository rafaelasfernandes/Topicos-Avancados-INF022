package com.example.appcervejaproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcervejaproject.DAO.CervejaDAO;

import com.example.appcervejaproject.DAO.EstabelecimentoDAO;
import com.example.appcervejaproject.R;

import com.example.appcervejaproject.adapter.EstabelecimentoAdapter;
import com.example.appcervejaproject.model.Cerveja;
import com.example.appcervejaproject.model.Estabelecimento;

import java.util.List;


public class CadastroCervejaActivity extends AppCompatActivity {

    private TextView cesta;
    private AutoCompleteTextView estabelecimento;
    private AutoCompleteTextView marca;
    private AutoCompleteTextView tipo;
    private EditText valor;
    private CervejaDAO cervejaDao;
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
        cervejaDao = new CervejaDAO(this);

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

        if (cerveja == null) {
            Cerveja cerveja = new Cerveja();
            cerveja.setEstabelecimento(estabelecimento.getText().toString());
            cerveja.setMarca(marca.getText().toString());
            cerveja.setTipo(tipo.getText().toString());
            float val = Float.valueOf(valor.getText().toString());
            cerveja.setValor(val);
            cervejaDao.inserir(cerveja);
        }
        else{
            cerveja.setEstabelecimento(estabelecimento.getText().toString());
            cerveja.setMarca(marca.getText().toString());
            cerveja.setTipo(tipo.getText().toString());
            float val = Float.valueOf(valor.getText().toString());
            cerveja.setValor(val);
            cervejaDao.atualizar(cerveja);
        }
        Toast toast = Toast.makeText(this, "Cerveja cadastrada com sucesso!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void sair(View view){
        Intent intent = new Intent(CadastroCervejaActivity.this, ListaDetalhesCestaActivity.class);
        startActivity(intent);
    }
}
