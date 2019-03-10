package com.example.appcervejaproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Tipo;

import java.util.List;

public class TipoAdapter extends BaseAdapter {

    private List<Tipo> tipos;
    private Activity activity;

    public TipoAdapter(Activity activity, List<Tipo> tipos){
        this.activity = activity;
        this.tipos = tipos;
    }

    @Override
    public int getCount() {
        return tipos.size();
    }

    @Override
    public Object getItem(int position) {
        return tipos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tipos.get(position).getId_tipo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_lista_tipo, parent, false);
        TextView descricaoTipo = view.findViewById(R.id.textView_DescricaoTipo);
        TextView volumeTipo = view.findViewById(R.id.textView_Volume);

        Tipo tipo = tipos.get(position);
        descricaoTipo.setText(tipo.getDescricao());
        String vol = String.valueOf(tipo.getVolume());
        volumeTipo.setText(vol);
        return view;
    }
}
