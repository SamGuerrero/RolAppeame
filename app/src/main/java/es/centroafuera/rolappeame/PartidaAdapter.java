package es.centroafuera.rolappeame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PartidaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Partida> partidas;
    private LayoutInflater layoutInflater;

    @Override
    public int getCount() {
        return partidas.size();
    }

    public PartidaAdapter(Fragment context, ArrayList<Partida> partidas) {
        this.context = context.getContext();
        this.partidas = partidas;
        layoutInflater = LayoutInflater.from(context.getContext());
    }

    @Override
    public Object getItem(int i) {
        return partidas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return partidas.get(i).getId();
    }

    static class ViewHolder {
        //A la izquierda
        ImageView avatar;
        //Como tal
        TextView nombre;
        TextView tipoPartida;

        //A un lado saldrá el nombre y al otro los números
        TextView vida;
        TextView ataque;
        TextView defensa;

        //FUTURO: la ubicación
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        PartidaAdapter.ViewHolder viewHolder = null;

        if (convertView  == null){
            convertView = layoutInflater.inflate(R.layout.item_partida, null);
            viewHolder = new PartidaAdapter.ViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.IVavatar);
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.TVnombre);
            viewHolder.tipoPartida = (TextView) convertView.findViewById(R.id.TVtipoPartida);
            viewHolder.vida = (TextView) convertView.findViewById(R.id.TVvida);
            viewHolder.ataque =(TextView)  convertView.findViewById(R.id.TVataque);
            viewHolder.defensa = (TextView) convertView.findViewById(R.id.TVdefensa);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (PartidaAdapter.ViewHolder) convertView.getTag();
        }

        Partida partida = partidas.get(pos);
        String vidaTexto = context.getString(R.string.vida) + partida.getMinVida() + ") - (" + partida.getMaxVida() + ")";
        String ataqueTexto = context.getString(R.string.ataque) + partida.getMinAtaque() + ") - (" + partida.getMaxAtaque() + ")";
        String defensaTexto = context.getString(R.string.defensa) + partida.getMinDefensa() + ") - (" + partida.getMaxDefensa() + ")";

        viewHolder.avatar.setImageBitmap(partida.getImagen());
        viewHolder.nombre.setText(partida.getNombre());
        viewHolder.tipoPartida.setText(partida.getTipoPartida().toString());
        viewHolder.vida.setText(vidaTexto);
        viewHolder.ataque.setText(ataqueTexto);
        viewHolder.defensa.setText(defensaTexto);

        return convertView;
    }

}

