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

import com.example.appcervejaproject.DAO.EstabelecimentoDAO;
import com.example.appcervejaproject.R;
import com.example.appcervejaproject.adapter.EstabelecimentoAdapter;
import com.example.appcervejaproject.model.Estabelecimento;
import com.example.appcervejaproject.rest.EstabelecimentoService;
import com.example.appcervejaproject.rest.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaEstabelecimentoActivity extends AppCompatActivity {

    private ListView listView;
    private List<Estabelecimento> estabelecimentos;
    private List<Estabelecimento> estabelecimentosFiltrados = new ArrayList<>();
    EstabelecimentoService estabelecimentoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estabelecimento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Lista de Estabelecimentos");


        listView = findViewById(R.id.listview_Estabelecimento);

        estabelecimentoService = ServiceGenerator.createService(EstabelecimentoService.class);
        Call<List<Estabelecimento>> call = estabelecimentoService.getEstabelecimentos();

        call.enqueue(new Callback<List<Estabelecimento>>() {
            @Override
            public void onResponse(Call<List<Estabelecimento>> call, Response<List<Estabelecimento>> response) {
                if (response.isSuccessful()) {
                    estabelecimentosFiltrados = response.body();
                    EstabelecimentoAdapter adaptador = new EstabelecimentoAdapter(ListaEstabelecimentoActivity.this, estabelecimentosFiltrados);
                    listView.setAdapter(adaptador);
                }
            }

            @Override
            public void onFailure(Call<List<Estabelecimento>> call, Throwable t) {
                Log.e("ERROR ", t.getMessage());
            }
        });

        registerForContextMenu(listView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaEstabelecimentoActivity.this, CadastroEstabelecimentoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ListaCestaActivity.class));
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal_estabelecimento, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pesquisaEstabelecimento(newText);
                return false;
            }
        });
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_contexto_estabelecimento, menu);
    }

    public void excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Estabelecimento estabelecimentoExcluir = estabelecimentosFiltrados.get(menuInfo.position);
        Intent intent = new Intent(this, CadastroEstabelecimentoActivity.class);
        intent.putExtra("estabelecimento", estabelecimentoExcluir);

        startActivity(intent);
    }


    public void atualizar(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Estabelecimento estabelecimentoAtualizar = estabelecimentosFiltrados.get(menuInfo.position);
        Intent intent = new Intent(this, CadastroEstabelecimentoActivity.class);
        intent.putExtra("estabelecimento", estabelecimentoAtualizar);

        startActivity(intent);
    }

    public void pesquisaEstabelecimento(String nome) {
        estabelecimentosFiltrados.clear();
        for (Estabelecimento estabelecimento : estabelecimentos) {
            if (estabelecimento.getNome().toLowerCase().contains(nome.toLowerCase())) {
                estabelecimentosFiltrados.add(estabelecimento);
            }
        }
        listView.invalidateViews();
    }
}
