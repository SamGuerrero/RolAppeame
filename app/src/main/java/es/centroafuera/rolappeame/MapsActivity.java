package es.centroafuera.rolappeame;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private GoogleMap mMap;
    private ArrayList<Sitio> sitios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Solicito permisos
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }else{
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }

        marcadores(googleMap);
    }

    public void marcadores(GoogleMap googleMap){
        mMap = googleMap;
        sitios = new ArrayList<>();
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        //Obtengo una lista de la base de datos
        DatabaseReference myRef = database.getReference(Utils.TABLA_SITIOS); //La clase en Java

        // Read from the database
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

                        final LatLng punto = new LatLng(sitioT.getLatitud(), sitioT.getLongitud());
                        mMap.addMarker(new MarkerOptions().position(punto).title(sitioT.getNombre()));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        for (Sitio sitio : sitios){
            if ((sitio.getLatitud() == marker.getPosition().latitude) && (sitio.getLongitud() == marker.getPosition().longitude)){
                Toast.makeText(this, getString(R.string.loc) + sitio.getLatitud() + ", " + sitio.getLongitud(), Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        for (Sitio sitio : sitios){
            if ((sitio.getLatitud() == marker.getPosition().latitude) && (sitio.getLongitud() == marker.getPosition().longitude)){
                InformacionFragment.newInstance(marker.getTitle(), sitio.getDescripcion()).show(getSupportFragmentManager(), null);
            }
        }

        //TODO: Añadir guardar sitios al mapa
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode == event.KEYCODE_BACK){
           Intent intentMapa = new Intent(this, MainActivity.class);
           intentMapa.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intentMapa);
       }
       return super.onKeyDown(keyCode, event);
    }

}
