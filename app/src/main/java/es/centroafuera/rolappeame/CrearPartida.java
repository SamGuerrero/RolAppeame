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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class CrearPartida extends AppCompatActivity implements View.OnClickListener {
    boolean cambiofoto = false;
    private final int AVATAR = 1;

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

        ImageView ivAvatar;

        ivAvatar = findViewById(R.id.IVavatar);
        //TextViews
        TextView tvVida = findViewById(R.id.tvVida);
        tvVida.setOnClickListener(this);
        TextView tvAtaque = findViewById(R.id.tvAtaque);
        tvAtaque.setOnClickListener(this);
        TextView tvDefensa = findViewById(R.id.tvDefensa);
        tvDefensa.setOnClickListener(this);


        Button volver = findViewById(R.id.BTvolver);
        volver.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.BTvolver:
                onBackPressed();
                break;

            case R.id.BTcontinuar:
                onBackPressed();
                //guardarPartida();
                break;

            //Muestra información sobre los atributos
            case R.id.tvVida:
                vistaInformacion(getString(R.string.desAgilidad));
                break;

            case R.id.tvAtaque:
                vistaInformacion(getString(R.string.desCarisma));
                break;

            case R.id.tvDefensa:
                vistaInformacion(getString(R.string.desConstitucion));
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



    /*public void guardarPartida(){
        //TODO: Hacer con firebase
        //Imagen
        ImageView IVavatar = findViewById(R.id.IVavatar);
        Bitmap imagen;
        if (cambiofoto == false) {
            Toast.makeText(this, getString(R.string.errorfoto), Toast.LENGTH_SHORT).show();
            return;
        }else {
            imagen = ((BitmapDrawable) IVavatar.getDrawable()).getBitmap();
        }

        //Básico
        EditText ETnombre = findViewById(R.id.ETnombre);
        Spinner Sraza = findViewById(R.id.Sraza);
        Spinner Soficio = findViewById(R.id.Soficio);


        //Estadisticas
        TextView TVpuntosAgilidad = findViewById(R.id.puntosAgilidad);
        TextView TVpuntoscarisma = findViewById(R.id.puntosCarisma);
        TextView TVpuntosconstitucion = findViewById(R.id.puntosConstitucion);
        TextView TVpuntosfuerza = findViewById(R.id.puntosFuerza);
        TextView TVpuntosinteligencia = findViewById(R.id.puntosInteligencia);
        TextView TVpuntospercepcion = findViewById(R.id.puntosPercepcion);

        String nombre = ETnombre.getText().toString();
        Raza raza = Raza.valueOf(Sraza.getSelectedItem().toString());
        Oficio oficio = Oficio.valueOf(Soficio.getSelectedItem().toString());

        int agilidad = Integer.parseInt(TVpuntosAgilidad.getText().toString());
        int carisma = Integer.parseInt(TVpuntoscarisma.getText().toString());
        int constitucion = Integer.parseInt(TVpuntosconstitucion.getText().toString());
        int fuerza = Integer.parseInt(TVpuntosfuerza.getText().toString());
        int inteligencia = Integer.parseInt(TVpuntosinteligencia.getText().toString());
        int percepcion = Integer.parseInt(TVpuntospercepcion.getText().toString());

        Personaje personaje = new Personaje(nombre, raza, oficio, fuerza, agilidad, percepcion, constitucion, inteligencia, carisma, imagen);

        BaseDeDatos bd = new BaseDeDatos(this);
        bd.nuevoPersonaje(personaje);
        onBackPressed();
    }*/
}
