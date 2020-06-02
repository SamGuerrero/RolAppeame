package es.centroafuera.rolappeame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.centroafuera.rolappeame.adapters.SitioAdapter;
import es.centroafuera.rolappeame.models.Sitio;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ActivityListaSitios extends AppCompatActivity implements View.OnClickListener {
    SitioAdapter adapter;
    ArrayList<Sitio> sitios;
    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarlista);
        setTitle("Sitios");

        listView = findViewById(R.id.listView);
        FloatingActionButton fab = findViewById(R.id.fab);

        getSitiosFromDatabase();
        adapter = new SitioAdapter(this, sitios);
        listView.setAdapter(adapter);

        fab.setOnClickListener(this);
    }

    private void getSitiosFromDatabase() {
        sitios = new ArrayList<>();
        //Obtengo una lista de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Utils.TABLA_SITIOS); //La clase en Java

        //TODO: Si no voy a guardar desde mapa, que muestre nomás todos los sitios disponibles

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){
                    sitios.clear();

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = ds.child(Utils.NOMBRE_SITIO).getValue().toString();
                        String descripcion = ds.child(Utils.DESCRIPCION_SITIO).getValue().toString();
                        double latitud = Double.parseDouble(ds.child(Utils.LATITUD_SITIO).getValue().toString());
                        double longitud = Double.parseDouble(ds.child(Utils.LONGITUD_SITIO).getValue().toString());

                        Sitio sitioT = new Sitio(nombre, descripcion, latitud, longitud);
                        sitioT.setIdT(ds.getKey());
                        sitios.add(sitioT);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        sitios.add(new Sitio("Sitio prueba", "Como no coge de internet tendré que probarlo a mano", 90.87, 40.36));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                //Mostrar mapa
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;

            default: break;
        }
    }
}
