package es.centroafuera.rolappeame;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import es.centroafuera.rolappeame.models.Partida;
import es.centroafuera.rolappeame.models.Texto;
import es.centroafuera.rolappeame.models.TipoPartida;
import es.centroafuera.rolappeame.models.Usuario;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CrearPartida extends AppCompatActivity implements View.OnClickListener {
    boolean cambiofoto = false;
    private final int AVATAR = 1;
    List<String> grupoExpandible;
    HashMap<String, LinkedHashMap<Texto, Boolean>> itemExpandible;
    PartidaAdapterExpandible adapter;
    Partida partida;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearpartida);

        partida = new Partida();
        //Imagen de la partida
        ImageView ivAvatar = findViewById(R.id.IVavatar);

        //Relleno Spinner
        ArrayList<TipoPartida> tipos = new ArrayList<>();
        TipoPartida[] tiposArray = TipoPartida.values();
        tipos.addAll(Arrays.asList(tiposArray));
        Spinner SPtipos = findViewById(R.id.SPtipos);
        ArrayAdapter<TipoPartida> adaptadorTipos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tipos);
        SPtipos.setAdapter(adaptadorTipos);

        //Expandible List View
        ExpandableListView expandableListView = findViewById(R.id.ELVcaracteristicas);
        grupoExpandible = new ArrayList<>();
        itemExpandible = new HashMap<>();
        rellenarLista();
        adapter = new PartidaAdapterExpandible(this, grupoExpandible, itemExpandible);
        expandableListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //Botones
        Button volver = findViewById(R.id.btVolver);
        volver.setOnClickListener(this);
        Button continuar = findViewById(R.id.btGuardar);
        continuar.setOnClickListener(this);


    }

    private void rellenarLista() {
        grupoExpandible.add(Utils.TABLA_RAZAS);
        grupoExpandible.add(Utils.TABLA_CLASES);
        grupoExpandible.add(Utils.TABLA_RASGOS);
        grupoExpandible.add(Utils.TABLA_CONJUROS);

        LinkedHashMap<Texto, Boolean> listaRazas = getRazasFromDatabase();
        LinkedHashMap<Texto, Boolean> listaClases = getClasesFromDatabase();
        LinkedHashMap<Texto, Boolean> listaRasgos = getRasgosFromDatabase();
        LinkedHashMap<Texto, Boolean> listaConjuros = getConjurosFromDatabase();

        itemExpandible.put(grupoExpandible.get(0), listaRazas);
        itemExpandible.put(grupoExpandible.get(1), listaClases);
        itemExpandible.put(grupoExpandible.get(2), listaRasgos);
        itemExpandible.put(grupoExpandible.get(3), listaConjuros);
    }


    public LinkedHashMap<Texto, Boolean> getRazasFromDatabase() {
        final ArrayList<Texto> razas = new ArrayList<>();
        final LinkedHashMap<Texto, Boolean> razasF = new LinkedHashMap<>();;

        //Obtengo una lista de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Utils.TABLA_REGLAS); //La clase en Java

        // Read from the database
        myRef.child(Utils.TABLA_RAZAS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = ds.getKey();
                        String descripcion = String.valueOf(ds.child(Utils.RAZA_DESCRIPCION).getValue());
                        razas.add(new Texto(nombre, descripcion));
                    }


                    for (Texto raza: razas)
                        razasF.put(raza, true);


                    partida.setRazas(razasF);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return razasF;
    }

    /**Devuelve un ArrayList con las Clases existentes*/
    public LinkedHashMap<Texto, Boolean> getClasesFromDatabase() {
        final ArrayList<Texto> clases = new ArrayList<>();
        LinkedHashMap<Texto, Boolean> clasesF = new LinkedHashMap<>();

        //Obtengo una lista de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Utils.TABLA_REGLAS); //La clase en Java

        // Read from the database
        myRef.child(Utils.TABLA_CLASES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = ds.getKey();
                        String descripcion = String.valueOf(ds.child(Utils.CLASE_DESCRIPCION).getValue());
                        clases.add(new Texto(nombre, descripcion));
                    }

                    for (Texto clase: clases)
                        clasesF.put(clase, true);


                    partida.setClases(clasesF);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return clasesF;
    }

    /**Devuelve un ArrayList con los Rasgos existentes*/
    public LinkedHashMap<Texto, Boolean> getRasgosFromDatabase(){
        final ArrayList<Texto> rasgos = new ArrayList<>();
        LinkedHashMap<Texto, Boolean> rasgosF = new LinkedHashMap<>();

        //Obtengo una lista de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Utils.TABLA_REGLAS); //La clase en Java

        // Read from the database
        myRef.child(Utils.TABLA_RASGOS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = ds.getKey();
                        String descripcion = String.valueOf(ds.getValue());
                        rasgos.add(new Texto(nombre, descripcion));
                    }

                    for (Texto rasgo: rasgos)
                        rasgosF.put(rasgo, true);

                    partida.setRasgos(rasgosF);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return rasgosF;
    }

    /**Devuelve un ArrayList con los Conjuros existentes*/
    public LinkedHashMap<Texto, Boolean> getConjurosFromDatabase(){
        final ArrayList<Texto> conjuros = new ArrayList<>();
        LinkedHashMap<Texto, Boolean> conjurosF = new LinkedHashMap<>();

        //Obtengo una lista de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Utils.TABLA_REGLAS); //La clase en Java

        // Read from the database
        myRef.child(Utils.TABLA_CONJUROS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){

                    for (DataSnapshot ds: dataSnapshot.getChildren()) { //Nos encontramos en los ID
                        String nombre = ds.getKey();
                        String descripcion = String.valueOf(ds.getValue());
                        conjuros.add(new Texto(nombre, descripcion));
                    }

                    for (Texto conjuro: conjuros)
                        conjurosF.put(conjuro, true);

                    partida.setConjuros(conjurosF);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return conjurosF;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btVolver:
                onBackPressed();
                break;

            case R.id.btGuardar:
                guardarPartida();
                break;

            default: break;
        }
    }

    public void ponerFoto(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else{
            Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent2, AVATAR);
            cambiofoto = true;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == AVATAR) && (resultCode == RESULT_OK) && (data != null)) {
            // Obtiene el Uri de la imagen seleccionada por el usuario
            Uri imagenSeleccionada = data.getData();
            String[] ruta = {MediaStore.Images.Media.DATA};

            // Realiza una consulta a la galería de imágenes solicitando la imagen seleccionada
            Cursor cursor = getContentResolver().query(imagenSeleccionada, ruta, null, null, null);
            cursor.moveToFirst();

            // Obtiene la ruta a la imagen
            int indice = cursor.getColumnIndex(ruta[0]);
            String picturePath = cursor.getString(indice);
            cursor.close();

            // Carga la imagen en una vista ImageView que se encuentra en
            // en layout de la Activity actual
            ImageView imageView = findViewById(R.id.IVavatar);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void guardarPartida(){
        //Imagen
        ImageView IVavatar = findViewById(R.id.IVavatar);
        Bitmap imagen;
        if (!cambiofoto) {
            Toast.makeText(this, getString(R.string.errorfoto), Toast.LENGTH_SHORT).show();
            return;
        }else {
            imagen = ((BitmapDrawable) IVavatar.getDrawable()).getBitmap();
        }

        //Básico
        EditText ETnombre = findViewById(R.id.ETnombre);
        Spinner SPtipos = findViewById(R.id.SPtipos);
        HashMap<String, LinkedHashMap<Texto, Boolean>> datosVarios = adapter.itemExpandible; //La lista con todos los datos y sus checkbox

        partida.setNombre(ETnombre.getText().toString());
        partida.setTipoPartida(TipoPartida.valueOf(SPtipos.getSelectedItem().toString()));
        partida.setImagen(imagen);
        partida.setRazas(datosVarios.get(Utils.TABLA_RAZAS));
        partida.setClases(datosVarios.get(Utils.TABLA_CLASES));
        partida.setRasgos(datosVarios.get(Utils.TABLA_RASGOS));
        partida.setConjuros(datosVarios.get(Utils.TABLA_CONJUROS));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String nombreUser = prefs.getString("Usuario", "John Doe");
        Usuario usuario = new Usuario(nombreUser);
        Utils.insertarPartida(partida, usuario);
        onBackPressed();

    }

    //Infla el Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fijo, menu);
        return true;
    }

}
