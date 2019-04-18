package com.example.appcervejaproject.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appcervejaproject.R;
import com.example.appcervejaproject.model.Estabelecimento;

import java.util.List;

public class EstabelecimentoAdapter extends BaseAdapter {

    private List<Estabelecimento> estabelecimentos;
    private Activity activity;

    public EstabelecimentoAdapter(Activity activity, List<Estabelecimento> estabelecimentos){
        this.activity = activity;
        this.estabelecimentos = estabelecimentos;
    }

    @Override
    public int getCount() {
        return estabelecimentos.size();
    }

    @Override
    public Object getItem(int position) {
        return estabelecimentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_lista_estabelecimento, parent, false);
        TextView nomeEstabelecimento = view.findViewById(R.id.textView_NomeEstab);

        Estabelecimento estabelecimento = estabelecimentos.get(position);
        nomeEstabelecimento.setText(estabelecimento.getNome());
        return view;
    }
}
