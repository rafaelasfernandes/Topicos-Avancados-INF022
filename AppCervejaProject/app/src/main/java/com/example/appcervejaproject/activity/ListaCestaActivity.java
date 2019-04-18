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
import com.example.appcervejaproject.adapter.CestaAdapter;
import com.example.appcervejaproject.model.Cesta;
import com.example.appcervejaproject.rest.CestaService;
import com.example.appcervejaproject.rest.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListaCestaActivity extends AppCompatActivity {

    private ListView listView;
    private List<Cesta> cestas; /*lista de cestas cadastradas*/
    private List<Cesta> cestasFiltradas = new ArrayList<>(); /*listas de cestas consultadas*/
    private CestaService cestaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cesta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listview_Cestas); /*vincula o xml com os atributos do java*/

        cestaService = ServiceGenerator.createService(CestaService.class);
        Call<List<Cesta>> call = cestaService.getCestas();

        call.enqueue(new Callback<List<Cesta>>() {
            @Override
            public void onResponse(Call<List<Cesta>> call, Response<List<Cesta>> response) {
                if(response.isSuccessful()) {
                    cestasFiltradas = response.body();
                    CestaAdapter adapter = new CestaAdapter(ListaCestaActivity.this, cestasFiltradas);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Cesta>> call, Throwable t) {
                Log.e("ERROR ", t.getMessage());
            }
        });

        registerForContextMenu(listView); /*quando o listview for pressionado, ele abre o menu de contexto*/

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaCestaActivity.this, CadastroCestaActivity.class);
                startActivity(intent);
            }
        });
    }


    /*exibir o menu */
     public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal_cesta, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView(); /* pesquisar*/
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pesquisaCesta(newText);
                return false;
            }
        });
        return true;
    }

    public void irListaEstabelecimento(MenuItem item){
        Intent intent = new Intent(ListaCestaActivity.this, ListaEstabelecimentoActivity.class);
        startActivity(intent);
    }

    public void irListaMarca(MenuItem item){
        Intent intent = new Intent(ListaCestaActivity.this, ListaMarcaActivity.class);
        startActivity(intent);
    }

    public void irListaTipo(MenuItem item){
        Intent intent = new Intent(ListaCestaActivity.this, ListaTipoActivity.class);
        startActivity(intent);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_contexto_cesta, menu);
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Cesta cestaExcluir = cestasFiltradas.get(menuInfo.position);
        Intent intent = new Intent(this, CadastroCestaActivity.class);
        intent.putExtra("cesta", cestaExcluir);

        startActivity(intent);
    }


    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Cesta cestaAtualizar = cestasFiltradas.get(menuInfo.position);
        Intent intent = new Intent(this, CadastroCestaActivity.class);
        intent.putExtra("cesta", cestaAtualizar);

        startActivity(intent);
    }

    public void cadastroCerveja(MenuItem item){
        Intent intent = new Intent(ListaCestaActivity.this, CadastroCervejaActivity.class);
        startActivity(intent);
    }

    /*metodo que procura uma cesta na lista que tenha o nome que foi digitado*/
    public void pesquisaCesta(String nome){
        cestasFiltradas.clear();
        for(Cesta cesta : cestas){
            if(cesta.getNome().toLowerCase().contains(nome.toLowerCase())){
                cestasFiltradas.add(cesta);
            }
        }
        listView.invalidateViews();
    }
}
