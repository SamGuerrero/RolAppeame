package es.centroafuera.rolappeame.ui.cerrarsesion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import es.centroafuera.rolappeame.LogInActivity;
import es.centroafuera.rolappeame.R;

public class CerrarsesionFragment extends Fragment {

    private CerrarsesionViewModel shareViewModel;
    private FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel = ViewModelProviders.of(this).get(CerrarsesionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cerrarsesion, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        cerrarSesion();

        return root;
    }

    private void cerrarSesion() {

        //Si está con facebook
        if (AccessToken.getCurrentAccessToken() == null){
            LoginManager.getInstance().logOut();

        }else {
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

            if (opr.isDone()) { //Si ha iniciado sesión con Google
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Intent intent = new Intent(getContext(), LogInActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Algo ha fallado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }else {
                firebaseAuth.signOut();
            }
        }

        Intent intent = new Intent(getContext(), LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}