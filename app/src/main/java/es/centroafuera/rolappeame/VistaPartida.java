package es.centroafuera.rolappeame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import es.centroafuera.rolappeame.models.Partida;
import es.centroafuera.rolappeame.models.Texto;
import es.centroafuera.rolappeame.models.TipoPartida;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class VistaPartida extends AppCompatActivity implements View.OnClickListener {
    Partida partida;
    String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistapartida);

        //Recojo el id String que es la key de la base de datos
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        getPartidaId(id);

        //Botones
        Button btPersonajes = findViewById(R.id.btPersonajes);
        Button btBestias = findViewById(R.id.btBestias);
        Button btEquipo = findViewById(R.id.btEquipo);
        Button btConjuros = findViewById(R.id.btConjuros);
        Button btDado = findViewById(R.id.btDado);
        Button btRasgos =findViewById(R.id.btRasgos);
        Button btSitios = findViewById(R.id.btSitios);
        Button btCalendario = findViewById(R.id.btCalendario);

        btConjuros.setOnClickListener(this);
        btRasgos.setOnClickListener(this);
        btDado.setOnClickListener(this);
        btCalendario.setOnClickListener(this);
        btSitios.setOnClickListener(this);

        Button volver = findViewById(R.id.btVolver);
        Button guardar = findViewById(R.id.btGuardar);
        volver.setOnClickListener(this);
        guardar.setOnClickListener(this);

    }

    public void getPartidaId(String id){
        partida = new Partida();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Obtengo una lista de la base de datos
        DatabaseReference myRef = database.getReference(Utils.TABLA_PARTIDAS); //La clase en Java

        //Partidas > id
        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {

                String nombre = (String) ds.child(Utils.NOMBRE_PARTIDA).getValue();
                Bitmap imagen = null;
                TipoPartida tipoPartida = TipoPartida.DnD;
                ArrayList razas = new ArrayList<>();
                ArrayList clases = new ArrayList<>();
                ArrayList rasgos = new ArrayList<>();
                ArrayList conjuros = new ArrayList<>();

                try{
                    imagen = Utils.StringToBitMap(ds.child(Utils.IMAGEN_PARTIDA).getValue().toString());

                }catch (NullPointerException e){}

                try{
                tipoPartida = TipoPartida.valueOf(ds.child("tipoPartida").getValue().toString());
                }catch (NullPointerException e){}

                try{
                    razas = (ArrayList) ds.child(Utils.TABLA_RAZAS).getValue();
                    clases = (ArrayList) ds.child(Utils.TABLA_CLASES).getValue();
                    rasgos = (ArrayList) ds.child(Utils.TABLA_RASGOS).getValue();
                    conjuros = (ArrayList) ds.child(Utils.TABLA_CONJUROS).getValue();
                }catch (NullPointerException e){}
                tipoPartida = TipoPartida.valueOf(ds.child("tipoPartida").getValue().toString());

                partida.setIdReal(id);
                partida.setNombre(nombre);
                partida.setTipoPartida(tipoPartida);
                partida.setImagen(imagen);
                partida.setRazas(Utils.arrayToLinkHAshMap(razas));
                partida.setClases(Utils.arrayToLinkHAshMap(clases));
                partida.setRasgos(Utils.arrayToLinkHAshMap(rasgos));
                partida.setConjuros(Utils.arrayToLinkHAshMap(conjuros));

                //Muestro datos
                TextView tvNombre = findViewById(R.id.TVnombre);
                TextView tvTipoPartida = findViewById(R.id.TVtipoPartida);
                ImageView ivAvatar = findViewById(R.id.IVavatar);

                tvNombre.setText(partida.getNombre());
                tvTipoPartida.setText(partida.getTipoPartida().toString());
                ivAvatar.setImageBitmap(partida.getImagen());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btVolver:
                onBackPressed();
                break;

            case R.id.btGuardar:
                guardarPartida();

            case R.id.btDado:
                tirarDado();
                break;
                
            case R.id.btCalendario:
                Intent intent = new Intent(this, ActivityListaEventos.class);
                startActivity(intent);
                break;

            case R.id.btSitios:
                Intent intent2 = new Intent(this, ActivityListaSitios.class);
                startActivity(intent2);
                break;
                
            case R.id.btConjuros:
                mostrarLista(Utils.TABLA_CONJUROS, partida.getConjuros());
                break;
                
            case R.id.btRasgos:
                mostrarLista(Utils.TABLA_RASGOS, partida.getRasgos());
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

    private void mostrarLista(String tabla, LinkedHashMap<Texto, Boolean> lista) {

        CharSequence[] nombres = new CharSequence[lista.size()];
        int i = 0;
        for (Map.Entry m: lista.entrySet()) {
            nombres[i] = ((Texto) m.getKey()).getTitulo();
            i++;
        }

        boolean[] booleanos = new boolean[lista.size()];
        int j = 0;
        for (Map.Entry m: lista.entrySet()) {
            booleanos[j] = (boolean) m.getValue();
            j++;
        }


        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);

        builder
                .setTitle(tabla)
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                })
        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
        .setMultiChoiceItems(nombres, booleanos, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        })
        .show();

    }


    public void guardarPartida(){
        //Estadisticas
        ArrayList razasIncluidas = new ArrayList();
        for (Map.Entry m : partida.getRazas().entrySet()) {
            if (m.getValue().equals(true))
                razasIncluidas.add(m.getKey());
        }
        ArrayList clasesIncluidas = new ArrayList();
        for (Map.Entry m : partida.getClases().entrySet()) {
            if (m.getValue().equals(true))
                clasesIncluidas.add(m.getKey());
        }

        ArrayList rasgosIncluidos = new ArrayList();
        for (Map.Entry m : partida.getRasgos().entrySet()) {
            if (m.getValue().equals(true))
                rasgosIncluidos.add(m.getKey());
        }

        ArrayList conjurosIncluidos = new ArrayList();
        for (Map.Entry m : partida.getConjuros().entrySet()) {
            if (m.getValue().equals(true))
                conjurosIncluidos.add(m.getKey());
        }

        //Guardo los datos en un HashMap que luego guardaré
        Map<String, Object> nuevaPartida = new HashMap<>();
        nuevaPartida.put(Utils.NOMBRE_PARTIDA, partida.getNombre());
        nuevaPartida.put(Utils.IMAGEN_PARTIDA, Utils.BitMapToString(partida.getImagen()));
        nuevaPartida.put("tipoPartida", partida.getTipoPartida());

        nuevaPartida.put(Utils.TABLA_RAZAS, razasIncluidas);
        nuevaPartida.put(Utils.TABLA_CLASES, clasesIncluidas);
        nuevaPartida.put(Utils.TABLA_RASGOS, rasgosIncluidos);
        nuevaPartida.put(Utils.TABLA_CONJUROS, conjurosIncluidos);

        nuevaPartida.put("jugadores", partida.getJugadores());
        nuevaPartida.put("citas", partida.getCitas());


        Utils.actualizarPartida(nuevaPartida, partida, VistaPartida.this);

    }

    //Infla el Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fijo, menu);
        return true;
    }


}
