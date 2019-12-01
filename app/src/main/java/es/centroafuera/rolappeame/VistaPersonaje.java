package es.centroafuera.rolappeame;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class VistaPersonaje extends AppCompatActivity implements View.OnClickListener{
    Personaje personaje;
    BaseDeDatos bd;
    private final int AVATAR = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistapersonaje);

        //Recojo los datos del Intent para tener al personaje
        Intent intent = getIntent();
        long id = intent.getLongExtra("ID", 0);
        bd = new BaseDeDatos(this);
        personaje = bd.getPersonajeId(id);

        //Muestro los datos por pantalla
        ImageView ivAvatar = findViewById(R.id.IVavatar);
        ivAvatar.setImageBitmap(personaje.getImagen());
        TextView tvNombre = findViewById(R.id.TVnombre);
        tvNombre.setText(personaje.getNombre());
        TextView tvRaza = findViewById(R.id.TVraza);
        tvRaza.setText(personaje.getRaza().toString());
        TextView tvOficio = findViewById(R.id.TVoficio);
        tvOficio.setText(personaje.getOficio().toString());

        TextView tvFuerza = findViewById(R.id.puntosFuerza);
        tvFuerza.setText(personaje.getFuerza());
        TextView tvAgilidad = findViewById(R.id.puntosAgilidad);
        tvAgilidad.setText(personaje.getAgilidad());
        TextView tvPercepcion = findViewById(R.id.puntosPercepcion);
        tvPercepcion.setText(personaje.getPercepcion());
        TextView tvConstitucion = findViewById(R.id.puntosConstitucion);
        tvConstitucion.setText(personaje.getConstitucion());
        TextView tvInteligencia = findViewById(R.id.puntosInteligencia);
        tvInteligencia.setText(personaje.getInteligencia());
        TextView tvCarisma = findViewById(R.id.puntosCarisma);
        tvCarisma.setText(personaje.getCarisma());

        //Añado listeners a los botones
        //Coger y añadir ClickListener en botones
        Button BTvolver = findViewById(R.id.BTvolver);
        BTvolver.setOnClickListener(this);
        Button BTcontinuar = findViewById(R.id.BTcontinuar);
        BTcontinuar.setOnClickListener(this);
        ImageView IVavatar = findViewById(R.id.IVavatar);
        IVavatar.setOnClickListener(this);

        Button BTmasAgilidad = findViewById(R.id.BTmasAgilidad);
        BTmasAgilidad.setOnClickListener(this);
        Button BTmenosAgilidad = findViewById(R.id.BTmenosAgilidad);
        BTmenosAgilidad.setOnClickListener(this);
        Button BTmasCarisma = findViewById(R.id.BTmasCarisma);
        BTmasCarisma.setOnClickListener(this);
        Button BTmenosCarisma = findViewById(R.id.BTmenosCarisma);
        BTmenosCarisma.setOnClickListener(this);
        Button BTmasConstitucion = findViewById(R.id.BTmasConstitucion);
        BTmasConstitucion.setOnClickListener(this);
        Button BTmenosConstitucion = findViewById(R.id.BTmenosConstitucion);
        BTmenosConstitucion.setOnClickListener(this);
        Button BTmasFuerza = findViewById(R.id.BTmasFuerza);
        BTmasFuerza.setOnClickListener(this);
        Button BTmenosFuerza = findViewById(R.id.BTmenosFuerza);
        BTmenosFuerza.setOnClickListener(this);
        Button BTmasInteligencia = findViewById(R.id.BTmasInteligencia);
        BTmasInteligencia.setOnClickListener(this);
        Button BTmenosInteligencia = findViewById(R.id.BTmenosInteligencia);
        BTmenosInteligencia.setOnClickListener(this);
        Button BTmasPercepcion = findViewById(R.id.BTmasPercepcion);
        BTmasPercepcion.setOnClickListener(this);
        Button BTmenosPercepcion = findViewById(R.id.BTmenosPercepcion);
        BTmenosPercepcion.setOnClickListener(this);

        TextView tvComentario = findViewById(R.id.TVpuntos);
        tvComentario.setText("Buena suerte en la partida");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.BTvolver:
                onBackPressed();
                break;

            case R.id.BTguardar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Si guardas se modificarán los datos que hayas cambiado\n¿Seguro que quieres continuar?")
                            .setPositiveButton("Sí",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            guardarPersonaje();
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // En este caso se cierra directamente el diálogo y no se hace nada más
                                            dialog.dismiss();
                                        }
                                    });
                    builder.create().show();

            case R.id.IVavatar:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }else{
                    Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent2, AVATAR);
                }

                break;

            //Cambia los puntos
            case R.id.BTmasAgilidad:
                TextView puntosAgilidad = findViewById(R.id.puntosAgilidad);
                sumarPuntos(puntosAgilidad);

                break;
            case R.id.BTmenosAgilidad:
                TextView puntosagilidad = findViewById(R.id.puntosAgilidad);
                restarPuntos(puntosagilidad);

                break;
            case R.id.BTmasCarisma:
                TextView puntosCarisma = findViewById(R.id.puntosCarisma);
                sumarPuntos(puntosCarisma);

                break;
            case R.id.BTmenosCarisma:
                TextView puntoscarisma = findViewById(R.id.puntosCarisma);
                restarPuntos(puntoscarisma);

                break;
            case R.id.BTmasConstitucion:
                TextView puntosConstitucion = findViewById(R.id.puntosConstitucion);
                sumarPuntos(puntosConstitucion);

                break;
            case R.id.BTmenosConstitucion:
                TextView puntosconstitucion = findViewById(R.id.puntosConstitucion);
                restarPuntos(puntosconstitucion);

                break;
            case R.id.BTmasFuerza:
                TextView puntosFuerza = findViewById(R.id.puntosFuerza);
                sumarPuntos(puntosFuerza);

                break;
            case R.id.BTmenosFuerza:
                TextView puntosfuerza = findViewById(R.id.puntosFuerza);
                restarPuntos(puntosfuerza);

                break;
            case R.id.BTmasInteligencia:
                TextView puntosInteligencia = findViewById(R.id.puntosInteligencia);
                sumarPuntos(puntosInteligencia);

                break;
            case R.id.BTmenosInteligencia:
                TextView puntosinteligencia = findViewById(R.id.puntosInteligencia);
                restarPuntos(puntosinteligencia);

                break;
            case R.id.BTmasPercepcion:
                TextView puntosPercepcion = findViewById(R.id.puntosPercepcion);
                sumarPuntos(puntosPercepcion);

                break;
            case R.id.BTmenosPercepcion:
                TextView puntospercepcion = findViewById(R.id.puntosPercepcion);
                restarPuntos(puntospercepcion);

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

    public void guardarPersonaje(){
        //Imagen
        ImageView IVavatar = findViewById(R.id.IVavatar);
        personaje.setImagen( ((BitmapDrawable) IVavatar.getDrawable()).getBitmap() );

        //Estadisticas
        TextView TVpuntosAgilidad = findViewById(R.id.puntosAgilidad);
        TextView TVpuntoscarisma = findViewById(R.id.puntosCarisma);
        TextView TVpuntosconstitucion = findViewById(R.id.puntosConstitucion);
        TextView TVpuntosfuerza = findViewById(R.id.puntosFuerza);
        TextView TVpuntosinteligencia = findViewById(R.id.puntosInteligencia);
        TextView TVpuntospercepcion = findViewById(R.id.puntosPercepcion);

        personaje.setAgilidad( Integer.parseInt(TVpuntosAgilidad.getText().toString()) );
        personaje.setCarisma( Integer.parseInt(TVpuntoscarisma.getText().toString()));
        personaje.setConstitucion( Integer.parseInt(TVpuntosconstitucion.getText().toString()));
        personaje.setFuerza( Integer.parseInt(TVpuntosfuerza.getText().toString()));
        personaje.setInteligencia( Integer.parseInt(TVpuntosinteligencia.getText().toString()));
        personaje.setPercepcion( Integer.parseInt(TVpuntospercepcion.getText().toString()));

        bd.actualizaPersonaje(personaje);
    }
}
