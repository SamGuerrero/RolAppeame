package es.centroafuera.rolappeame;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PersonajeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Personaje> personajes;
    private LayoutInflater layoutInflater;

    @Override
    public int getCount() {
        return personajes.size();
    }

    public PersonajeAdapter(Fragment context, ArrayList<Personaje> personajes) {
        this.context = context.getContext();
        this.personajes = personajes;
        layoutInflater = LayoutInflater.from(context.getContext());
    }

    @Override
    public Object getItem(int i) {
        return personajes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return personajes.get(i).getId();
    }

    static class ViewHolder {
        //A la izquierda
        ImageView avatar;
        //Como tal
        TextView nombre;
        TextView raza;
        TextView oficio;
        //A un lado saldrÃ¡ el nombre y al otro el nÃºmero
        TextView fuerza;
        TextView agilidad;
        TextView percepcion;
        TextView constitucion;
        TextView inteligencia;
        TextView carisma;

        //FUTURO: la ubicaciÃ³n
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (convertView  == null){
            convertView = layoutInflater.inflate(R.layout.item_personaje, null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.IVavatar);
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.TVnombre);
            viewHolder.raza = (TextView) convertView.findViewById(R.id.TVraza);
            viewHolder.oficio = (TextView) convertView.findViewById(R.id.TVoficio);
            viewHolder.fuerza =(TextView)  convertView.findViewById(R.id.TVfuerza);
            viewHolder.agilidad = (TextView) convertView.findViewById(R.id.TVagilidad);
            viewHolder.percepcion = (TextView) convertView.findViewById(R.id.TVpercepcion);
            viewHolder.constitucion = (TextView) convertView.findViewById(R.id.TVconstitucion);
            viewHolder.inteligencia = (TextView) convertView.findViewById(R.id.TVinteligencia);
            viewHolder.carisma = (TextView) convertView.findViewById(R.id.TVcarisma);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Personaje personaje = personajes.get(pos);
        String fuerzaTexto = context.getString(R.string.fuerza) + " " + personaje.getFuerza();
        String agilidadTexto = context.getString(R.string.agilidad) + " " + personaje.getAgilidad();
        String percepcionTexto = context.getString(R.string.percepci_n) + " " + personaje.getPercepcion();
        String constitucionTexto = context.getString(R.string.constituci_n) + " " + personaje.getConstitucion();
        String inteligenciaTexto = context.getString(R.string.inteligencia) + " " + personaje.getInteligencia();
        String carismaTexto = context.getString(R.string.carisma) + " " + personaje.getCarisma();


        viewHolder.avatar.setImageBitmap(personaje.getImagen());
        viewHolder.nombre.setText(personaje.getNombre());
        viewHolder.raza.setText(personaje.getRaza().toString());
        viewHolder.oficio.setText(personaje.getOficio().toString());
        viewHolder.fuerza.setText(fuerzaTexto);
        viewHolder.agilidad.setText(agilidadTexto);
        viewHolder.percepcion.setText(percepcionTexto);
        viewHolder.constitucion.setText(constitucionTexto);
        viewHolder.inteligencia.setText(inteligenciaTexto);
        viewHolder.carisma.setText(carismaTexto);

        return convertView;
    }

}
