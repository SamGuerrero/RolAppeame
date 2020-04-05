package es.centroafuera.rolappeame;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TextoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Texto> textos;
    private LayoutInflater layoutInflater;

    public TextoAdapter(Fragment context, ArrayList<Texto> textos) {
        this.context = context.getContext();
        this.textos = textos;
        layoutInflater = LayoutInflater.from(context.getContext());
    }

    @Override
    public int getCount() {
        return textos.size();
    }

    @Override
    public Object getItem(int i) {
        return textos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolderTexto {
        TextView titulo;
        TextView contenido;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolderTexto holder = null;

        if (convertView  == null){
            convertView = layoutInflater.inflate(R.layout.item_acercade, null);
            holder = new ViewHolderTexto();
            holder.titulo = (TextView) convertView.findViewById(R.id.tvTitulo);
            holder.contenido = (TextView) convertView.findViewById(R.id.tvContenido);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolderTexto) convertView.getTag();
        }

        Texto texto = textos.get(pos);
        holder.titulo.setText(texto.getTitulo());
        holder.contenido.setText(texto.getTexto());

        return convertView;
    }
}