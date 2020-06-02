package es.centroafuera.rolappeame.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import es.centroafuera.rolappeame.CrearPartida;
import es.centroafuera.rolappeame.CrearPersonaje;
import es.centroafuera.rolappeame.models.Partida;
import es.centroafuera.rolappeame.adapters.PartidaAdapter;
import es.centroafuera.rolappeame.models.Personaje;
import es.centroafuera.rolappeame.adapters.PersonajeAdapter;
import es.centroafuera.rolappeame.R;
import es.centroafuera.rolappeame.models.Usuario;
import es.centroafuera.rolappeame.Utils;
import es.centroafuera.rolappeame.VistaPartida;
import es.centroafuera.rolappeame.VistaPersonaje;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    View root;

    ArrayList<Personaje> personajes = new ArrayList<>();
    PersonajeAdapter personajeAdapter;
    ListView lvPersonajes;

    ArrayList<Partida> partidas = new ArrayList<>();
    PartidaAdapter partidaAdapter;
    ListView lvPartidas;

    TabHost tabHost;
    Usuario usuario;
    ViewGroup container;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, this)
                .commit();

        root = inflater.inflate(R.layout.fragment_home, container, false);
        this.container = container;
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String nombre = prefs.getString("Usuario", "John Doe");
        usuario = new Usuario(nombre);


        //Tab
        tabHost = root.findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec(getString(R.string.personajes));
        spec.setContent(R.id.personajes);
        spec.setIndicator(getString(R.string.personajes));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(getString(R.string.partidas));
        spec.setContent(R.id.partidas);
        spec.setIndicator(getString(R.string.partidas));
        tabHost.addTab(spec);

        //añado los listeners
        FloatingActionButton anadirFAB = root.findViewById(R.id.anadirFAB);
        anadirFAB.setOnClickListener(this);
        FloatingActionButton FABanadir = root.findViewById(R.id.FABanadir);
        FABanadir.setOnClickListener(this);

        //Obtengo los personajes y los muestro en su tabhost
        lvPersonajes = root.findViewById(R.id.partidasLV);
        getPersonajesFromFirebase(this);
        registerForContextMenu(lvPersonajes);
        lvPersonajes.setAdapter(personajeAdapter);


        //Obtengo las partidas y las muestro en su tabhost
        lvPartidas = root.findViewById(R.id.LVpartidas);
        getPartidasFromFirebase(this);
        registerForContextMenu(lvPartidas);
        lvPartidas.setAdapter(partidaAdapter);

        return root;
    }

    public void getPersonajesFromFirebase(final HomeFragment activity){
        personajes = Utils.getPersonajes(usuario);

        //Esto es un comentario como arriba de la página que te dirá si tienes o no partidas
        TextView comentario = root.findViewById(R.id.comentarioTV);
        if (personajes.size() == 0)
            comentario.setText("Aquí se mostrarán tus personajes cuando guardes alguno");
        else
            comentario.setText(getString(R.string.comentario));

        personajeAdapter = new PersonajeAdapter(activity, personajes);
    }

    public void getPartidasFromFirebase(final HomeFragment activity){
        partidas = Utils.getPartidas(usuario);

        //Esto es un comentario como arriba de la página que te dirá si tienes o no partidas
        TextView comentario = root.findViewById(R.id.TVcomentario);
        if (partidas.size() == 0)
            comentario.setText(getString(R.string.comentarioInicial));
        else
            comentario.setText("Tus partidas:");

        partidaAdapter = new PartidaAdapter(activity, partidas);

    }

    //Cuando vuelve
    public void onResume() {
        super.onResume();
        getPersonajesFromFirebase(this);
        personajeAdapter.notifyDataSetChanged();
        getPartidasFromFirebase(this);
        partidaAdapter.notifyDataSetChanged();

    }

    public void onPause() {
        super.onPause();
    }

    //Si aprietas el float action button
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.anadirFAB: case R.id.FABanadir:

                //Elegir entre Master y Jugador
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(getString(R.string.pregunta_partida))
                        .setPositiveButton(getString(R.string.jugador),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        crearPersonaje();
                                    }
                                })
                        .setNeutralButton("Master",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        crearPartida();
                                    }
                                });
                builder.create().show();



                break;
        }
    }

    public void crearPersonaje(){
        Intent intent = new Intent(getContext(), CrearPersonaje.class);
        startActivity(intent);
    }

    public void crearPartida(){
        Intent intent = new Intent(getContext(), CrearPartida.class);
        startActivity(intent);
    }

    @Override //Infla el menú contextual
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.elige, menu);
    }

    @Override//Dentro del menú contextual
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = menuInfo.position;

        switch (item.getItemId()){
            case R.id.imVer:
                Intent intent;

                if (tabHost.getCurrentTabTag().equals(getString(R.string.personajes))) {
                    intent = new Intent(getContext(), VistaPersonaje.class);
                    intent.putExtra("ID", personajes.get(pos).getIdReal()); //Estoy pasando mal el Id
                }else{
                    intent = new Intent(getContext(), VistaPartida.class);
                    intent.putExtra("ID", partidas.get(pos).getIdReal()); //Estoy pasando mal el Id
                }

                startActivity(intent);
                break;

            case R.id.imEliminar:
                if (tabHost.getCurrentTabTag().equals(getString(R.string.personajes))) {
                    Utils.eliminarPersonaje(pos, getContext(), personajes, usuario);

                }else{
                    Utils.eliminarPartida(pos, getContext(), partidas, usuario);

                }

                break;

            default: break;
        }

        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}