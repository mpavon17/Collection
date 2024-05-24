package com.example.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _04_MostrarDiscos extends Fragment {

    private TextView textViewAnio, textViewFechadeAdquisicion, textViewFormato;
    private DatabaseReference databaseReference;
    private String anio, fechadeAdquisicion, formato;
    private String genSelect, grupoSeleccionado, nombreAlbum;

    public _04_MostrarDiscos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_discos12, container, false);
        textViewAnio = view.findViewById(R.id.txtAnio);
        textViewFechadeAdquisicion = view.findViewById(R.id.txtFechaAd);
        textViewFormato = view.findViewById(R.id.txtFormato);
        inicializarFirebase();
        Bundle args = getArguments();
        if (args != null) {
            genSelect = args.getString("genSelect", "");
             grupoSeleccionado = args.getString("nombreGrupo", "");
           nombreAlbum = args.getString("nombreAlbum", "");
            mostrarDatosDelAlbum(grupoSeleccionado, nombreAlbum);
        }
        return view;
    }

    private void mostrarDatosDelAlbum(String grupoSeleccionado, String albumSeleccionado) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            databaseReference.child("usuarios").child(uid).child(genSelect).child(grupoSeleccionado).child(albumSeleccionado)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Obtener los valores de los datos del álbum
                                anio = snapshot.child("Anio").getValue(String.class);
                                fechadeAdquisicion = snapshot.child("FechadeAdquisicion").getValue(String.class);
                                formato = snapshot.child("Formato").getValue(String.class);

                                // Mostrar los datos en los TextView correspondientes
                                textViewAnio.setText(anio);
                                textViewFechadeAdquisicion.setText(fechadeAdquisicion);
                                textViewFormato.setText(formato);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Manejar el error si es necesario
                        }
                    });
        }
    }

    private void inicializarFirebase() {
        // Obtener la instancia de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Obtener la referencia a la base de datos
        databaseReference = database.getReference();
    }
}
