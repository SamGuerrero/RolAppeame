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

        Texto acercaDe = new Texto(getString(R.string.About), getString(R.string.contenido_about));
        Texto condicionesLegales = new Texto(getString(R.string.titulo_condiciones), getString(R.string.contenido_condiciones));

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
            Toast.makeText(this, R.string.control, Toast.LENGTH_SHORT).show();
            return;
        }else
            onBackPressed();

    }
}
