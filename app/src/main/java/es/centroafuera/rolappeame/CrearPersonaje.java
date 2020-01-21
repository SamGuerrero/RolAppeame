package es.centroafuera.rolappeame;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class CrearPersonaje extends AppCompatActivity implements View.OnClickListener {
    //TODO: set puntos obligatorios, que no se puedan reducir según las combinaciones
    int puntosTotales = 12; //El máximo de puntos a repartir cuando eliges X clase y X raza
    int puntosActuales = 0; //Los puntos que llevas acumulados
    boolean cambiofoto = false;
    private final int AVATAR = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearpersonaje);

        //Coger y añadir ClickListener en botones
        Button BTvolver = findViewById(R.id.BTvolver);
        BTvolver.setOnClickListener(this);
        Button BTcontinuar = findViewById(R.id.BTcontinuar);
        BTcontinuar.setOnClickListener(this);
        ImageView IVavatar = findViewById(R.id.IVavatar);
        IVavatar.setOnClickListener(this);

        TextView BTinfoAgilidad = findViewById(R.id.agilidad);
        BTinfoAgilidad.setOnClickListener(this);
        TextView BTinfoCarisma = findViewById(R.id.carisma);
        BTinfoCarisma.setOnClickListener(this);
        TextView BTinfoConstitucion = findViewById(R.id.constitucion);
        BTinfoConstitucion.setOnClickListener(this);
        TextView BTinfoFuerza = findViewById(R.id.fuerza);
        BTinfoFuerza.setOnClickListener(this);
        TextView BTinfoInteligencia = findViewById(R.id.inteligencia);
        BTinfoInteligencia.setOnClickListener(this);
        TextView BTinfoPercepcion = findViewById(R.id.percepcion);
        BTinfoPercepcion.setOnClickListener(this);

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

        //Rellenar Spinners
        ArrayList<Raza> razas = new ArrayList<>();
        Raza[] razasArray = Raza.values();
        for(int i = 0; i < razasArray.length; i++)
            razas.add(razasArray[i]);

        Spinner Sraza = findViewById(R.id.Sraza);
        ArrayAdapter<Raza> adaptadorRaza = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, razas);
        Sraza.setAdapter(adaptadorRaza);

        ArrayList<Oficio> oficios = new ArrayList<>();
        Oficio[] oArray = Oficio.values();
        for(int i = 0; i < oArray.length; i++)
            oficios.add(oArray[i]);

        Spinner Soficio = findViewById(R.id.Soficio);
        ArrayAdapter<Oficio> adaptadorOficio = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, oficios);
        Soficio.setAdapter(adaptadorOficio);

        //Inicializar mensaje de los puntos
        TextView TVpuntos = findViewById(R.id.TVpuntos);
        TVpuntos.setText(getString(R.string.tienes) + " " + (puntosTotales-puntosActuales) + " " + getString(R.string.puntos));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BTvolver:
                onBackPressed();
                break;

            case R.id.BTcontinuar:
                EditText nombre = findViewById(R.id.ETnombre);
                if (nombre.getText().toString().equals("")) {
                    Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    if (puntosTotales > puntosActuales) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(getString(R.string.quedanpuntos))
                                .setPositiveButton(getString(R.string.si),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                guardarPersonaje();
                                            }
                                        })
                                .setNegativeButton(getString(R.string.no),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // En este caso se cierra directamente el diálogo y no se hace nada más
                                                dialog.dismiss();
                                            }
                                        });
                        builder.create().show();
                    }else
                        guardarPersonaje();

                break;

            case R.id.IVavatar:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }else{
                    Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent2, AVATAR);
                    cambiofoto = true;
                }

                break;

            //Muestra información sobre los atributos
            case R.id.agilidad:
                vistaInformacion(getString(R.string.desAgilidad));
                break;

            case R.id.carisma:
                vistaInformacion(getString(R.string.desCarisma));
                break;

            case R.id.constitucion:
                vistaInformacion(getString(R.string.desConstitucion));
                break;

            case R.id.fuerza:
                vistaInformacion(getString(R.string.desFuerza));
                break;

            case R.id.inteligencia:
                vistaInformacion(getString(R.string.desInteligencia));
                break;

            case R.id.percepcion:
                vistaInformacion(getString(R.string.desPercepcion));
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

        //Se actualiza el mensaje de puntos
        TextView TVpuntos = findViewById(R.id.TVpuntos);
        TVpuntos.setText(getString(R.string.tienes) + " " + (puntosTotales-puntosActuales) + " " + getString(R.string.puntos));

    }

    public void sumarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        if (puntosTotales > puntosActuales) {
            puntos++;
            puntosActuales++;
            pantalla.setText(String.valueOf(puntos));
        }
    }

    public void restarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        if (puntos > 0) {
            puntos--;
            puntosActuales--;
            pantalla.setText(String.valueOf(puntos));
        }
    }

    public void guardarPersonaje(){
        //TODO: poner foto por defecto
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
    }

    @Override
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

    public void vistaInformacion(String descripcion){
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.toast_info, (ViewGroup) findViewById(R.id.lyToastInfo));

        TextView info = v.findViewById(R.id.tvInfo);
        info.setText(descripcion);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(v);
        toast.show();
    }
}
