package es.centroafuera.rolappeame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashMap;
import java.util.Map;

public class VistaPersonaje extends AppCompatActivity implements View.OnClickListener{
    Personaje personaje;
    String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistapersonaje);

        //Recojo los datos del Intent para tener al personaje
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        personaje = Utils.getPersonajeId(id, VistaPersonaje.this);

        //Muestro los datos por pantalla
        try {
            ImageView ivAvatar = findViewById(R.id.IVavatar);
            ivAvatar.setImageBitmap(personaje.getImagen());

            TextView tvNombre = findViewById(R.id.TVnombre);
            tvNombre.setText(personaje.getNombre());
            TextView tvRaza = findViewById(R.id.TVraza);
            tvRaza.setText(personaje.getSubraza());
            TextView tvOficio = findViewById(R.id.TVoficio);
            tvOficio.setText(personaje.getOficio());

            TextView tvFuerza = findViewById(R.id.puntosFuerza);
            tvFuerza.setText(Integer.toString(personaje.getFuerza()));
            TextView tvAgilidad = findViewById(R.id.puntosAgilidad);
            tvAgilidad.setText(Integer.toString(personaje.getAgilidad()));
            TextView tvPercepcion = findViewById(R.id.puntosPercepcion);
            tvPercepcion.setText(Integer.toString(personaje.getPercepcion()));
            TextView tvConstitucion = findViewById(R.id.puntosConstitucion);
            tvConstitucion.setText(Integer.toString(personaje.getConstitucion()));
            TextView tvInteligencia = findViewById(R.id.puntosInteligencia);
            tvInteligencia.setText(Integer.toString(personaje.getInteligencia()));
            TextView tvCarisma = findViewById(R.id.puntosCarisma);
            tvCarisma.setText(Integer.toString(personaje.getCarisma()));
        }catch (NullPointerException e){}

        //Añado listeners a los botones
        //Coger y añadir ClickListener en botones
        Button BTvolver = findViewById(R.id.btVolver);
        BTvolver.setOnClickListener(this);
        Button BTguardar = findViewById(R.id.BTguardar);
        BTguardar.setOnClickListener(this);

        Button BTmasAgilidad = findViewById(R.id.BTmasAgilidad);
        BTmasAgilidad.setOnClickListener(this);
        Button BTmenosAgilidad = findViewById(R.id.BTmenosAgilidad);
        BTmenosAgilidad.setOnClickListener(this);
        Button BTmasCarisma = findViewById(R.id.BTmasCarisma);
        BTmasCarisma.setOnClickListener(this);
        Button BTmenosCarisma = findViewById(R.id.BTmenosCarisma);
        BTmenosCarisma.setOnClickListener(this);
        Button BTmasConstitucion = findViewById(R.id.BTmasConstitucion);
        BTmasConstitucion.setOnClickListener(this);
        Button BTmenosConstitucion = findViewById(R.id.BTmenosConstitucion);
        BTmenosConstitucion.setOnClickListener(this);
        Button BTmasFuerza = findViewById(R.id.BTmasFuerza);
        BTmasFuerza.setOnClickListener(this);
        Button BTmenosFuerza = findViewById(R.id.BTmenosFuerza);
        BTmenosFuerza.setOnClickListener(this);
        Button BTmasInteligencia = findViewById(R.id.BTmasInteligencia);
        BTmasInteligencia.setOnClickListener(this);
        Button BTmenosInteligencia = findViewById(R.id.BTmenosInteligencia);
        BTmenosInteligencia.setOnClickListener(this);
        Button BTmasPercepcion = findViewById(R.id.BTmasPercepcion);
        BTmasPercepcion.setOnClickListener(this);
        Button BTmenosPercepcion = findViewById(R.id.BTmenosPercepcion);
        BTmenosPercepcion.setOnClickListener(this);

        Button btDado = findViewById(R.id.btDado);
        btDado.setOnClickListener(this);
        Button btRasgos = findViewById(R.id.btRasgos);
        btRasgos.setOnClickListener(this);
        Button btCitas = findViewById(R.id.btCitas);
        btCitas.setOnClickListener(this);
        Button btMagia =findViewById(R.id.btMagia);
        btMagia.setOnClickListener(this);

        TextView BTinfoAgilidad = findViewById(R.id.agilidad);
        BTinfoAgilidad.setOnClickListener(this);
        TextView BTinfoCarisma = findViewById(R.id.carisma);
        BTinfoCarisma.setOnClickListener(this);
        TextView BTinfoConstitucion = findViewById(R.id.constitucion);
        BTinfoConstitucion.setOnClickListener(this);
        TextView BTinfoFuerza = findViewById(R.id.fuerza);
        BTinfoFuerza.setOnClickListener(this);
        TextView BTinfoInteligencia = findViewById(R.id.inteligencia);
        BTinfoInteligencia.setOnClickListener(this);
        TextView BTinfoPercepcion = findViewById(R.id.percepcion);
        BTinfoPercepcion.setOnClickListener(this);

        TextView tvComentario = findViewById(R.id.TVpuntos);
        tvComentario.setText(R.string.suerte);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btVolver:
                onBackPressed();
                break;

            case R.id.BTguardar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.seguro))
                            .setPositiveButton(getString(R.string.si),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            guardarPersonaje();
                                        }
                                    })
                            .setNegativeButton(getString(R.string.no),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // En este caso se cierra directamente el diálogo y no se hace nada más
                                            dialog.dismiss();
                                        }
                                    });
                    builder.create().show();

                break;

            case R.id.agilidad:
                vistaInformacion(getString(R.string.desAgilidad));
                break;

            case R.id.carisma:
                vistaInformacion(getString(R.string.desCarisma));
                break;

            case R.id.constitucion:
                vistaInformacion(getString(R.string.desConstitucion));
                break;

            case R.id.fuerza:
                vistaInformacion(getString(R.string.desFuerza));
                break;

            case R.id.inteligencia:
                vistaInformacion(getString(R.string.desInteligencia));
                break;

            case R.id.percepcion:
                vistaInformacion(getString(R.string.desPercepcion));
                break;

            //Cambia los puntos
            case R.id.BTmasAgilidad:
                TextView puntosAgilidad = findViewById(R.id.puntosAgilidad);
                sumarPuntos(puntosAgilidad);

                break;
            case R.id.BTmenosAgilidad:
                TextView puntosagilidad = findViewById(R.id.puntosAgilidad);
                restarPuntos(puntosagilidad);

                break;
            case R.id.BTmasCarisma:
                TextView puntosCarisma = findViewById(R.id.puntosCarisma);
                sumarPuntos(puntosCarisma);

                break;
            case R.id.BTmenosCarisma:
                TextView puntoscarisma = findViewById(R.id.puntosCarisma);
                restarPuntos(puntoscarisma);

                break;
            case R.id.BTmasConstitucion:
                TextView puntosConstitucion = findViewById(R.id.puntosConstitucion);
                sumarPuntos(puntosConstitucion);

                break;
            case R.id.BTmenosConstitucion:
                TextView puntosconstitucion = findViewById(R.id.puntosConstitucion);
                restarPuntos(puntosconstitucion);

                break;
            case R.id.BTmasFuerza:
                TextView puntosFuerza = findViewById(R.id.puntosFuerza);
                sumarPuntos(puntosFuerza);

                break;
            case R.id.BTmenosFuerza:
                TextView puntosfuerza = findViewById(R.id.puntosFuerza);
                restarPuntos(puntosfuerza);

                break;
            case R.id.BTmasInteligencia:
                TextView puntosInteligencia = findViewById(R.id.puntosInteligencia);
                sumarPuntos(puntosInteligencia);

                break;
            case R.id.BTmenosInteligencia:
                TextView puntosinteligencia = findViewById(R.id.puntosInteligencia);
                restarPuntos(puntosinteligencia);

                break;
            case R.id.BTmasPercepcion:
                TextView puntosPercepcion = findViewById(R.id.puntosPercepcion);
                sumarPuntos(puntosPercepcion);

                break;
            case R.id.BTmenosPercepcion:
                TextView puntospercepcion = findViewById(R.id.puntosPercepcion);
                restarPuntos(puntospercepcion);

                break;

            case R.id.btRasgos:
                //mostrarListaRasgos();
                break;

            case R.id.btDado:
                tirarDado();
                break;

            case R.id.btCitas:
                Intent intent = new Intent(this, ActivityListaEventos.class);
                startActivity(intent);
                break;

            case R.id.btMagia:
                //mostrarListaMagia();
                break;


            default: break;
        }

    }

    private void tirarDado() {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        final Button btDado = findViewById(R.id.btDado);

        builder.setTitle("Dado")
                .setNeutralButton("d6", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int aleatorio = (int) (Math.random()*7);
                        btDado.setText("D6: " + aleatorio);
                    }
                })
                .setNegativeButton("d20", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int aleatorio = (int) (Math.random()*21);
                        btDado.setText("D20: " + aleatorio);
                    }
                })
                .setPositiveButton("d100", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int aleatorio = (int) (Math.random()*101);
                        btDado.setText("D100: " + aleatorio);
                    }
                }).show();

    }

    public void sumarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        puntos++;
        pantalla.setText(String.valueOf(puntos));
        TextView tvComentario = findViewById(R.id.TVpuntos);
        tvComentario.setText(R.string.buenasuerte);
    }

    public void restarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        puntos--;
        pantalla.setText(String.valueOf(puntos));
        TextView tvComentario = findViewById(R.id.TVpuntos);
        tvComentario.setText(R.string.malasuerte);
    }

    public void guardarPersonaje(){
        //Estadisticas
        TextView TVpuntosAgilidad = findViewById(R.id.puntosAgilidad);
        TextView TVpuntoscarisma = findViewById(R.id.puntosCarisma);
        TextView TVpuntosconstitucion = findViewById(R.id.puntosConstitucion);
        TextView TVpuntosfuerza = findViewById(R.id.puntosFuerza);
        TextView TVpuntosinteligencia = findViewById(R.id.puntosInteligencia);
        TextView TVpuntospercepcion = findViewById(R.id.puntosPercepcion);

        personaje.setAgilidad( Integer.parseInt(TVpuntosAgilidad.getText().toString()) );
        personaje.setCarisma( Integer.parseInt(TVpuntoscarisma.getText().toString()));
        personaje.setConstitucion( Integer.parseInt(TVpuntosconstitucion.getText().toString()));
        personaje.setFuerza( Integer.parseInt(TVpuntosfuerza.getText().toString()));
        personaje.setInteligencia( Integer.parseInt(TVpuntosinteligencia.getText().toString()));
        personaje.setPercepcion( Integer.parseInt(TVpuntospercepcion.getText().toString()));

        //Guardo los datos en un HashMap que luego guardaré
        Map<String, Object> personajeT = new HashMap<>();
        personajeT.put(Utils.NOMBRE_PERSONAJE, personaje.getNombre());
        personajeT.put("raza", personaje.getRaza());
        personajeT.put("oficio", personaje.getOficio());
        personajeT.put("fuerza", personaje.getFuerza());
        personajeT.put("agilidad", personaje.getAgilidad());
        personajeT.put("percepcion", personaje.getPercepcion());
        personajeT.put("constitucion", personaje.getConstitucion());
        personajeT.put("inteligencia", personaje.getInteligencia());
        personajeT.put("carisma", personaje.getCarisma());
        personajeT.put(Utils.IMAGEN_PERSONAJE, Utils.BitMapToString(personaje.getImagen()));


        Utils.actualizarPersonaje(personajeT, personaje, VistaPersonaje.this);

        onBackPressed();
    }

    public void vistaInformacion(String descripcion){
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.toast_info, (ViewGroup) findViewById(R.id.lyToastInfo));

        TextView info = v.findViewById(R.id.tvInfo);
        info.setText(descripcion);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(v);
        toast.show();
    }

    //Infla el Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fijo, menu);
        return true;
    }

}
