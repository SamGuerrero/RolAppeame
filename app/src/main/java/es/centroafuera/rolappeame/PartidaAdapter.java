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

        TextView jugadores;

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
            viewHolder.jugadores = (TextView) convertView.findViewById(R.id.tvJugadores);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (PartidaAdapter.ViewHolder) convertView.getTag();
        }

        Partida partida = partidas.get(pos);

        viewHolder.avatar.setImageBitmap(partida.getImagen());
        viewHolder.nombre.setText(partida.getNombre());
        viewHolder.tipoPartida.setText(partida.getTipoPartida().toString());

        String listaJugadores = "Jugadores: ";
        if (partida.getJugadores().size() > 0) {
            for (String jugador : partida.getJugadores())
                listaJugadores = listaJugadores + jugador + ", ";
            listaJugadores = listaJugadores.substring(0, listaJugadores.length() - 2); //Esta línea quita el ", "
        }else{
            listaJugadores = "Sin jugadores";
        }
        viewHolder.jugadores.setText(listaJugadores);

        return convertView;
    }

}

