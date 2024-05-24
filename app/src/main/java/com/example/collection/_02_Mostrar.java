package com.example.collection;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class _02_Mostrar extends Fragment {

    private DatabaseReference dbr;
    private List<String> discosKeys;
    private ListView lv;
    private ArrayAdapter<String> ArrayAdapter;
    private String genSelect;

    public _02_Mostrar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_discos, container, false);
        lv = view.findViewById(R.id.lvDiscos);

        // Obtener el argumento pasado al fragmento
        Bundle args = getArguments();
        if (args != null) {
            genSelect = args.getString("generalSeleccionado", "");
        }

        discosKeys = new ArrayList<>();
        inicializarFirebase();
        listarDatos();
        configurarListenerClick();

        return view;
    }

    private void listarDatos() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && genSelect != null) {
            String uid = currentUser.getUid();
            dbr.child("usuarios").child(uid).child(genSelect).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    discosKeys.clear();
                    if (task.isSuccessful() && task.getResult().exists()) {
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            String grupo = snapshot.getKey();
                            discosKeys.add(grupo);
                        }
                    }
                    ArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, discosKeys);
                    lv.setAdapter(ArrayAdapter);
                }
            });
        }
    }

    private void configurarListenerClick() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedDisc = ArrayAdapter.getItem(position);
                _03_Mostrar fragment = new _03_Mostrar();
                Bundle args = new Bundle();
                args.putString("genSelect",  genSelect);
                args.putString("nombreGrupo", selectedDisc);
                fragment.setArguments(args);
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameagregar, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbr = db.getReference();
    }
}