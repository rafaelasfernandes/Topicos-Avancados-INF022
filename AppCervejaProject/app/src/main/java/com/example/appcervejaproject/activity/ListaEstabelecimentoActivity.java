package com.example.appcervejaproject.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListaEstabelecimentoActivity extends AppCompatActivity {

    private ListView listView;
    private EstabelecimentoDAO estabelecimentoDao;
    private List<Estabelecimento> estabelecimentos;
    private List<Estabelecimento> estabelecimentosFiltrados = new ArrayList<>();

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
        estabelecimentoDao = new EstabelecimentoDAO(this);
        estabelecimentos = estabelecimentoDao.listarEstabelecimentos();
        estabelecimentosFiltrados.addAll(estabelecimentos);

        EstabelecimentoAdapter adaptador = new EstabelecimentoAdapter(this,estabelecimentosFiltrados);
        listView.setAdapter(adaptador);

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
/*
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ListaCestaActivity.class));
                finish();
                break;
            default:break;
        }
        return true;
    }*/

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, ListaCestaActivity.class));
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu){
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

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_contexto_estabelecimento, menu);
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Estabelecimento estabelecimentoExcluir = estabelecimentosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir o estabelecimento?")
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        estabelecimentosFiltrados.remove(estabelecimentoExcluir);
                        estabelecimentos.remove(estabelecimentoExcluir);
                        estabelecimentoDao.excluir(estabelecimentoExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Estabelecimento estabelecimentoAtualizar = estabelecimentosFiltrados.get(menuInfo.position);
        Intent intent = new Intent(this, CadastroEstabelecimentoActivity.class);
        intent.putExtra("estabelecimento", estabelecimentoAtualizar);
        startActivity(intent);
    }

    public void pesquisaEstabelecimento(String nome){
        estabelecimentosFiltrados.clear();
        for(Estabelecimento estabelecimento : estabelecimentos){
            if(estabelecimento.getNome().toLowerCase().contains(nome.toLowerCase())){
                estabelecimentosFiltrados.add(estabelecimento);
            }
        }
        listView.invalidateViews();
    }

    @Override
    public void onResume(){
        super.onResume();
        estabelecimentos = estabelecimentoDao.listarEstabelecimentos();
        estabelecimentosFiltrados.clear();
        estabelecimentosFiltrados.addAll(estabelecimentos);
        listView.invalidateViews();
    }
}
