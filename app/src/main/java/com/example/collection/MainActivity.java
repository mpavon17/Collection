package com.example.collection;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private ChildEventListener childEventListener;
    DatabaseReference bartReference;

    Button boton1;
    EditText nombre1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre1 = findViewById(R.id.editTextText);
        boton1 = findViewById(R.id.button);
        db = FirebaseDatabase.getInstance();
        bartReference = db.getReference();
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.getReference().child("Bart").setValue(nombre1.getText().toString());
            }
        });

    }

    public void insertar(View vista) {


    }
}