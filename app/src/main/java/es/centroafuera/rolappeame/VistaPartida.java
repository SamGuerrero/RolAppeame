package es.centroafuera.rolappeame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class VistaPartida extends AppCompatActivity implements View.OnClickListener {
    Partida partida;
    String id;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistapartida);

        //Recojo el id String que es la key de la base de datos
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        //Cojo el personaje de la base de datos
        DatabaseReference myRef = database.getReference();

        // Read from the database
        myRef.child("Partida").child("partidas").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (ds.exists()) {
                    String nombre = ds.child("nombre").getValue().toString();

                    int minVida,maxVida, minAtaque, maxAtaque ,minDefensa, maxDefensa;

                    Bitmap imagen = StringToBitMap(ds.child("imagen").getValue().toString());
                    TipoPartida tipoPartida = TipoPartida.valueOf(ds.child("tipoPartida").getValue().toString());

                    minVida = Integer.parseInt(ds.child("minVida").getValue().toString());
                    maxVida = Integer.parseInt(ds.child("maxVida").getValue().toString());
                    minAtaque = Integer.parseInt(ds.child("minAtaque").getValue().toString());
                    maxAtaque = Integer.parseInt(ds.child("maxAtaque").getValue().toString());
                    minDefensa = Integer.parseInt(ds.child("minDefensa").getValue().toString());
                    maxDefensa = Integer.parseInt(ds.child("maxDefensa").getValue().toString());

                    partida = new Partida(nombre, imagen, tipoPartida, minVida, maxVida, minAtaque, maxAtaque, minDefensa, maxDefensa);
                    partida.setIdT(ds.getKey());

                    //Muestro los datos por pantalla
                    ImageView ivAvatar = findViewById(R.id.IVavatar);
                    ivAvatar.setImageBitmap(partida.getImagen());

                    TextView tvNombre = findViewById(R.id.TVnombre);
                    tvNombre.setText(partida.getNombre());
                    TextView tvTipoPartida = findViewById(R.id.TVtipoPartida);
                    tvTipoPartida.setText(partida.getTipoPartida().toString());

                    String vidaActual = "Vida: " + partida.sacarVida();
                    String ataqueActual = "Ataque: " + partida.sacarAtaque();
                    String defensaActual = "Defensa: " + partida.sacarDefensa();
                    TextView tvVida = findViewById(R.id.tvVida);
                    tvVida.setText(vidaActual);
                    TextView tvAtaque = findViewById(R.id.tvAtaque);
                    tvAtaque.setText(ataqueActual);
                    TextView tvDefensa = findViewById(R.id.tvDefensa);
                    tvDefensa.setText(defensaActual);

                    TextView tvminVida = findViewById(R.id.minVida);
                    tvminVida.setText(String.valueOf(partida.getMinVida()));
                    TextView tvmaxVida = findViewById(R.id.maxVida);
                    tvmaxVida.setText(String.valueOf(partida.getMaxVida()));
                    TextView tvminAtaque = findViewById(R.id.minAtaque);
                    tvminAtaque.setText(String.valueOf(partida.getMinAtaque()));
                    TextView tvmaxAtaque = findViewById(R.id.maxAtaque);
                    tvmaxAtaque.setText(String.valueOf(partida.getMaxAtaque()));
                    TextView tvminDefensa = findViewById(R.id.minDefensa);
                    tvminDefensa.setText(String.valueOf(partida.getMinDefensa()));
                    TextView tvmaxDefensa = findViewById(R.id.maxDefensa);
                    tvmaxDefensa.setText(String.valueOf(partida.getMaxDefensa()));



                } else {
                    Toast.makeText(VistaPartida.this, "Algo salió mal", Toast.LENGTH_LONG);
                    partida = new Partida();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //Botones
        Button btMenosMinVida = findViewById(R.id.btMenosMinVida);
        btMenosMinVida.setOnClickListener(this);
        Button btMasMinVida = findViewById(R.id.btMasMinVida);
        btMasMinVida.setOnClickListener(this);
        Button btMenosMaxVida = findViewById(R.id.btMenosMaxVida);
        btMenosMaxVida.setOnClickListener(this);
        Button btMasMaxVida = findViewById(R.id.btMasMaxVida);
        btMasMaxVida.setOnClickListener(this);
        Button btMenosMinAtaque = findViewById(R.id.btMenosMinAtaque);
        btMenosMinAtaque.setOnClickListener(this);
        Button btMasMinAtaque = findViewById(R.id.btMasMinAtaque);
        btMasMinAtaque.setOnClickListener(this);
        Button btMenosMaxAtaque = findViewById(R.id.btMenosMaxAtaque);
        btMenosMaxAtaque.setOnClickListener(this);
        Button btMasMaxAtaque = findViewById(R.id.btMasMaxAtaque);
        btMasMaxAtaque.setOnClickListener(this);
        Button btMenosMinDefensa = findViewById(R.id.btMenosMinDefensa);
        btMenosMinDefensa.setOnClickListener(this);
        Button btMasMinDefensa = findViewById(R.id.btMasMinDefensa);
        btMasMinDefensa.setOnClickListener(this);
        Button btMenosMaxDefensa = findViewById(R.id.btMenosMaxDefensa);
        btMenosMaxDefensa.setOnClickListener(this);
        Button btMasMaxDefensa = findViewById(R.id.btMasMaxDefensa);
        btMasMaxDefensa.setOnClickListener(this);

        //TextViews
        TextView tvVida = findViewById(R.id.tvVida);
        tvVida.setOnClickListener(this);
        TextView tvAtaque = findViewById(R.id.tvAtaque);
        tvAtaque.setOnClickListener(this);
        TextView tvDefensa = findViewById(R.id.tvDefensa);
        tvDefensa.setOnClickListener(this);

        TextView BTinfoEnemigos = findViewById(R.id.tvEnemigo);
        BTinfoEnemigos.setOnClickListener(this);
        
        Button volver = findViewById(R.id.BTvolver);
        volver.setOnClickListener(this);
        Button generar = findViewById(R.id.BTgenerar);
        generar.setOnClickListener(this);
        Button continuar = findViewById(R.id.BTcontinuar);
        continuar.setOnClickListener(this);
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.BTvolver:
                onBackPressed();
                break;

            case R.id.BTcontinuar:
                guardarPartida();
                onBackPressed();
                break;

            case R.id.BTgenerar:
                String vidaActual = "Vida: " + partida.sacarVida();
                String ataqueActual = "Ataque: " + partida.sacarAtaque();
                String defensaActual = "Defensa: " + partida.sacarDefensa();
                TextView tvVida = findViewById(R.id.tvVida);
                tvVida.setText(vidaActual);
                TextView tvAtaque = findViewById(R.id.tvAtaque);
                tvAtaque.setText(ataqueActual);
                TextView tvDefensa = findViewById(R.id.tvDefensa);
                tvDefensa.setText(defensaActual);

                break;

            //Muestra información sobre los atributos
            case R.id.tvVida:
                vistaInformacion("Esto indicará la vida que tendrá tu enemigo");
                break;

            case R.id.tvAtaque:
                vistaInformacion("Esto es el daño que puede causar a tus personajes");
                break;

            case R.id.tvDefensa:
                vistaInformacion("Esta es la defensa que tendrá tu enemigo cuando los personajes le ataquen");
                break;

            //Cambia los puntos
            case R.id.btMenosMinVida:
                TextView minVida = findViewById(R.id.minVida);
                restarPuntos(minVida);

                break;
            case R.id.btMasMinVida:
                TextView minVida2 = findViewById(R.id.minVida);
                sumarPuntos(minVida2);

                break;
            case R.id.btMenosMaxVida:
                TextView maxVida = findViewById(R.id.maxVida);
                restarPuntos(maxVida);

                break;
            case R.id.btMasMaxVida:
                TextView maxVida2 = findViewById(R.id.maxVida);
                sumarPuntos(maxVida2);

                break;

            case R.id.btMenosMinAtaque:
                TextView minAtaque = findViewById(R.id.minAtaque);
                restarPuntos(minAtaque);

                break;
            case R.id.btMasMinAtaque:
                TextView minAtaque2 = findViewById(R.id.minAtaque);
                sumarPuntos(minAtaque2);

                break;
            case R.id.btMenosMaxAtaque:
                TextView maxAtaque = findViewById(R.id.maxAtaque);
                restarPuntos(maxAtaque);

                break;
            case R.id.btMasMaxAtaque:
                TextView maxAtaque2 = findViewById(R.id.maxAtaque);
                sumarPuntos(maxAtaque2);

                break;

            case R.id.btMenosMinDefensa:
                TextView minDefensa = findViewById(R.id.minDefensa);
                restarPuntos(minDefensa);

                break;
            case R.id.btMasMinDefensa:
                TextView minDefensa2 = findViewById(R.id.minDefensa);
                sumarPuntos(minDefensa2);

                break;
            case R.id.btMenosMaxDefensa:
                TextView maxDefensa = findViewById(R.id.maxDefensa);
                restarPuntos(maxDefensa);

                break;
            case R.id.btMasMaxDefensa:
                TextView maxDefensa2 = findViewById(R.id.maxDefensa);
                sumarPuntos(maxDefensa2);

                break;

            case R.id.tvEnemigo:
                vistaInformacion("Pulsa el botón Generar, para generar un enemigo aleatorio");

            default: break;
        }
    }

    public void sumarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        puntos++;
        pantalla.setText(String.valueOf(puntos));

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
    }

    public void restarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        puntos--;
        pantalla.setText(String.valueOf(puntos));

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
        personajeT.put("nombre", partida.getNombre());
        personajeT.put("tipoPartida", partida.getTipoPartida());
        personajeT.put("minVida", partida.getMinVida());
        personajeT.put("maxVida", partida.getMaxVida());
        personajeT.put("minAtaque", partida.getMinAtaque());
        personajeT.put("maxAtaque", partida.getMaxAtaque());
        personajeT.put("minDefensa", partida.getMinDefensa());
        personajeT.put("maxDefensa", partida.getMaxDefensa());
        personajeT.put("imagen", BitMapToString(partida.getImagen()));


        //Personaje > personajes > id > Todos los datos del personaje
        DatabaseReference myRef = database.getReference("Partida");
        myRef.child("partidas").child(partida.getIdT()).updateChildren(personajeT).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(VistaPartida.this, "El personaje se ha guardado correctamente", Toast.LENGTH_LONG);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VistaPartida.this, "El personaje NO se ha guardado", Toast.LENGTH_LONG);
            }
        });

    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream ByteStream=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, ByteStream);
        byte [] b = ByteStream.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


}
