package com.example.collection;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private ChildEventListener childEventListener;
    DatabaseReference bartReference;

    Button boton1;
    ImageButton salir, home;
    EditText nombre1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void agregar(View v) {
        FragmentManager fm = getSupportFragmentManager();
        Agregar a = new Agregar();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameagregar, a);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void salir (View v) {
        finish();
    }

    public void inicio(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Inicio i = new Inicio();
        ft.replace(R.id.frameagregar,i);
        ft.addToBackStack(null);
        ft.commit();
    }
}