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
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CrearPartida extends AppCompatActivity implements View.OnClickListener {
    boolean cambiofoto = false;
    private final int AVATAR = 1;
    List<String> grupoExpandible;
    HashMap<String, LinkedHashMap<String, Boolean>> itemExpandible;
    PartidaAdapterExpandible adapter;
    Partida partida;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearpartida);

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
        Button continuar = findViewById(R.id.btContinuar);
        continuar.setOnClickListener(this);

    }

    private void rellenarLista() {
        grupoExpandible.add(Utils.TABLA_RAZAS);
        grupoExpandible.add(Utils.TABLA_CLASES);

        partida = Utils.getPartidaDefecto();
        LinkedHashMap<String, Boolean> listaRazas = partida.getRazas();
        LinkedHashMap<String, Boolean> listaClases = partida.getClases();

        //Para probar, porque no tengo buena conexión y no me muestra las razas
        /*LinkedHashMap<String, Boolean> listaRazas = new LinkedHashMap<>();
        listaRazas.put("Orco", true);
        listaRazas.put("Elfo", true);

        LinkedHashMap<String, Boolean> listaClases = new LinkedHashMap<>();
        listaClases.put("Guerrero", true);
        listaClases.put("Mago", true);*/

        itemExpandible.put(grupoExpandible.get(0), listaRazas);
        itemExpandible.put(grupoExpandible.get(1), listaClases);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btVolver:
                onBackPressed();
                break;

            case R.id.btContinuar:
                guardarPartida();
                break;

            default: break;
        }
    }

    //TODO: Mirar si utilizo esta función para algo
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
        HashMap<String, LinkedHashMap<String, Boolean>> datosVarios = adapter.itemExpandible;

        partida.setNombre(ETnombre.getText().toString());
        partida.setTipoPartida(TipoPartida.valueOf(SPtipos.getSelectedItem().toString()));
        partida.setImagen(imagen);
        partida.setRazas(datosVarios.get(Utils.TABLA_RAZAS));
        partida.setClases(datosVarios.get(Utils.TABLA_CLASES));

        Utils.insertarPartida(partida);
        onBackPressed();

    }

    //Infla el Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fijo, menu);
        return true;
    }

}
