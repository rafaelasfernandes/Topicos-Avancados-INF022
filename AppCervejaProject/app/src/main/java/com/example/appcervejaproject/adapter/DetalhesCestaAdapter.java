package com.example.appcervejaproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Cerveja;

import java.util.List;

public class DetalhesCestaAdapter extends BaseAdapter {

    private List<Cerveja> cervejas;
    private Activity activity;

    public DetalhesCestaAdapter(Activity activity, List<Cerveja> cervejas){
        this.activity = activity;
        this.cervejas = cervejas;
    }

    @Override
    public int getCount() {
        return cervejas.size();
    }

    @Override
    public Object getItem(int position) {
        return cervejas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cervejas.get(position).getId_cerveja();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_lista_detalhes_cesta, parent, false);
        TextView estabelecimentoDet = view.findViewById(R.id.textView_NomeEstabelecimento);
        TextView marcaDet = view.findViewById(R.id.textView_NomeMarcaCerv);
        TextView tipoDet = view.findViewById(R.id.textView_TipoCerv);
        TextView valorDet = view.findViewById(R.id.textView_valorCerv);

        Cerveja cerveja = cervejas.get(position);

        estabelecimentoDet.setText(cerveja.getEstabelecimento());
        marcaDet.setText(cerveja.getMarca());
        tipoDet.setText(cerveja.getTipo());
        String val = String.valueOf(cerveja.getValor());
        valorDet.setText(val);
        return view;
    }
}
