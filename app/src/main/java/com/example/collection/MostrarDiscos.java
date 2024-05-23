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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MostrarDiscos extends Fragment {

    private DatabaseReference dbr;
    private List<String> discosKeys;
    private ListView lvDiscos;
    private ArrayAdapter<String> discosArrayAdapter;

    public MostrarDiscos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_discos, container, false);
        lvDiscos = view.findViewById(R.id.lvDiscos);
        discosKeys = new ArrayList<>();
        inicializarFirebase();
        listarDatos();
        configurarListenerClick();
        return view;
    }

    private void listarDatos() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            dbr.child("usuarios").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    discosKeys.clear();
                    if (task.isSuccessful() && task.getResult().getValue() != null) {
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            String key = snapshot.getKey();
                            discosKeys.add(key);
                        }
                    }
                    discosArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, discosKeys);
                    lvDiscos.setAdapter(discosArrayAdapter);
                }
            });
        }
    }

    private void inicializarFirebase() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbr = db.getReference();
    }

    private void configurarListenerClick() {
        lvDiscos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedDisc = discosArrayAdapter.getItem(position);
                Fragment detalleDiscoFragment = new BbddDiscos();

                Bundle bundle = new Bundle();
                bundle.putString("selectedDisc", selectedDisc);
                detalleDiscoFragment.setArguments(bundle);

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameagregar, detalleDiscoFragment);
                transaction.addToBackStack(null); // Esto permite volver atrás
                transaction.commit();
            }
        });
    }

}
