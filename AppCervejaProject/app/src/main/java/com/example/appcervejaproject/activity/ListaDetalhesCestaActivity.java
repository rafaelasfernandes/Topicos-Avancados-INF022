package com.example.appcervejaproject.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.example.appcervejaproject.DAO.CervejaDAO;
import com.example.appcervejaproject.R;
import com.example.appcervejaproject.adapter.DetalhesCestaAdapter;
import com.example.appcervejaproject.model.Cerveja;

import java.util.ArrayList;
import java.util.List;

public class ListaDetalhesCestaActivity extends AppCompatActivity {

    private ListView listView;
    private CervejaDAO cervejaDao;
    private List<Cerveja> cervejas;
    private List<Cerveja> cervejasFiltradas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_detalhes_cesta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.drawable.outline_arrow_back_white_24);
        getSupportActionBar().setTitle(" Detalhes da Cesta");

        listView = findViewById(R.id.listview_DetalhesCesta);
        cervejaDao = new CervejaDAO(this);
        cervejas = cervejaDao.listarCervejas();
        cervejasFiltradas.addAll(cervejas);

        DetalhesCestaAdapter adaptador = new DetalhesCestaAdapter(this, cervejasFiltradas);
        listView.setAdapter(adaptador);

        registerForContextMenu(listView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(ListaDetalhesCestaActivity.this, CadastroCestaActivity.class);
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
        menuInflater.inflate(R.menu.menu_principal_detalhes_cesta, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pesquisaCerveja(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_contexto_detalhes_cesta, menu);
    }

    public void excluirCerveja(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Cerveja cervejaExcluir = cervejasFiltradas.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir a cerveja?")
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cervejasFiltradas.remove(cervejaExcluir);
                        cervejas.remove(cervejaExcluir);
                        cervejaDao.excluir(cervejaExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void atualizarCerveja(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Cerveja cervejaAtualizar = cervejasFiltradas.get(menuInfo.position);
        Intent intent = new Intent(this, CadastroTipoActivity.class);
        intent.putExtra("cerveja", cervejaAtualizar);
        startActivity(intent);
    }

    public void pesquisaCerveja(String marca){
        cervejasFiltradas.clear();
        for(Cerveja cerveja : cervejas){
            if(cerveja.getMarca().toLowerCase().contains(marca.toLowerCase())){
                cervejasFiltradas.add(cerveja);
            }
        }
        listView.invalidateViews();
    }

    @Override
    public void onResume(){
        super.onResume();
        cervejas = cervejaDao.listarCervejas();
        cervejasFiltradas.clear();
        cervejasFiltradas.addAll(cervejas);
        listView.invalidateViews();
    }

}
