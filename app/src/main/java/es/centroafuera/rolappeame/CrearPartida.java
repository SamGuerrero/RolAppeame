package es.centroafuera.rolappeame;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class CrearPartida extends AppCompatActivity implements View.OnClickListener {
    boolean cambiofoto = false;
    private final int AVATAR = 1;
    public static Brillo brillo = new Brillo();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearpartida);

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

        ImageView ivAvatar = findViewById(R.id.IVavatar);

        //TextViews
        TextView tvVida = findViewById(R.id.tvVida);
        tvVida.setOnClickListener(this);
        TextView tvAtaque = findViewById(R.id.tvAtaque);
        tvAtaque.setOnClickListener(this);
        TextView tvDefensa = findViewById(R.id.tvDefensa);
        tvDefensa.setOnClickListener(this);

        TextView BTinfoEnemigos = findViewById(R.id.tvEnemigo);
        BTinfoEnemigos.setOnClickListener(this);

        //Relleno Spinner
        ArrayList<TipoPartida> tipos = new ArrayList<>();
        TipoPartida[] tiposArray = TipoPartida.values();
        tipos.addAll(Arrays.asList(tiposArray));
        Spinner SPtipos = findViewById(R.id.SPtipos);
        ArrayAdapter<TipoPartida> adaptadorTipos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tipos);
        SPtipos.setAdapter(adaptadorTipos);


        Button volver = findViewById(R.id.BTvolver);
        volver.setOnClickListener(this);
        Button continuar = findViewById(R.id.BTcontinuar);
        continuar.setOnClickListener(this);

        //Brillo Pantalla
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = brillo.getBrillo();
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.BTvolver:
                onBackPressed();
                break;

            case R.id.BTcontinuar:
                guardarPartida();
                break;

            //Muestra información sobre los atributos
            case R.id.tvVida:
                vistaInformacion(getString(R.string.vida_info));
                break;

            case R.id.tvAtaque:
                vistaInformacion(getString(R.string.ataque_info));
                break;

            case R.id.tvDefensa:
                vistaInformacion(getString(R.string.defensa_info));
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
                vistaInformacion(getString(R.string.enemigo_info_crear));

            default: break;
        }
    }

    public void sumarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        puntos++;
        pantalla.setText(String.valueOf(puntos));
    }

    public void restarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        puntos--;
        pantalla.setText(String.valueOf(puntos));
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

    public void ponerFoto(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else{
            Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent2, AVATAR);
            cambiofoto = true;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if ((requestCode == AVATAR) && (resultCode == RESULT_OK) && (data != null)) {
            // Obtiene el Uri de la imagen seleccionada por el usuario
            Uri imagenSeleccionada = data.getData();
            String[] ruta = {MediaStore.Images.Media.DATA };

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


        //Estadisticas
        TextView TvminVida = findViewById(R.id.minVida);
        TextView TVmaxVida = findViewById(R.id.maxVida);
        TextView TVminAtaque = findViewById(R.id.minAtaque);
        TextView TVmaxAtaque = findViewById(R.id.maxAtaque);
        TextView TVminDefensa = findViewById(R.id.minDefensa);
        TextView TVmaxDefensa = findViewById(R.id.maxDefensa);

        String nombre = ETnombre.getText().toString();
        TipoPartida tipoPartida = TipoPartida.valueOf(SPtipos.getSelectedItem().toString());

        int minVida = Integer.parseInt(TvminVida.getText().toString());
        int maxVida = Integer.parseInt(TVmaxVida.getText().toString());
        int minAtaque = Integer.parseInt(TVminAtaque.getText().toString());
        int maxAtaque = Integer.parseInt(TVmaxAtaque.getText().toString());
        int minDefensa = Integer.parseInt(TVminDefensa.getText().toString());
        int maxDefensa = Integer.parseInt(TVmaxDefensa.getText().toString());

        Partida partida = new Partida(nombre, imagen, tipoPartida, minVida, maxVida, minAtaque, maxAtaque, minDefensa, maxDefensa);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Partida"); //Referencia a la clase Java
        myRef.child("partidas").child(String.valueOf(partida.getId())).child("nombre").setValue(partida.getNombre()); //El .push() es para crear un id único, lo pondría antes del segundo child
        myRef.child("partidas").child(String.valueOf(partida.getId())).child("imagen").setValue(BitMapToString(partida.getImagen()));
        myRef.child("partidas").child(String.valueOf(partida.getId())).child("tipoPartida").setValue(partida.getTipoPartida());

        myRef.child("partidas").child(String.valueOf(partida.getId())).child("minVida").setValue(partida.getMinVida());
        myRef.child("partidas").child(String.valueOf(partida.getId())).child("maxVida").setValue(partida.getMaxVida());

        myRef.child("partidas").child(String.valueOf(partida.getId())).child("minAtaque").setValue(partida.getMinAtaque());
        myRef.child("partidas").child(String.valueOf(partida.getId())).child("maxAtaque").setValue(partida.getMaxAtaque());

        myRef.child("partidas").child(String.valueOf(partida.getId())).child("minDefensa").setValue(partida.getMinDefensa());
        myRef.child("partidas").child(String.valueOf(partida.getId())).child("maxDefensa").setValue(partida.getMaxDefensa());

        onBackPressed();
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream ByteStream=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, ByteStream);
        byte [] b = ByteStream.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    //Infla el Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fijo, menu);
        return true;
    }

    @Override //Dentro del Action Bar
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, Configuracion.class);
        startActivity(intent);
        return true;
    }

}
