package com.example.collection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Discos extends Fragment {

    public Discos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discos, container, false);

        Bundle args = getArguments();
        String coleccionSeleccionada = "";
        if (args != null) {
            coleccionSeleccionada = args.getString("coleccionSeleccionada", "");
        }
        String finalColeccionSeleccionada = coleccionSeleccionada;

        if ("Discos".equals(finalColeccionSeleccionada)) {
            Toast.makeText(getActivity(), "Discos", Toast.LENGTH_SHORT).show();
            interfazDiscos(view);
        } else if ("Camisetas".equals(finalColeccionSeleccionada)) {
            Toast.makeText(getActivity(), "Camisetas", Toast.LENGTH_SHORT).show();

            // Crear un EditText dinámicamente
            EditText editTextCamisetas = new EditText(getActivity());
            editTextCamisetas.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            editTextCamisetas.setHint("Ingrese la talla de la camiseta");

            // Añadir el EditText al layout
            LinearLayout layout = view.findViewById(R.id.main);
            layout.addView(editTextCamisetas);
        }

        return view;
    }



    public void interfazDiscos(View vista) {
        // Crear el diseño principal LinearLayout
        LinearLayout mainLayout = new LinearLayout(getContext());
        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(16, 16, 16, 16);

        // Crear un ScrollView para envolver el contenido
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.setLayoutParams(new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT
        ));

        // Añadir el LinearLayout principal al ScrollView
        scrollView.addView(mainLayout);

        // Crear LinearLayout para Grupo
        LinearLayout grupoLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams grupoParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        grupoParams.setMargins(0, 0, 0, 32); // Margen inferior aumentado
        grupoLayout.setLayoutParams(grupoParams);
        grupoLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.addView(grupoLayout);

        // Crear TextView para Grupo
        TextView tvGrupo = new TextView(getContext());
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        tvParams.weight = 1;
        tvGrupo.setLayoutParams(tvParams);
        tvGrupo.setText("Grupo");
        grupoLayout.addView(tvGrupo);

        // Crear EditText para Grupo
        EditText etGrupo = new EditText(getContext());
        LinearLayout.LayoutParams etParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        etParams.weight = 1;
        etGrupo.setLayoutParams(etParams);
        etGrupo.setHint("Grupo");
        grupoLayout.addView(etGrupo);

        // Repetir el mismo proceso para los otros elementos (Álbum, Año, Fecha de Adquisición)

        // Crear LinearLayout para Álbum
        LinearLayout albumLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams albumParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        albumParams.setMargins(0, 0, 0, 32); // Margen inferior aumentado
        albumLayout.setLayoutParams(albumParams);
        albumLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.addView(albumLayout);

        // Crear TextView para Álbum
        TextView tvAlbum = new TextView(getContext());
        tvAlbum.setLayoutParams(tvParams);
        tvAlbum.setText("Álbum");
        albumLayout.addView(tvAlbum);

        // Crear EditText para Álbum
        EditText etAlbum = new EditText(getContext());
        etAlbum.setLayoutParams(etParams);
        etAlbum.setHint("Álbum");
        albumLayout.addView(etAlbum);

        // Crear LinearLayout para Año
        LinearLayout añoLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams añoParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        añoParams.setMargins(0, 0, 0, 32); // Margen inferior aumentado
        añoLayout.setLayoutParams(añoParams);
        añoLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.addView(añoLayout);

        // Crear TextView para Año
        TextView tvAño = new TextView(getContext());
        tvAño.setLayoutParams(tvParams);
        tvAño.setText("Año");
        añoLayout.addView(tvAño);

        // Crear EditText para Año
        EditText etAño = new EditText(getContext());
        etAño.setLayoutParams(etParams);
        etAño.setHint("Año");
        añoLayout.addView(etAño);

        // Crear LinearLayout para Fecha de Adquisición
        LinearLayout fechaLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams fechaParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        fechaParams.setMargins(0, 0, 0, 32); // Margen inferior aumentado
        fechaLayout.setLayoutParams(fechaParams);
        fechaLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.addView(fechaLayout);

        // Crear TextView para Fecha de Adquisición
        TextView tvFecha = new TextView(getContext());
        tvFecha.setLayoutParams(tvParams);
        tvFecha.setText("Fecha de Adquisición");
        fechaLayout.addView(tvFecha);

        // Crear EditText para Fecha de Adquisición
        EditText etFecha = new EditText(getContext());
        etFecha.setLayoutParams(etParams);
        etFecha.setHint("Fecha de Adquisición");
        fechaLayout.addView(etFecha);

        // Repetir el mismo proceso para Formato

        // Crear LinearLayout para Formato
        LinearLayout formatoLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams formatoParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        formatoParams.setMargins(0, 0, 0, 32); // Margen inferior aumentado
        formatoLayout.setLayoutParams(formatoParams);
        formatoLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.addView(formatoLayout);

        // Crear TextView para Formato
        TextView tvFormato = new TextView(getContext());
        tvFormato.setLayoutParams(tvParams);
        tvFormato.setText("Formato");
        formatoLayout.addView(tvFormato);

        // Crear CheckBox para CD
        CheckBox cbCd = new CheckBox(getContext());
        LinearLayout.LayoutParams cbParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cbCd.setLayoutParams(cbParams);
        cbCd.setText("CD");
        formatoLayout.addView(cbCd);

        // Crear CheckBox para Vinilo
        CheckBox cbVinilo = new CheckBox(getContext());
        cbVinilo.setLayoutParams(cbParams);
        cbVinilo.setText("Vinilo");
        formatoLayout.addView(cbVinilo);

        // Crear Button para Añadir
        Button btnAñadir = new Button(getContext());
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnParams.setMargins(0, 32, 0, 0);
        btnAñadir.setLayoutParams(btnParams);
        btnAñadir.setText("Añadir");
        mainLayout.addView(btnAñadir);
        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    String usuario = user.getUid();
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

                    DatabaseReference usuarioDiscosRef = db.child("usuarios").child(usuario).child("Discos").child(etGrupo.getText().toString()).child(etAlbum.getText().toString());

                    usuarioDiscosRef.child("Anio").setValue(etAño.getText().toString());
                    usuarioDiscosRef.child("FechadeAdquisicion").setValue(etFecha.getText().toString());
                    usuarioDiscosRef.child("Formato").setValue(cbCd.isChecked() ? "CD" : "Vinilo");

                    Toast.makeText(getActivity(), "Disco guardado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "No se pudo guardar el disco. Usuario no autenticado.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Agregar el ScrollView al contenido de la actividad
        ((ViewGroup) vista).addView(scrollView);
    }
}
