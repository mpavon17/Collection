package com.example.collection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Discos extends Fragment {


    EditText grupo, album, anio, fAdquisicion;

    CheckBox cd, vinil;
    Button guardar;

    public Discos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discos, container, false);

        grupo = view.findViewById(R.id.EtGrupo);
        album = view.findViewById(R.id.EtTitulo);
        anio = view.findViewById(R.id.EtAnio);
        fAdquisicion = view.findViewById(R.id.EtFechaAdquisicion);
        cd = view.findViewById(R.id.CbCd);
        vinil = view.findViewById(R.id.CbVinilo);
        guardar = view.findViewById(R.id.btnAñadir);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String grupoTexto = grupo.getText().toString();
                String albumTexto = album.getText().toString();
                String anoTexto = anio.getText().toString();
                String fAdquisicionTexto = fAdquisicion.getText().toString();
                String cdTexto = cd.getText().toString();
                String vinilTexto = vinil.getText().toString();

                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                db.child("Discos").child(grupoTexto).child(albumTexto).child("Año").setValue(anoTexto);
                db.child("Discos").child(grupoTexto).child(albumTexto).child("Fecha de Adquisicion").setValue(fAdquisicionTexto);
                if (cd.isChecked()) {
                    db.child("Discos").child(grupoTexto).child(albumTexto).child("Formato").setValue(cdTexto);
                } else {
                    db.child("Discos").child(grupoTexto).child(albumTexto).child("Formato").setValue(vinilTexto);
                }
            }
        });
        return view;
    }
}

