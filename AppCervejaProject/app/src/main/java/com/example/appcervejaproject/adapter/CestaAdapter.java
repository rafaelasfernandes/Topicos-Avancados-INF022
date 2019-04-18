package com.example.appcervejaproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Cesta;

import java.util.List;

public class CestaAdapter extends BaseAdapter {

    private List<Cesta> cestas;
    private Activity activity;

    public CestaAdapter(Activity activity, List<Cesta> cestas) {
        this.activity = activity;
        this.cestas = cestas;
    }

    @Override
    public int getCount() {
        return cestas.size();
    }

    @Override
    public Object getItem(int position) {
        return cestas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.item_lista_cesta, parent, false);

        TextView nome = view.findViewById(R.id.text_Nome);
        TextView data = view.findViewById(R.id.text_Data);

        Cesta cesta = cestas.get(position);

        nome.setText(cesta.getNome());
        data.setText(cesta.getData());
        return view;
    }
}
