package es.centroafuera.rolappeame;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import es.centroafuera.rolappeame.models.Personaje;
import es.centroafuera.rolappeame.models.Texto;
import es.centroafuera.rolappeame.models.Usuario;

public class CrearPersonaje extends AppCompatActivity implements View.OnClickListener {
    int[] puntosTotales = new int[6]; //Puntos por defecto, se obtienen aleatoriamente
    boolean[] puntosUsados = new boolean[6]; //Saber si esos puntos se han usado ya o no
    int puntosActuales = 0; //Los puntos que llevas usados
    boolean cambiofoto = false;
    private final int AVATAR = 1;

    String master;
    String nombrePartida;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearpersonaje);
        final Context context = this;

        //Abrir dialogo para preguntar por master
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        /*final EditText dialogUsuario = new EditText(this);
        dialogUsuario.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogUsuario.setHint("Nombre Master");
        builder.setView(dialogUsuario);
        builder.setMessage("Introduce el usuario de tu master")
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Recoger usuario
                                //Comprobar que existe ese usuario
                                //Recoger partida
                                //Comprobar que tiene esa partida

                                ArrayList<String> usuarios = Utils.getUsuarios();
                                boolean existeUsuario = false;
                                int i = 0;
                                while ( (!existeUsuario) && (i < usuarios.size()) ){
                                    if (usuarios.get(i) == dialogUsuario.getText().toString())
                                        existeUsuario = true;
                                    else
                                        i++;
                                }

                                if (!existeUsuario){
                                    Toast.makeText(context, "No existe nadie con ese nombre de usuario", Toast.LENGTH_LONG);
                                    onBackPressed();

                                }else {
                                    master = dialogUsuario.getText().toString();
                                    //Abrir dialogo para preguntar por partida
                                    AlertDialog.Builder builderPartida = new AlertDialog.Builder(context);

                                    final EditText dialogPartida = new EditText(context);
                                    dialogPartida.setInputType(InputType.TYPE_CLASS_TEXT);
                                    dialogPartida.setHint("Nombre Partida");
                                    builderPartida.setView(dialogPartida);
                                    builderPartida.setMessage("Introduce el nombre de la partida")
                                            .setPositiveButton("Aceptar",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            //Recoger usuario
                                                            //Comprobar que existe ese usuario
                                                            //Recoger partida
                                                            //Comprobar que tiene esa partida

                                                            ArrayList<String> partidas = Utils.getPartidasUsuario(master);
                                                            boolean existePartida = false;
                                                            int i = 0;
                                                            while ( (!existePartida) && (i < partidas.size()) ){
                                                                if (partidas.get(i) == dialogPartida.getText().toString())
                                                                    existePartida = true;
                                                                else
                                                                    i++;
                                                            }

                                                            if (!existePartida){
                                                                Toast.makeText(context, "Ese usuario no tiene ninguna partida llamada así", Toast.LENGTH_LONG);
                                                                onBackPressed();
                                                            }else
                                                                nombrePartida = dialogPartida.getText().toString();

                                                        }
                                                    })
                                            .setNegativeButton("Volver",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            onBackPressed(); //Vuelve
                                                        }
                                                    });
                                    builderPartida.create().show();
                                }

                            }
                        })
                .setNegativeButton("Volver",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed(); //Vuelve
                        }
                });
        builder.create().show();*/



        //Coger y añadir ClickListener en botones
        Button BTvolver = findViewById(R.id.btVolver);
        BTvolver.setOnClickListener(this);
        Button BTcontinuar = findViewById(R.id.btGuardar);
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
        ArrayList<Texto> razasDes = (ArrayList<Texto>) Utils.getRazasFromDatabase();
        final ArrayList<String> razas = new ArrayList<>();
        for(Texto raza : razasDes)
            razas.add(raza.getTitulo());

        final Spinner Sraza = findViewById(R.id.Sraza);
        final ArrayAdapter<String> adaptadorRaza = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, razas);
        Sraza.setAdapter(adaptadorRaza);
        Sraza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                ArrayList<String> subRazas = Utils.getSubRazas(Sraza.getSelectedItem().toString());

                Spinner sSubRaza = findViewById(R.id.sSubRaza);
                ArrayAdapter<String> adaptadorSubRaza = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, subRazas);
                sSubRaza.setAdapter(adaptadorSubRaza);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        ArrayList<Texto> clasesDes = (ArrayList<Texto>) Utils.getClasesFromDatabase();
        ArrayList<String> clases = new ArrayList<>();
        for(Texto clase : clasesDes)
            clases.add(clase.getTitulo());

        Spinner Soficio = findViewById(R.id.Soficio);
        ArrayAdapter<String> adaptadorOficio = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clases);
        Soficio.setAdapter(adaptadorOficio);

        //Inicializar puntos
        TextView TVpuntos = findViewById(R.id.TVpuntos);
        String puntosTemp = "";
        for (int i = 0; i < puntosTotales.length; i++) {
            puntosTotales[i] = obtenerPuntuacion();
            puntosTemp += puntosTotales[i] + " ";
        }
        TVpuntos.setText(getString(R.string.tienes) + " " + puntosTemp + getString(R.string.puntos));

        for (int i = 0; i < puntosUsados.length; i++) {
            puntosUsados[i] = false;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btVolver:
                onBackPressed();
                break;

            case R.id.btGuardar:
                EditText nombre = findViewById(R.id.ETnombre);
                if (nombre.getText().toString().equals("")) {
                    Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
                    return;
                    
                } else if (!usadosTodosPuntos()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Has repartido mal los puntos")
                                .setPositiveButton("Volver",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
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

    }

    private boolean usadosTodosPuntos() {
        TextView tvFuerza = findViewById(R.id.puntosFuerza);
        TextView tvAgilidad = findViewById(R.id.puntosAgilidad);
        TextView tvCarisma = findViewById(R.id.puntosCarisma);
        TextView tvConstitucion = findViewById(R.id.puntosConstitucion);
        TextView tvInteligencia = findViewById(R.id.puntosInteligencia);
        TextView tvPercepcion = findViewById(R.id.puntosPercepcion);

        int fuerza = Integer.parseInt(tvFuerza.getText().toString());
        int agilidad = Integer.parseInt(tvAgilidad.getText().toString());
        int carisma = Integer.parseInt(tvCarisma.getText().toString());
        int constitucion = Integer.parseInt(tvConstitucion.getText().toString());
        int inteligencia = Integer.parseInt(tvInteligencia.getText().toString());
        int percepcion = Integer.parseInt(tvPercepcion.getText().toString());

        int[] pActuales = {fuerza, agilidad, carisma, constitucion, inteligencia, percepcion};

        int[] pUsados = puntosTotales;
        int i = 0;
        int t = 0;
        boolean fin = false;
        boolean coincidencia;
        while ((!fin) && (i < pActuales.length)){
            if (pActuales[i] == 0)
                fin = true;

            else{
                t = 0;
                coincidencia = false;
                while ((!coincidencia) && (t < pUsados.length)){
                    if (pActuales[i] == pUsados[t]){
                        coincidencia = true;
                        pUsados = borrarElemento(pUsados, pUsados[t]);
                    }else
                        t++;
                }

                if (!coincidencia)
                    fin = true;
                else
                    i++;

            }
        }

        if (fin)
            return false; //No se han usado todos los puntos
        else
            return true; //Sí que se han usado todos

    }

    private static int[] borrarElemento(int[] pUsados, int num) {
        int[] lista = new int[pUsados.length];
        for (int i = 0; i < lista.length; i++)
            lista[i] = 0;

        boolean hecho = false;
        for (int i = 0; i < pUsados.length; i++)
            if (pUsados[i] != num)
                lista[i] = pUsados[i];
            else if (hecho)
                lista[i] = pUsados[i];
            else{
                hecho = true;
            }

        return lista;
    }

    int pos = -1;
    public void sumarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        boolean mostrarPantalla = false;
        while(!mostrarPantalla) {
            pos++;
            if (pos >= 6) {
                puntos = 0;
                pos = -1;
                mostrarPantalla = true;
                puntosActuales--;

            } else {
                puntos = puntosTotales[pos];
                mostrarPantalla = true;
                puntosActuales++;
            }
        }
        pantalla.setText(String.valueOf(puntos));

    }

    public void restarPuntos(TextView pantalla){
        int puntos = Integer.parseInt(pantalla.getText().toString());
        boolean mostrarPantalla = false;
        while(!mostrarPantalla) {
            pos--;
            if (pos <= -1) {
                puntos = 0;
                pos = 6;
                mostrarPantalla = true;
                puntosActuales--;

            } else {
                puntos = puntosTotales[pos];
                mostrarPantalla = true;
                puntosActuales++;
            }
        }
        pantalla.setText(String.valueOf(puntos));
    }

    public void guardarPersonaje(){
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
        Spinner Sraza = findViewById(R.id.Sraza);
        Spinner sSubRaza = findViewById(R.id.sSubRaza);
        Spinner Soficio = findViewById(R.id.Soficio);


        //Estadisticas
        TextView TVpuntosAgilidad = findViewById(R.id.puntosAgilidad);
        TextView TVpuntoscarisma = findViewById(R.id.puntosCarisma);
        TextView TVpuntosconstitucion = findViewById(R.id.puntosConstitucion);
        TextView TVpuntosfuerza = findViewById(R.id.puntosFuerza);
        TextView TVpuntosinteligencia = findViewById(R.id.puntosInteligencia);
        TextView TVpuntospercepcion = findViewById(R.id.puntosPercepcion);

        String nombre = ETnombre.getText().toString();
        //String raza = Sraza.getSelectedItem().toString();
        //String subRaza = sSubRaza.getSelectedItem().toString();
        //String oficio = Soficio.getSelectedItem().toString();
        String raza = "Elfo";
        String subRaza = "Elfo de los Bosques";
        String oficio = "Bardo";

        int agilidad = Integer.parseInt(TVpuntosAgilidad.getText().toString());
        int carisma = Integer.parseInt(TVpuntoscarisma.getText().toString());
        int constitucion = Integer.parseInt(TVpuntosconstitucion.getText().toString());
        int fuerza = Integer.parseInt(TVpuntosfuerza.getText().toString());
        int inteligencia = Integer.parseInt(TVpuntosinteligencia.getText().toString());
        int percepcion = Integer.parseInt(TVpuntospercepcion.getText().toString());

        Personaje personaje = new Personaje(nombre, raza, subRaza, oficio, fuerza, agilidad, percepcion, constitucion, inteligencia, carisma, imagen);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String nombreUser = prefs.getString("Usuario", "John Doe");
        Usuario usuario = new Usuario(nombreUser);
        Utils.insertarPersonaje(personaje, usuario);

        onBackPressed();
    }

    @Override
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

    public void vistaInformacion(String descripcion){
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.toast_info, (ViewGroup) findViewById(R.id.lyToastInfo));

        TextView info = v.findViewById(R.id.tvInfo);
        info.setText(descripcion);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(v);
        toast.show();
    }

    public int obtenerPuntuacion(){
        //Relleno el array con 4 números aleatorios del 1 al 6
        int[] tiradas = new int[4];
        for (int i = 0; i < tiradas.length; i++)
            tiradas[i] = (int) (Math.random()*6 + 1);

        //Calculo cual es el menor
        int menor = 0;
        for (int i = 0; i < tiradas.length; i++)
            if (tiradas[i] < menor)
                menor = tiradas[i];

        //Sumo los 3 mayores
        int puntuacion = 0;
        boolean menorContado = false;
        for (int i = 0; i < tiradas.length; i++)
            if (tiradas[i] > menor)
                puntuacion += tiradas[i];
            else if (tiradas[i] == menor && menorContado)
                puntuacion += tiradas[i];
            else
                menorContado = true;

        return puntuacion;

    }

    //Infla el Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fijo, menu);
        return true;
    }

}
