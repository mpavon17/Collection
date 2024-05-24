package com.example.collection;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
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

public class AgregarFirebase extends Fragment {

    public AgregarFirebase() {
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
            interfazDiscos(view);
        } else if ("Camisetas".equals(finalColeccionSeleccionada)) {
            interfazCamisetas(view);
        } else if ("Libros".equals(finalColeccionSeleccionada)) {
            interfazLibros(view);
        }else if ("Cine".equals(finalColeccionSeleccionada)) {
            interfazCine(view);
        }

        return view;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void interfazCine(View vista) {
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

        // Array de nombres para los campos
        String[] nombresCampos = {"Director", "Pelicula", "Año", "Fecha de Adquisición"};

        // Array para los EditText correspondientes
        EditText[] editTexts = new EditText[nombresCampos.length];

        // Agregar los elementos al diseño principal
        for (int i = 0; i < nombresCampos.length; i++) {
            // Crear LinearLayout para el grupo actual
            LinearLayout grupoLayout = new LinearLayout(getContext());
            grupoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            grupoLayout.setOrientation(LinearLayout.VERTICAL);
            grupoLayout.setPadding(0, 0, 0, 32);
            mainLayout.addView(grupoLayout);

            // Crear TextView para el campo actual
            TextView textView = new TextView(getContext());
            textView.setText(nombresCampos[i]);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            grupoLayout.addView(textView);

            // Crear EditText para el campo actual
            EditText editText = new EditText(getContext());
            editText.setHint(nombresCampos[i]);
            editTexts[i] = editText;
            editText.setBackground(getResources().getDrawable(R.drawable.style_borde_et));
            grupoLayout.addView(editText);
        }

        // Crear TextView para los checkboxes de Formato
        TextView formatoTextView = new TextView(getContext());
        formatoTextView.setText("Formato");
        formatoTextView.setTypeface(null, Typeface.BOLD);
        formatoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mainLayout.addView(formatoTextView);

        // Crear LinearLayout para los checkboxes de Formato
        LinearLayout formatoCheckboxLayout = new LinearLayout(getContext());
        formatoCheckboxLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        formatoCheckboxLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.addView(formatoCheckboxLayout);

        // Crear CheckBox para CD
        CheckBox cbCd = new CheckBox(getContext());
        cbCd.setText("CD");
        formatoCheckboxLayout.addView(cbCd);

        // Crear CheckBox para Vinilo
        CheckBox cbVinilo = new CheckBox(getContext());
        cbVinilo.setText("VHS");
        formatoCheckboxLayout.addView(cbVinilo);

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

                    DatabaseReference usuarioDiscosRef = db.child("usuarios").child(usuario).child("Cine").child(editTexts[0].getText().toString()).child(editTexts[1].getText().toString());

                    usuarioDiscosRef.child("Año").setValue(editTexts[2].getText().toString());
                    usuarioDiscosRef.child("Fecha de Adquisicion").setValue(editTexts[3].getText().toString());
                    usuarioDiscosRef.child("Formato").setValue(cbCd.isChecked() ? "CD" : (cbVinilo.isChecked() ? "VHS" : ""));

                    Toast.makeText(getActivity(), "Pelicula guardado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "No se pudo guardar el Pelicula. Usuario no autenticado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((ViewGroup) vista).addView(scrollView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void interfazLibros(View vista) {
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

        // Array de nombres para los campos
        String[] nombresCampos = {"Autor", "Libro", "Año", "Fecha de Adquisición"};

        // Array para los EditText correspondientes
        EditText[] editTexts = new EditText[nombresCampos.length];

        // Agregar los elementos al diseño principal
        for (int i = 0; i < nombresCampos.length; i++) {
            // Crear LinearLayout para el grupo actual
            LinearLayout grupoLayout = new LinearLayout(getContext());
            grupoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            grupoLayout.setOrientation(LinearLayout.VERTICAL);
            grupoLayout.setPadding(0, 0, 0, 32);
            mainLayout.addView(grupoLayout);

            // Crear TextView para el campo actual
            TextView textView = new TextView(getContext());
            textView.setText(nombresCampos[i]);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            grupoLayout.addView(textView);

            // Crear EditText para el campo actual
            EditText editText = new EditText(getContext());
            editText.setHint(nombresCampos[i]);
            editTexts[i] = editText;
            editText.setBackground(getResources().getDrawable(R.drawable.style_borde_et));
            grupoLayout.addView(editText);
        }

        // Crear TextView para los checkboxes de Formato
        TextView formatoTextView = new TextView(getContext());
        formatoTextView.setText("Formato");
        formatoTextView.setTypeface(null, Typeface.BOLD);
        formatoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mainLayout.addView(formatoTextView);

        // Crear LinearLayout para los checkboxes de Formato
        LinearLayout formatoCheckboxLayout = new LinearLayout(getContext());
        formatoCheckboxLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        formatoCheckboxLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.addView(formatoCheckboxLayout);

        // Crear CheckBox para CD
        CheckBox cbCd = new CheckBox(getContext());
        cbCd.setText("Bolsillo");
        formatoCheckboxLayout.addView(cbCd);

        // Crear CheckBox para Vinilo
        CheckBox cbVinilo = new CheckBox(getContext());
        cbVinilo.setText("Tapa Dura");
        formatoCheckboxLayout.addView(cbVinilo);

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

                    DatabaseReference usuarioDiscosRef = db.child("usuarios").child(usuario).child("Libros").child(editTexts[0].getText().toString()).child(editTexts[1].getText().toString());

                    usuarioDiscosRef.child("Año").setValue(editTexts[2].getText().toString());
                    usuarioDiscosRef.child("Fecha de Adquisicion").setValue(editTexts[3].getText().toString());
                    usuarioDiscosRef.child("Formato").setValue(cbCd.isChecked() ? "Bolsillo" : (cbVinilo.isChecked() ? "Tapa Dura" : ""));

                    Toast.makeText(getActivity(), "Libro guardado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "No se pudo guardar el Libro. Usuario no autenticado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((ViewGroup) vista).addView(scrollView);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
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

        // Array de nombres para los campos
        String[] nombresCampos = {"Grupo", "Álbum", "Año", "Fecha de Adquisición"};

        // Array para los EditText correspondientes
        EditText[] editTexts = new EditText[nombresCampos.length];

        // Agregar los elementos al diseño principal
        for (int i = 0; i < nombresCampos.length; i++) {
            // Crear LinearLayout para el grupo actual
            LinearLayout grupoLayout = new LinearLayout(getContext());
            grupoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            grupoLayout.setOrientation(LinearLayout.VERTICAL);
            grupoLayout.setPadding(0, 0, 0, 32);
            mainLayout.addView(grupoLayout);

            // Crear TextView para el campo actual
            TextView textView = new TextView(getContext());
            textView.setText(nombresCampos[i]);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            grupoLayout.addView(textView);

            // Crear EditText para el campo actual
            EditText editText = new EditText(getContext());
            editText.setHint(nombresCampos[i]);
            editTexts[i] = editText;
            editText.setBackground(getResources().getDrawable(R.drawable.style_borde_et));
            grupoLayout.addView(editText);
        }

        // Crear TextView para los checkboxes de Formato
        TextView formatoTextView = new TextView(getContext());
        formatoTextView.setText("Formato");
        formatoTextView.setTypeface(null, Typeface.BOLD);
        formatoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mainLayout.addView(formatoTextView);

        // Crear LinearLayout para los checkboxes de Formato
        LinearLayout formatoCheckboxLayout = new LinearLayout(getContext());
        formatoCheckboxLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        formatoCheckboxLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.addView(formatoCheckboxLayout);

        // Crear CheckBox para CD
        CheckBox cbCd = new CheckBox(getContext());
        cbCd.setText("CD");
        formatoCheckboxLayout.addView(cbCd);

        // Crear CheckBox para Vinilo
        CheckBox cbVinilo = new CheckBox(getContext());
        cbVinilo.setText("Vinilo");
        formatoCheckboxLayout.addView(cbVinilo);

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

                    DatabaseReference usuarioDiscosRef = db.child("usuarios").child(usuario).child("Discos").child(editTexts[0].getText().toString()).child(editTexts[1].getText().toString());

                    usuarioDiscosRef.child("Año").setValue(editTexts[2].getText().toString());
                    usuarioDiscosRef.child("Fecha de Adquisicion").setValue(editTexts[3].getText().toString());
                    usuarioDiscosRef.child("Formato").setValue(cbCd.isChecked() ? "CD" : (cbVinilo.isChecked() ? "Vinilo" : ""));

                    Toast.makeText(getActivity(), "Disco guardado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "No se pudo guardar el disco. Usuario no autenticado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((ViewGroup) vista).addView(scrollView);
    }

    public void interfazCamisetas(View vista) {
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

        // Array de nombres para los campos
        String[] nombresCampos = {"Equipo", "Jugador", "Dorsal", "Talla", "Color"};

        // Array para los EditText correspondientes
        EditText[] editTexts = new EditText[nombresCampos.length];

        // Agregar los elementos al diseño principal
        for (int i = 0; i < nombresCampos.length; i++) {
            // Crear LinearLayout para el grupo actual
            LinearLayout grupoLayout = new LinearLayout(getContext());
            grupoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            grupoLayout.setOrientation(LinearLayout.VERTICAL);
            grupoLayout.setPadding(0, 0, 0, 32);
            mainLayout.addView(grupoLayout);

            // Crear TextView para el campo actual
            TextView textView = new TextView(getContext());
            textView.setText(nombresCampos[i]);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            grupoLayout.addView(textView);

            // Crear EditText para el campo actual
            EditText editText = new EditText(getContext());
            editText.setHint(nombresCampos[i]);
            editTexts[i] = editText;
            editText.setBackground(getResources().getDrawable(R.drawable.style_borde_et));
            grupoLayout.addView(editText);
        }

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

                    DatabaseReference usuarioCamisetasRef = db.child("usuarios").child(usuario).child("Camisetas").child(editTexts[0].getText().toString()).child(editTexts[1].getText().toString());

                    usuarioCamisetasRef.child("Dorsal").setValue(editTexts[2].getText().toString());
                    usuarioCamisetasRef.child("Talla").setValue(editTexts[3].getText().toString());
                    usuarioCamisetasRef.child("Color").setValue(editTexts[4].getText().toString());

                    Toast.makeText(getActivity(), "Camiseta guardada exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "No se pudo guardar la camiseta. Usuario no autenticado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((ViewGroup) vista).addView(scrollView);
    }
}
