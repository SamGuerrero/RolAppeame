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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class VistaPartida extends AppCompatActivity implements View.OnClickListener {
    Partida partida;
    String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistapartida);

        //Recojo el id String que es la key de la base de datos
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        partida = Utils.getPartidaId(id, VistaPartida.this);

        //Muestro datos
        TextView tvNombre = findViewById(R.id.TVnombre);
        TextView tvTipoPartida = findViewById(R.id.TVtipoPartida);
        ImageView ivAvatar = findViewById(R.id.IVavatar);

        tvNombre.setText(partida.getNombre());
        tvTipoPartida.setText(partida.getTipoPartida().toString());
        ivAvatar.setImageBitmap(partida.getImagen());

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
        volver.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btVolver:
                onBackPressed();
                break;

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
                //TODO: Guardar cambios
            }
        })
        .setMultiChoiceItems(nombres, booleanos, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        })
        .show();

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


    public void guardarPartida(){
        /*
        //Estadisticas
        TextView TvminVida = findViewById(R.id.minVida);
        TextView TVmaxVida = findViewById(R.id.maxVida);
        TextView TVminAtaque = findViewById(R.id.minAtaque);
        TextView TVmaxAtaque = findViewById(R.id.maxAtaque);
        TextView TVminDefensa = findViewById(R.id.minDefensa);
        TextView TVmaxDefensa = findViewById(R.id.maxDefensa);

        partida.setMinVida(Integer.parseInt(TvminVida.getText().toString()));
        partida.setMaxVida(Integer.parseInt(TVmaxVida.getText().toString()));
        partida.setMinAtaque(Integer.parseInt(TVminAtaque.getText().toString()));
        partida.setMaxAtaque(Integer.parseInt(TVmaxAtaque.getText().toString()));
        partida.setMinDefensa(Integer.parseInt(TVminDefensa.getText().toString()));
        partida.setMaxDefensa(Integer.parseInt(TVmaxDefensa.getText().toString()));

        //Guardo los datos en un HashMap que luego guardaré
        Map<String, Object> personajeT = new HashMap<>();
        personajeT.put(Utils.NOMBRE_PARTIDA, partida.getNombre());
        personajeT.put("tipoPartida", partida.getTipoPartida());
        personajeT.put("minVida", partida.getMinVida());
        personajeT.put("maxVida", partida.getMaxVida());
        personajeT.put("minAtaque", partida.getMinAtaque());
        personajeT.put("maxAtaque", partida.getMaxAtaque());
        personajeT.put("minDefensa", partida.getMinDefensa());
        personajeT.put("maxDefensa", partida.getMaxDefensa());
        personajeT.put(Utils.IMAGEN_PARTIDA, Utils.BitMapToString(partida.getImagen()));


        Utils.actualizaPartida(partidaT, partida, VistaPartida.this);

        */
    }

    //Infla el Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fijo, menu);
        return true;
    }


}
