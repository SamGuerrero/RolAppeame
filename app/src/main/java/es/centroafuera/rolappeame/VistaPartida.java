package es.centroafuera.rolappeame;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


        //Boto
        Button volver = findViewById(R.id.btVolver);
        volver.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btVolver:
                onBackPressed();
                break;

            default: break;
        }
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
