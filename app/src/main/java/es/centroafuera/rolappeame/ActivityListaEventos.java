package es.centroafuera.rolappeame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

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
        listView.setAdapter(adapter);

        fab.setOnClickListener(this);
    }

    private void getEventosFromDatabase() {
        eventos = new ArrayList<>();

        //TODO:Rellenar desde la base de datos

        adapter = new EventoAdapter(this, eventos);
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
                    }
                });
                break;

            default: break;
        }
    }
}
