package com.example.appcervejaproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Marca;

import java.util.List;

public class MarcaAdapter extends BaseAdapter{
    private List<Marca> marcas;
    private Activity activity;

    public MarcaAdapter(Activity activity, List<Marca> marcas){
        this.activity = activity;
        this.marcas = marcas;
    }

    @Override
    public int getCount() {
        return marcas.size();
    }

    @Override
    public Object getItem(int position) {
        return marcas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return marcas.get(position).getId_marca();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_lista_marca, parent, false);
        TextView nomeMarca = view.findViewById(R.id.textView_NomeMarca);

        Marca marca = marcas.get(position);
        nomeMarca.setText(marca.getNome());
        return view;
    }
}
