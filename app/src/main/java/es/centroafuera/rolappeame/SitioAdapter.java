package es.centroafuera.rolappeame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SitioAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Sitio> sitios;
    private LayoutInflater layoutInflater;

    public SitioAdapter(Context context, ArrayList<Sitio> sitios) {
        this.context = context;
        this.sitios = sitios;
        this.layoutInflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        TextView nombre;
        TextView descripcion;
        TextView latitud;
        TextView longitud;
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
        SitioAdapter.ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_partida, null);
            viewHolder = new SitioAdapter.ViewHolder();
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.tvNombre);
            viewHolder.descripcion = (TextView) convertView.findViewById(R.id.tvDescripcion);
            viewHolder.latitud = (TextView) convertView.findViewById(R.id.tvLatitud);
            viewHolder.longitud = (TextView) convertView.findViewById(R.id.tvLongitud);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (SitioAdapter.ViewHolder) convertView.getTag();
        }

        Sitio sitio = sitios.get(position);

        viewHolder.nombre.setText(sitio.getNombre());
        viewHolder.descripcion.setText(sitio.getDescripcion());
        viewHolder.latitud.setText((int) sitio.getLatitud());
        viewHolder.longitud.setText((int) sitio.getLongitud());

        return convertView;
    }
}
