package es.centroafuera.rolappeame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import es.centroafuera.rolappeame.R;
import es.centroafuera.rolappeame.models.Evento;

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
        return eventos.size();
    }

    @Override
    public Object getItem(int position) {
        return eventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventoAdapter.ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_evento, null);
            viewHolder = new EventoAdapter.ViewHolder();
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.tvNombre);
            viewHolder.descripcion = (TextView) convertView.findViewById(R.id.tvDescripcion);
            viewHolder.fecha = (TextView) convertView.findViewById(R.id.tvFecha);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (EventoAdapter.ViewHolder) convertView.getTag();
        }

        Evento evento = eventos.get(position);

        String formato = "dd/MM/yyyy";
        SimpleDateFormat fechaFormateada = new SimpleDateFormat(formato);

        viewHolder.nombre.setText(evento.getNombre());
        viewHolder.descripcion.setText(evento.getDescripcion());
        viewHolder.fecha.setText(fechaFormateada.format(evento.getFecha()));

        return convertView;
    }
}
