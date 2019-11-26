package es.centroafuera.rolappeame;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CrearPersonaje extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearpersonaje);

        //Coger y añadir ClickListener en botones
        Button BTvolver = findViewById(R.id.BTvolver);
        BTvolver.setOnClickListener(this);
        Button BTcontinuar = findViewById(R.id.BTcontinuar);
        BTcontinuar.setOnClickListener(this);

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
        ArrayList<String> nivel = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            nivel.add(Integer.toString(i));
        Spinner Snivel = findViewById(R.id.Snivel);
        ArrayAdapter<String> adaptadorNivel = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nivel);
        Snivel.setAdapter(adaptadorNivel);

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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BTvolver:
                onBackPressed();
                break;

            case R.id.BTcontinuar: //TODO: Recoger todos los datos y guardarlos en la base de datos
                break;

                //TODO: Que en un mensaje salga información de cada atributo
            case R.id.BTinfoAgilidad:
            case R.id.BTinfoCarisma:
            case R.id.BTinfoConstitucion:
            case R.id.BTinfoFuerza:
            case R.id.BTinfoInteligencia:
            case R.id.BTinfoPercepcion: break;

            //TODO: Cambiar el textView en función de si aumenta o disminuye su atributo
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

    }

    public void sumarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(String.valueOf(pantalla));
        puntos++;
        pantalla.setText(puntos);
    }

    public void restarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(String.valueOf(pantalla));
        puntos--;
        pantalla.setText(puntos);
    }
}
