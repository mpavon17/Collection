package com.example.collection;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MisColecciones extends Fragment {

    private String[] nombres = {"Colección 1", "Colección 2", "Colección 3", "Colección 4", "Colección 5", "Colección 6", "Colección 7", "Colección 8", "Colección 9", "Colección 10"};

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
        AdaptadorSimpson adaptador = new AdaptadorSimpson(nombres);

        // Configurar el RecyclerView con un LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Establecer el adaptador en el RecyclerView
        recyclerView.setAdapter(adaptador);

        return rootView;
    }

    public static class AdaptadorSimpson extends RecyclerView.Adapter<AdaptadorSimpson.AdaptadorSimpsonHolder> {
        private String[] nombres;

        public AdaptadorSimpson(String[] nombres) {
            this.nombres = nombres;
        }

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
            holder.bind(nombres[position]);
        }

        @Override
        public int getItemCount() {
            // Devolver el número total de elementos en la lista
            return nombres.length;
        }

        public static class AdaptadorSimpsonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView tvNombreColeccion;

            public AdaptadorSimpsonHolder(@NonNull View itemView) {
                super(itemView);
                tvNombreColeccion = itemView.findViewById(R.id.textView);
                itemView.setOnClickListener(this);
            }

            public void bind(String nombre) {
                // Asignar los datos a los elementos de la vista
                tvNombreColeccion.setText(nombre);
            }

            @Override
            public void onClick(View v) {
                // Manejar el clic en el elemento de la lista
                String nombreSeleccionado = tvNombreColeccion.getText().toString();
                // Aquí puedes abrir una nueva actividad o realizar otra acción según el elemento seleccionado
            }
        }
    }
}
