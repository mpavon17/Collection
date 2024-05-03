package com.example.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class MisColecciones extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference coleccionesRef = db.collection("coollections");

    private List<String> nombres = new ArrayList<>();
    private List<String> idsUsuarios = new ArrayList<>();
    private List<String> discos = new ArrayList<>();
    private AdaptadorSimpson adaptador;

    public MisColecciones() {
        // Constructor público vacío requerido por Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View rootView = inflater.inflate(R.layout.fragment_mis_colecciones, container, false);

        // Encontrar el RecyclerView en el diseño del fragmento
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        // Crear un adaptador para el RecyclerView
        adaptador = new AdaptadorSimpson();

        // Configurar el RecyclerView con un LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Establecer el adaptador en el RecyclerView
        recyclerView.setAdapter(adaptador);

        // Obtener datos de Firebase Firestore
        obtenerDatosFirestore();

        return rootView;
    }

    private void obtenerDatosFirestore() {
        coleccionesRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Obtener datos de cada documento y agregarlos a las listas
                    String nombre = document.getString("nombre");
                    String idUsuario = document.getString("idUsuario");
                    String disco = document.getString("disco");
                    nombres.add(nombre);
                    idsUsuarios.add(idUsuario);
                    discos.add(disco);
                }
                // Notificar al adaptador que los datos han cambiado
                adaptador.notifyDataSetChanged();
            } else {
                // Manejar errores si la consulta no es exitosa
            }
        });
    }

    public class AdaptadorSimpson extends RecyclerView.Adapter<AdaptadorSimpson.AdaptadorSimpsonHolder> {

        @NonNull
        @Override
        public AdaptadorSimpsonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflar el diseño de cada elemento de la lista
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.colecciones, parent, false);
            return new AdaptadorSimpsonHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorSimpsonHolder holder, int position) {
            // Vincular los datos a cada elemento de la lista
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            // Devolver el número total de elementos en la lista
            return nombres.size();
        }

        public class AdaptadorSimpsonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView tvNombreColeccion;

            public AdaptadorSimpsonHolder(@NonNull View itemView) {
                super(itemView);
                tvNombreColeccion = itemView.findViewById(R.id.textView);
                itemView.setOnClickListener(this);
            }

            public void bind(int position) {
                // Asignar los datos a los elementos de la vista
                String nombreColeccion = nombres.get(position);
                tvNombreColeccion.setText(nombreColeccion);
            }

            @Override
            public void onClick(View v) {
                // Manejar el clic en el elemento de la lista
                String nombreSeleccionado = nombres.get(getAdapterPosition());
                // Aquí puedes abrir una nueva actividad o realizar otra acción según el elemento seleccionado
            }
        }
    }
}

