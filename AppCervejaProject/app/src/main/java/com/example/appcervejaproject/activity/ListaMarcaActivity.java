package com.example.appcervejaproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.appcervejaproject.R;
import com.example.appcervejaproject.adapter.MarcaAdapter;
import com.example.appcervejaproject.model.Marca;
import com.example.appcervejaproject.rest.MarcaService;
import com.example.appcervejaproject.rest.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaMarcaActivity extends AppCompatActivity {

    private ListView listView;
    private List<Marca> marcas;
    private List<Marca> marcasFiltradas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_marca);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Lista de Marcas");

        listView = findViewById(R.id.listview_Marca);

        MarcaService marcaService = ServiceGenerator.createService(MarcaService.class);
        Call<List<Marca>> call = marcaService.getMarcas();

        call.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                if(response.isSuccessful()){
                    marcasFiltradas = response.body();
                    MarcaAdapter adaptador = new MarcaAdapter(ListaMarcaActivity.this, marcasFiltradas);
                    listView.setAdapter(adaptador);
                }
            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {
                Log.e("ERROR ", t.getMessage());
            }
        });

        registerForContextMenu(listView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaMarcaActivity.this, CadastroMarcaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, ListaCestaActivity.class));
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal_marca, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pesquisaMarca(newText);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_contexto_marca, menu);
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Marca marcaExcluir = marcasFiltradas.get(menuInfo.position);
        Intent intent = new Intent(this, CadastroMarcaActivity.class);
        intent.putExtra("marca", marcaExcluir);

        startActivity(intent);

    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Marca marcaAtualizar = marcasFiltradas.get(menuInfo.position);
        Intent intent = new Intent(this, CadastroMarcaActivity.class);
        intent.putExtra("marca", marcaAtualizar);

        startActivity(intent);
    }

    public void pesquisaMarca(String nome){
        marcasFiltradas.clear();
        for(Marca marca : marcas){
            if(marca.getNome().toLowerCase().contains(nome.toLowerCase())){
                marcasFiltradas.add(marca);
            }
        }
        listView.invalidateViews();
    }
}
