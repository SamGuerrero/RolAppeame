package es.centroafuera.rolappeame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
        Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(fullSnippet)
                .setPositiveButton(R.string.ver_web, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse(fullSnippet);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }).setNegativeButton(getString(R.string.volver), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        }).create();

        return dialog;
    }
}
