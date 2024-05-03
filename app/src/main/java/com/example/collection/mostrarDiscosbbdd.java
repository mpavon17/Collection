package com.example.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class mostrarDiscosbbdd extends Fragment {

    private DatabaseReference databaseReference;
    private List<String> discosKeys;
    private ListView lvDiscos;
    private ArrayAdapter<String> discosArrayAdapter;
    private String nombreGrupo;

    public mostrarDiscosbbdd() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_discosbbdd, container, false);
        lvDiscos = view.findViewById(R.id.lvDiscos);
        discosKeys = new ArrayList<>();
        inicializarFirebase();
        Bundle args = getArguments();
        if (args != null) {
            nombreGrupo = args.getString("nombreGrupo", "");
        }
        listarAlbumes(nombreGrupo);

        lvDiscos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String albumSeleccionado = discosArrayAdapter.getItem(position);
                mostrarDetallesDelAlbum(albumSeleccionado);
            }
        });

        return view;
    }

    private void listarAlbumes(String grupoSeleccionado) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            databaseReference.child("usuarios").child(uid).child("Discos").child(grupoSeleccionado).get()
                    .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            discosKeys.clear();
                            if (task.isSuccessful() && task.getResult().exists()) {
                                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                    String album = snapshot.getKey();
                                    discosKeys.add(album);
                                }
                            }
                            discosArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, discosKeys);
                            lvDiscos.setAdapter(discosArrayAdapter);
                        }
                    });
        }
    }

    private void mostrarDetallesDelAlbum(String albumSeleccionado) {
        Bundle args = new Bundle();
        args.putString("nombreAlbum", albumSeleccionado);
        args.putString("nombreGrupo", nombreGrupo);
        Fragment detalleDiscoFragment = new mostrarDiscos12();
        detalleDiscoFragment.setArguments(args);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frameagregar, detalleDiscoFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void inicializarFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }
}
