package es.centroafuera.rolappeame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonajeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Personaje> personajes;
    private LayoutInflater layoutInflater;

    @Override
    public int getCount() {
        return 0;
    }

    public PersonajeAdapter(Context context, ArrayList<Personaje> personajes) {
        this.context = context;
        this.personajes = personajes;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        //A la izquierda
        ImageView avatar;
        //Como tal
        TextView nombre;
        TextView raza;
        TextView oficio;
        //A un lado saldrá el nombre y al otro el número
        TextView fuerza;
        TextView agilidad;
        TextView percepcion;
        TextView constitucion;
        TextView inteligencia;
        TextView carisma;

        //FUTURO: la ubicación
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (convertView  == null){
            convertView = layoutInflater.inflate(R.layout.item_personaje, null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = convertView.findViewById(R.id.IVavatar);
            viewHolder.raza = convertView.findViewById(R.id.TVraza);
            viewHolder.oficio = convertView.findViewById(R.id.TVoficio);
            viewHolder.fuerza = convertView.findViewById(R.id.TVfuerza);
            viewHolder.agilidad = convertView.findViewById(R.id.TVagilidad);
            viewHolder.percepcion = convertView.findViewById(R.id.TVpercepcion);
            viewHolder.constitucion = convertView.findViewById(R.id.TVconstitucion);
            viewHolder.inteligencia = convertView.findViewById(R.id.TVinteligencia);
            viewHolder.carisma = convertView.findViewById(R.id.TVcarisma);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Personaje personaje = personajes.get(pos);
        viewHolder.avatar.setImageBitmap(personaje.getImagen());
        viewHolder.nombre.setText(personaje.getNombre());
        viewHolder.raza.setText(personaje.getRaza().toString());
        viewHolder.oficio.setText(personaje.getOficio().toString());
        viewHolder.fuerza.setText("@string/fuerza" + personaje.getFuerza());
        viewHolder.agilidad.setText("@string/agilidad" + personaje.getAgilidad());
        viewHolder.percepcion.setText("@string/percepci_n" + personaje.getPercepcion());
        viewHolder.constitucion.setText("@string/constituci_n" + personaje.getConstitucion());
        viewHolder.inteligencia.setText("@string/inteligencia" + personaje.getInteligencia());
        viewHolder.carisma.setText("@string/carisma" + personaje.getCarisma());

        return convertView;
    }

}
