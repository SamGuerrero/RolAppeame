package es.centroafuera.rolappeame;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ArrayList<Marker> markers;

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
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        marcadores(googleMap);
    }

    public void marcadores(GoogleMap googleMap){
        mMap = googleMap;
        markers = new ArrayList<>();
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        final LatLng punto1 = new LatLng(	40.47960826297022, -3.665799020694628);
        final LatLng punto2 = new LatLng(	40.43629956289038, 	-3.6504096146979457);
        final LatLng punto3 = new LatLng(	40.38513183038149, -3.761911556533903);
        final LatLng punto4 = new LatLng(	40.406792648617134, 	-3.7046086019457167);
        final LatLng punto5 = new LatLng(	40.473402418562216, -3.5785233191070316);
        final LatLng punto6 = new LatLng(	40.427538776051804, 	-3.732422725110936);
        final LatLng punto7 = new LatLng(	40.49082275610172, -3.761911556533903);
        final LatLng punto8 = new LatLng(	40.42027705095574, 	-3.621741078700336);
        final LatLng punto9 = new LatLng(	40.45843103553325, -3.785855667139094);
        final LatLng punto10 = new LatLng(	40.42996840999023, 	-3.622184001035289);

        Marker temporal = mMap.addMarker(new MarkerOptions().position(punto1).title("Auditorio Carmen Laforet"));
        markers.add(temporal);
        temporal = mMap.addMarker(new MarkerOptions().position(punto2).title("Auditorio al aire libre"));
        markers.add(temporal);
        temporal = mMap.addMarker(new MarkerOptions().position(punto3).title("Auditorio y sala de exposiciones Paco de Lucía"));
        markers.add(temporal);
        temporal = mMap.addMarker(new MarkerOptions().position(punto4).title("Centro Comunitario Casino de la Reina"));
        markers.add(temporal);
        temporal = mMap.addMarker(new MarkerOptions().position(punto5).title("Centro Cultural - Centro Socio Cultural Villa de Barajas"));
        markers.add(temporal);
        temporal = mMap.addMarker(new MarkerOptions().position(punto6).title("Centro Cultural Agustín Díaz (Moncloa - Aravaca)"));
        markers.add(temporal);
        temporal = mMap.addMarker(new MarkerOptions().position(punto7).title("Centro Cultural Alfredo Kraus"));
        markers.add(temporal);
        temporal = mMap.addMarker(new MarkerOptions().position(punto8).title("Centro Cultural Antonio Machado"));
        markers.add(temporal);
        temporal = mMap.addMarker(new MarkerOptions().position(punto9).title("Centro Cultural Aravaca"));
        markers.add(temporal);
        temporal = mMap.addMarker(new MarkerOptions().position(punto10).title("Centro Cultural Auditorio Parque El Paraíso"));
        markers.add(temporal);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(punto1));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //Con esto guardaré los sitios donde quedar


        for (Marker markerT : markers){
            if (marker.equals(markerT)){
                //Así veo qué marker me clickean
                String lat, lng;

                lat = String.valueOf(marker.getPosition().latitude);
                lng = String.valueOf(marker.getPosition().longitude);

                Toast.makeText(this, "Esto queda en " + lat + ", " + lng, Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        InformacionFragment.newInstance(marker.getTitle(), "Este sitio es ideal para hacer tus movidas roleras").show(getSupportFragmentManager(), null);

    }
}
