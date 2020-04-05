package es.centroafuera.rolappeame.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import es.centroafuera.rolappeame.MenuLateralActivity;
import es.centroafuera.rolappeame.R;
import es.centroafuera.rolappeame.Texto;
import es.centroafuera.rolappeame.TextoAdapter;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

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