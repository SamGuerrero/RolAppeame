package es.centroafuera.rolappeame;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class CrearPersonaje extends AppCompatActivity implements View.OnClickListener {
    //TODO: set puntos obligatorios, que no se puedan reducir según las combinaciones
    int puntosTotales = 10; //El máximo de puntos a repartir cuando eliges X clase y X raza
    int puntosActuales = 0; //Los puntos que llevas acumulados
    private final int AVATAR = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearpersonaje);

        //Coger y añadir ClickListener en botones
        Button BTvolver = findViewById(R.id.BTvolver);
        BTvolver.setOnClickListener(this);
        Button BTcontinuar = findViewById(R.id.BTcontinuar);
        BTcontinuar.setOnClickListener(this);
        ImageView IVavatar = findViewById(R.id.IVavatar);
        IVavatar.setOnClickListener(this);

        Button BTinfoAgilidad = findViewById(R.id.BTinfoAgilidad);
        BTinfoAgilidad.setOnClickListener(this);
        Button BTinfoCarisma = findViewById(R.id.BTinfoCarisma);
        BTinfoCarisma.setOnClickListener(this);
        Button BTinfoConstitucion = findViewById(R.id.BTinfoConstitucion);
        BTinfoConstitucion.setOnClickListener(this);
        Button BTinfoFuerza = findViewById(R.id.BTinfoFuerza);
        BTinfoFuerza.setOnClickListener(this);
        Button BTinfoInteligencia = findViewById(R.id.BTinfoInteligencia);
        BTinfoInteligencia.setOnClickListener(this);
        Button BTinfoPercepcion = findViewById(R.id.BTinfoPercepcion);
        BTinfoPercepcion.setOnClickListener(this);

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

        //Rellenar Spinners
        ArrayList<Raza> razas = new ArrayList<>();
        razas.add(Raza.GUNCH);
        razas.add(Raza.HUMANO);
        razas.add(Raza.MORULAK);
        razas.add(Raza.NOMADA_AZUL);
        razas.add(Raza.NULD);
        razas.add(Raza.REY_DRAGON);
        razas.add(Raza.SLORG);
        Spinner Sraza = findViewById(R.id.Sraza);
        ArrayAdapter<Raza> adaptadorRaza = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, razas);
        Sraza.setAdapter(adaptadorRaza);

        ArrayList<Oficio> oficios = new ArrayList<>();
        oficios.add(Oficio.ALQUIMISTA);
        oficios.add(Oficio.NOBLE);
        oficios.add(Oficio.SACERDOTE);
        oficios.add(Oficio.ASESINO);
        oficios.add(Oficio.BAILARINA);
        oficios.add(Oficio.BÁRBARO);
        oficios.add(Oficio.CAMARERA);
        oficios.add(Oficio.CAZADOR);
        oficios.add(Oficio.ESCLAVO);
        oficios.add(Oficio.ESCRIBA);
        oficios.add(Oficio.GALENO);
        oficios.add(Oficio.GLADIADOR);
        oficios.add(Oficio.GRANJERO);
        oficios.add(Oficio.HERRERO);
        oficios.add(Oficio.JUGLAR);
        oficios.add(Oficio.LADRON);
        oficios.add(Oficio.MAGO);
        oficios.add(Oficio.MERCADER);
        oficios.add(Oficio.MERCENARIO);
        oficios.add(Oficio.PILOTO);
        oficios.add(Oficio.PIRATA);
        oficios.add(Oficio.VERDUGO);
        oficios.add(Oficio.SOLDADO);
        Spinner Soficio = findViewById(R.id.Soficio);
        ArrayAdapter<Oficio> adaptadorOficio = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, oficios);
        Soficio.setAdapter(adaptadorOficio);

        //Inicializar mensaje de los puntos
        TextView TVpuntos = findViewById(R.id.TVpuntos);
        TVpuntos.setText("Tienes " + (puntosTotales-puntosActuales) +" puntos a repartir entre:");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BTvolver:
                onBackPressed();
                break;

            case R.id.BTcontinuar:
                if (puntosTotales > puntosActuales) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Te quedan puntos por repartir\n¿Quieres continuar?")
                            .setPositiveButton("Sí",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            guardarPersonaje();
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // En este caso se cierra directamente el diálogo y no se hace nada más
                                            dialog.dismiss();
                                        }
                                    });
                    builder.create().show();
                }else
                    guardarPersonaje();
                break;

            case R.id.IVavatar:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }else{
                    Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent2, AVATAR);
                }

                break;

                //TODO: Que en un mensaje salga información de cada atributo
            case R.id.BTinfoAgilidad:
            case R.id.BTinfoCarisma:
            case R.id.BTinfoConstitucion:
            case R.id.BTinfoFuerza:
            case R.id.BTinfoInteligencia:
            case R.id.BTinfoPercepcion: break;

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

            default: break;
        }

        //Se actualiza el mensaje de puntos
        TextView TVpuntos = findViewById(R.id.TVpuntos);
        TVpuntos.setText("Tienes " + (puntosTotales-puntosActuales) +" puntos a repartir entre:");

    }

    public void sumarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        if (puntosTotales > puntosActuales) {
            puntos++;
            puntosActuales++;
            pantalla.setText(String.valueOf(puntos));
        }
    }

    public void restarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        if (puntos > 0) {
            puntos--;
            puntosActuales--;
            pantalla.setText(String.valueOf(puntos));
        }
    }

    //TODO: Recoger todos los datos y guardarlos en la base de datos
    public void guardarPersonaje(){
        //Imagen
        ImageView IVavatar = findViewById(R.id.IVavatar);
        Bitmap imagen = ((BitmapDrawable) IVavatar.getDrawable()).getBitmap();

        //Básico
        TextView TVnombre = findViewById(R.id.TVnombre);
        Spinner Sraza = findViewById(R.id.Sraza);
        Spinner Soficio = findViewById(R.id.Soficio);


        //Estadisticas
        TextView TVpuntosAgilidad = findViewById(R.id.puntosAgilidad);
        TextView TVpuntoscarisma = findViewById(R.id.puntosCarisma);
        TextView TVpuntosconstitucion = findViewById(R.id.puntosConstitucion);
        TextView TVpuntosfuerza = findViewById(R.id.puntosFuerza);
        TextView TVpuntosinteligencia = findViewById(R.id.puntosInteligencia);
        TextView TVpuntospercepcion = findViewById(R.id.puntosPercepcion);

        String nombre = TVnombre.getText().toString();
        Raza raza = Raza.valueOf(Sraza.toString());
        Oficio oficio = Oficio.valueOf(Soficio.toString());

        int agilidad = Integer.parseInt(TVpuntosAgilidad.getText().toString());
        int carisma = Integer.parseInt(TVpuntoscarisma.getText().toString());
        int constitucion = Integer.parseInt(TVpuntosconstitucion.getText().toString());
        int fuerza = Integer.parseInt(TVpuntosfuerza.getText().toString());
        int inteligencia = Integer.parseInt(TVpuntosinteligencia.getText().toString());
        int percepcion = Integer.parseInt(TVpuntospercepcion.getText().toString());

        Personaje personaje = new Personaje(nombre, raza, oficio, fuerza, agilidad, percepcion, constitucion, inteligencia, carisma, imagen);

        BaseDeDatos bd = new BaseDeDatos(this);
        bd.nuevoPersonaje(personaje);

    }
}
