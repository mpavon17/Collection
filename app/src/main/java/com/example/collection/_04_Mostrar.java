package com.example.collection;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;

public class _04_Mostrar extends Fragment {

    private DatabaseReference databaseReference;
    private String genSelect, grupoSeleccionado, nombreAlbum;
    private LinearLayout container;
    private Map<String, String> originalValues; // Almacena los valores originales

    public _04_Mostrar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_discos12, container, false);
        this.container = view.findViewById(R.id.container); // Obtén la referencia al LinearLayout
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
                                // Limpiar el contenedor antes de agregar nuevos elementos
                                container.removeAllViews();

                                originalValues = new HashMap<>(); // Inicializar el mapa de valores originales
                                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                    // Obtener la clave y el valor de cada hijo
                                    final String clave = childSnapshot.getKey();
                                    final String valor = childSnapshot.getValue(String.class);
                                    originalValues.put(clave, valor); // Guardar el valor original

                                    // Crear un nuevo LinearLayout para cada entrada
                                    LinearLayout entryLayout = new LinearLayout(getContext());
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    layoutParams.setMargins(0, 0, 0, 16); // Agrega margen inferior
                                    entryLayout.setLayoutParams(layoutParams);
                                    entryLayout.setOrientation(LinearLayout.VERTICAL);

                                    // Crear un TextView para la clave
                                    TextView keyTextView = new TextView(getContext());
                                    keyTextView.setText(clave);
                                    keyTextView.setTextSize(20); // Tamaño grande
                                    keyTextView.setTypeface(null, Typeface.BOLD); // Texto en negrita
                                    entryLayout.addView(keyTextView);

                                    // Crear un EditText para el valor
                                    final EditText valueEditText = new EditText(getContext());
                                    valueEditText.setText(valor);
                                    valueEditText.setBackground(getResources().getDrawable(R.drawable.style_borde_et)); // Borde cuadrado
                                    entryLayout.addView(valueEditText);

                                    // Agregar el LinearLayout al contenedor
                                    container.addView(entryLayout);
                                }

                                // Crear un Button para modificar todos los valores
                                Button modifyButton = new Button(getContext());
                                modifyButton.setText("Modificar");
                                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                buttonParams.gravity = Gravity.CENTER_HORIZONTAL; // Centrar el botón
                                modifyButton.setLayoutParams(buttonParams);
                                modifyButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Actualizar solo los valores modificados en la base de datos
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user != null) {
                                            String uid = user.getUid();
                                            DatabaseReference albumRef = databaseReference.child("usuarios").child(uid).child(genSelect).child(grupoSeleccionado).child(albumSeleccionado);
                                            for (int i = 0; i < container.getChildCount(); i++) {
                                                View childView = container.getChildAt(i);
                                                if (childView instanceof LinearLayout) {
                                                    LinearLayout entryLayout = (LinearLayout) childView;
                                                    TextView keyTextView = (TextView) entryLayout.getChildAt(0);
                                                    EditText valueEditText = (EditText) entryLayout.getChildAt(1);
                                                    String clave = keyTextView.getText().toString();
                                                    String valorActual = valueEditText.getText().toString();
                                                    albumRef.child(clave).setValue(valorActual);
                                                }
                                            }
                                        }
                                    }
                                });
                                container.addView(modifyButton);
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
