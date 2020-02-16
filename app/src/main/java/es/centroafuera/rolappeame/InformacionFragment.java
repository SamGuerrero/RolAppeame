package es.centroafuera.rolappeame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class InformacionFragment extends DialogFragment {
    public static  final String ARGUMENTO_TITLE = "TITLE";
    public static  final String ARGUMENTO_FULL_SNIPETT = "FULL_SNIPETT";

    private String title;
    private  String fullSnippet;

    public InformacionFragment() {
    }

    public static InformacionFragment newInstance(String title, String fullSnippet) {
        InformacionFragment fragment = new InformacionFragment();
        Bundle b = new Bundle();
        b.putString(ARGUMENTO_TITLE, title);
        b.putString(ARGUMENTO_FULL_SNIPETT, fullSnippet);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        title = args.getString(ARGUMENTO_TITLE);
        fullSnippet = args.getString(ARGUMENTO_FULL_SNIPETT);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(fullSnippet).create();

        return dialog;
    }
}
