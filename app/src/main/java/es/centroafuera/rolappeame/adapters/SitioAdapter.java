package es.centroafuera.rolappeame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import es.centroafuera.rolappeame.R;
import es.centroafuera.rolappeame.models.Sitio;

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
        return sitios.size();
    }

    @Override
    public Object getItem(int position) {
        return sitios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SitioAdapter.ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_sitio, null);
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
        viewHolder.latitud.setText(String.valueOf(sitio.getLatitud()));
        viewHolder.longitud.setText(String.valueOf(sitio.getLongitud()));

        return convertView;
    }
}
