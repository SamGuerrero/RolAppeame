package es.centroafuera.rolappeame.ui.acercade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import es.centroafuera.rolappeame.R;
import es.centroafuera.rolappeame.models.Texto;
import es.centroafuera.rolappeame.adapters.TextoAdapter;

public class AcercadeFragment extends Fragment {

    private AcercadeViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(AcercadeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_acercade, container, false);

        //TODO: Cambiar las condiciones legales por algo con más sentido
        Texto acercaDe = new Texto(getString(R.string.About), getString(R.string.contenido_about));
        Texto condicionesLegales = new Texto(getString(R.string.titulo_condiciones), getString(R.string.contenido_condiciones));

        ArrayList<Texto> textos = new ArrayList<>();
        textos.add(acercaDe);
        textos.add(condicionesLegales);
        ListView LVprincipal = root.findViewById(R.id.LVprincipal);
        TextoAdapter adaptador = new TextoAdapter(this,  textos);
        LVprincipal.setAdapter(adaptador);

        return root;
    }
}