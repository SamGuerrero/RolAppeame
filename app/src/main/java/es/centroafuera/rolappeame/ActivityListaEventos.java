package es.centroafuera.rolappeame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

import es.centroafuera.rolappeame.adapters.EventoAdapter;
import es.centroafuera.rolappeame.models.Evento;

public class ActivityListaEventos extends AppCompatActivity implements View.OnClickListener{
    EventoAdapter adapter;
    ArrayList<Evento> eventos;
    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarlista);
        setTitle("Eventos");

        listView = findViewById(R.id.listView);
        FloatingActionButton fab = findViewById(R.id.fab);

        getEventosFromDatabase();
        adapter = new EventoAdapter(this, eventos);
        listView.setAdapter(adapter);

        fab.setOnClickListener(this);
        registerForContextMenu(listView);
    }

    private void getEventosFromDatabase() {
        eventos = new ArrayList<>();

        //TODO:Rellenar desde la base de datos
        eventos.add(new Evento("Evento de prueba", "Primera partida de rol", new Date()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:

                //Abre un calendario
                MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Nuevo Evento");
                final MaterialDatePicker materialDatePicker = builder.build();
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Date fecha = new Date((long) materialDatePicker.getSelection());
                        Evento evento = new Evento(fecha);
                        eventos.add(evento);
                        adapter.notifyDataSetChanged();

                        //Nueva activity que permita rellenar y guardar un evento
                    }
                });
                break;

            default: break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        this.getMenuInflater().inflate(R.menu.sitios, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int pos = menuInfo.position;

        switch (item.getItemId()){
            case R.id.imNombre:
                String nombre = eventos.get(pos).getNombre();

                AlertDialog.Builder builderNombre = new AlertDialog.Builder(this);
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(nombre);
                builderNombre.setView(input);
                builderNombre.setMessage("Nombre Evento")
                        .setPositiveButton("Guardar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        eventos.get(pos).setNombre(input.getText().toString());
                                        adapter.notifyDataSetChanged();

                                    }
                                })
                        .setNegativeButton("Volver",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Nada
                                    }
                                });
                builderNombre.create().show();
                break;

            case R.id.imDescripcion:
                final String descripcion = eventos.get(pos).getDescripcion();

                AlertDialog.Builder builderDes = new AlertDialog.Builder(this);
                final EditText input2 = new EditText(this);
                input2.setInputType(InputType.TYPE_CLASS_TEXT);
                input2.setText(descripcion);
                builderDes.setView(input2);
                builderDes.setMessage("Descripción Evento")
                        .setPositiveButton("Guardar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        eventos.get(pos).setDescripcion(input2.getText().toString());
                                        adapter.notifyDataSetChanged();

                                    }
                                })
                        .setNegativeButton("Volver",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Nada
                                    }
                                });
                builderDes.create().show();

                break;

            default: break;
        }

        return true;
    }
}
