package es.centroafuera.rolappeame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonajeAdapter extends RecyclerView.Adapter<PersonajeAdapter.ViewHolder> {
    private ArrayList<Personaje> personajes;
    private int resources;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //R.layout.item_personaje
        View view = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        Personaje personaje = personajes.get(i);

        String fuerzaTexto = "Fuerza: " + personaje.getFuerza();
        String agilidadTexto = "Agilidad: " + personaje.getAgilidad();
        String percepcionTexto = "Percepcion: " + personaje.getPercepcion();
        String constitucionTexto = "Constitucion: " + personaje.getConstitucion();
        String inteligenciaTexto = "Inteligencia: " + personaje.getInteligencia();
        String carismaTexto = "Carisma: " + personaje.getCarisma();

        holder.avatar.setImageBitmap(personaje.getImagen());
        holder.nombre.setText(personaje.getNombre());
        holder.raza.setText(personaje.getRaza().toString());
        holder.oficio.setText(personaje.getOficio().toString());
        holder.fuerza.setText(fuerzaTexto);
        holder.agilidad.setText(agilidadTexto);
        holder.percepcion.setText(percepcionTexto);
        holder.constitucion.setText(constitucionTexto);
        holder.inteligencia.setText(inteligenciaTexto);
        holder.carisma.setText(carismaTexto);

    }

    @Override
    public int getItemCount() {
        return personajes.size();
    }


    public PersonajeAdapter(ArrayList<Personaje> personajes, int resources) {
        this.resources = resources;
        this.personajes = personajes;

        //layoutInflater = LayoutInflater.from(context);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
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

        View view;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.view = view;

            //A la izquierda
            this.avatar = (ImageView) view.findViewById(R.id.IVavatar);
            //Como tal
            this.nombre = (TextView) view.findViewById(R.id.TVnombre);
            this.raza = (TextView) view.findViewById(R.id.TVraza);
            this.oficio = (TextView) view.findViewById(R.id.TVoficio);
            //A un lado saldrá el nombre y al otro el número
            this.fuerza = (TextView) view.findViewById(R.id.TVfuerza);
            this.agilidad = (TextView) view.findViewById(R.id.TVagilidad);
            this.percepcion = (TextView) view.findViewById(R.id.TVpercepcion);
            this.constitucion = (TextView) view.findViewById(R.id.TVconstitucion);
            this.inteligencia = (TextView) view.findViewById(R.id.TVinteligencia);
            this.carisma = (TextView) view.findViewById(R.id.TVcarisma);

        }

        //FUTURO: la ubicación
    }

    /*@Override
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
    }*/

}
