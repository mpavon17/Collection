package com.example.collection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Discos extends Fragment {


    EditText grupo, album, ano;

    TextView grupo1, album1, ano1;
    Button guardar;

    public Discos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discos, container, false);

        grupo = view.findViewById(R.id.editTextText);
        album = view.findViewById(R.id.editTextText2);
        ano1 = view.findViewById(R.id.EtAnio);
        grupo1 = view.findViewById(R.id.textView);
        ano = view.findViewById(R.id.TvAnio);
        album1 = view.findViewById(R.id.textView4);
        guardar = view.findViewById(R.id.button);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String grupoTexto = grupo.getText().toString();
                String albumTexto = album.getText().toString();
                String anoTexto = ano.getText().toString();

                // Asegúrate de manejar el caso cuando alguno de los EditText esté vacío
                if (grupoTexto.isEmpty() || albumTexto.isEmpty() || anoTexto.isEmpty()) {
                    Toast.makeText(getActivity(), "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                db.child("Discos").child(grupoTexto).child(albumTexto).child("Año").setValue(anoTexto);


            }
        });

        return view;
    }


}

