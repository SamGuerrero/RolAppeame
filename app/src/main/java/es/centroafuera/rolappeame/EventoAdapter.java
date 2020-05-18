package es.centroafuera.rolappeame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Evento> eventos;
    private LayoutInflater layoutInflater;

    public EventoAdapter(Context context, ArrayList<Evento> eventos) {
        this.context = context;
        this.eventos = eventos;
        this.layoutInflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        TextView nombre;
        TextView descripcion;
        TextView fecha;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventoAdapter.ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_partida, null);
            viewHolder = new EventoAdapter.ViewHolder();
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.tvNombre);
            viewHolder.descripcion = (TextView) convertView.findViewById(R.id.tvDescripcion);
            viewHolder.fecha = (TextView) convertView.findViewById(R.id.tvFecha);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (EventoAdapter.ViewHolder) convertView.getTag();
        }

        Evento evento = eventos.get(position);

        viewHolder.nombre.setText(evento.getNombre());
        viewHolder.descripcion.setText(evento.getDescripcion());
        viewHolder.fecha.setText((CharSequence) evento.getFecha());

        return convertView;
    }
}
