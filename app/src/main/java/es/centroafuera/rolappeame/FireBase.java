package es.centroafuera.rolappeame;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FireBase {

    protected void onCreate(Bundle savedInstanceState) {
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> personaje = new HashMap<>();
        personaje.put("nombre", "Adelaida");
        personaje.put("raza", "Tiefling");
        personaje.put("oficio", "Explorador");
        personaje.put("fuerza", 2);
        personaje.put("agilidad", 2);
        personaje.put("percepcion", 2);
        personaje.put("constitucion", 2);
        personaje.put("inteligencia", 2);
        personaje.put("carisma", 2);


        // Add a new document with a generated ID
        db.collection("personajes")
                .add(personaje)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}
