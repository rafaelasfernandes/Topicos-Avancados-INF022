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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.appcervejaproject.DAO.TipoDAO;
import com.example.appcervejaproject.R;
import com.example.appcervejaproject.adapter.TipoAdapter;
import com.example.appcervejaproject.model.Tipo;

import java.util.ArrayList;
import java.util.List;


public class ListaTipoActivity extends AppCompatActivity {

    private ListView listView;
    private TipoDAO tipoDao;
    private List<Tipo> tipos;
    private List<Tipo> tiposFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tipo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Lista de Tipos");

        listView = findViewById(R.id.listview_Tipo);
        tipoDao = new TipoDAO(this);
        tipos = tipoDao.listarTipos();
        tiposFiltrados.addAll(tipos);

        TipoAdapter adaptador = new TipoAdapter(this, tiposFiltrados);
        listView.setAdapter(adaptador);

        registerForContextMenu(listView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaTipoActivity.this, CadastroTipoActivity.class);
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
        menuInflater.inflate(R.menu.menu_principal_tipo, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pesquisaTipo(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_contexto_tipo, menu);
    }

    public void excluirTipo(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Tipo tipoExcluir = tiposFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir o tipo?")
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tiposFiltrados.remove(tipoExcluir);
                        tipos.remove(tipoExcluir);
                        tipoDao.excluir(tipoExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void atualizarTipo(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Tipo tipoAtualizar = tiposFiltrados.get(menuInfo.position);
        Intent intent = new Intent(this, CadastroTipoActivity.class);
        intent.putExtra("tipo", tipoAtualizar);
        startActivity(intent);
    }

    public void pesquisaTipo(String descricao){
        tiposFiltrados.clear();
        for(Tipo tipo : tipos){
            if(tipo.getDescricao().toLowerCase().contains(descricao.toLowerCase())){
                tiposFiltrados.add(tipo);
            }
        }
        listView.invalidateViews();
    }

    @Override
    public void onResume(){
        super.onResume();
        tipos = tipoDao.listarTipos();
        tiposFiltrados.clear();
        tiposFiltrados.addAll(tipos);
        listView.invalidateViews();
    }
}
