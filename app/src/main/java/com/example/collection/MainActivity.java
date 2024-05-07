package com.example.collection;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private ChildEventListener childEventListener;
    private DatabaseReference bartReference;

    private Button boton1;
    private Button btn2;
    private EditText nombre1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void agregar(View v) {
        FragmentManager fm = getSupportFragmentManager();
        Agregar a = new Agregar();
        FragmentTransaction ft = fm.beginTransaction();
        //ft.replace(R.id.fragment_agregar, a);
        ft.addToBackStack(null);
        ft.commit();
    }
}
