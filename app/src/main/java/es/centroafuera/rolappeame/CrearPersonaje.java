package es.centroafuera.rolappeame;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

            case R.id.BTmenosAgilidad:
            case R.id.BTmasCarisma:
            case R.id.BTmenosCarisma:
            case R.id.BTmasConstitucion:
            case R.id.BTmenosConstitucion:
            case R.id.BTmasFuerza:
            case R.id.BTmenosFuerza:
            case R.id.BTmasInteligencia:
            case R.id.BTmenosInteligencia:
            case R.id.BTmasPercepcion:
            case R.id.BTmenosPercepcion: break;

            default: break;
        }

    }
}
