package es.centroafuera.rolappeame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AcercaDe extends AppCompatActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

        Button BTaceptar = findViewById(R.id.BTaceptar);
        BTaceptar.setOnClickListener(this);

        Texto acercaDe = new Texto("Acerca de", "RolAppeame es una applicación desarrollada por:\n G. Samantha Guerrero Macías. GSam en GitHub.\n La aplicación está diseñada para poder anotar fácilmente y con rápidez las estadísticas de tu personaje en un juego de rol. Al mismo tiempo que te ofrece información sobre las distintas opciones del jeugo y su lore");
        Texto condicionesLegales = new Texto("Condiciones legales", "Al aceptar las condiciones aquí recogidas, autorizas a G. Samantha Guerrero a recopilar información sobre la aplicación. Su rendimiento, su uso y sus errores, si es que se produjera alguno.");

        ArrayList<Texto> textos = new ArrayList<>();
        textos.add(acercaDe);
        textos.add(condicionesLegales);
        ListView LVprincipal = findViewById(R.id.LVprincipal);
        TextoAdapter adaptador = new TextoAdapter(this, textos);
        LVprincipal.setAdapter(adaptador);
    }


    @Override
    public void onClick(View v) {
        CheckBox CBaceptar = findViewById(R.id.CBaceptar);

        if (!CBaceptar.isChecked()) {
            Toast.makeText(this, "Debes aceptar primero las condiciones legales", Toast.LENGTH_SHORT).show();
            return;
        }else
            onBackPressed();

    }
}
