package com.example.collection;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
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
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mostrarWeb();
    }

    public void mostrarWeb (){
        webView = findViewById(R.id.webView);
        //WebSettings webSettings = webView.getSettings();
        webView.clearCache(true);
       // webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/coollections.html");
    }

    public void agregar(View v) {
        FragmentManager fm = getSupportFragmentManager();
        Agregar a = new Agregar();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameagregar, a);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void salir(View v) {
        finish();
    }

    public void inicio(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Inicio i = new Inicio();
        ft.replace(R.id.frameagregar, i);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void discos(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AgregarFirebase d = new AgregarFirebase();
        Button btnDiscos = findViewById(R.id.discos);
        Button btnCamisetas = findViewById(R.id.camisetas);
        Button btnLibros = findViewById(R.id.libros);
        Button btnCine = findViewById(R.id.movie);
        Button btnCustom = findViewById(R.id.add);

        String coleccionSeleccionada = "";
        if (btnDiscos.isPressed()) {
            coleccionSeleccionada = "Discos";
        } else if (btnCamisetas.isPressed()) {
            coleccionSeleccionada = "Camisetas";
        } else if (btnLibros.isPressed()) {
            coleccionSeleccionada = "Libros";
        }else if(btnCine.isPressed()){
            coleccionSeleccionada = "Cine";
        }else if (btnCustom.isPressed()){
            coleccionSeleccionada = "Custom";
        }
        Bundle args = new Bundle();
        args.putString("coleccionSeleccionada", coleccionSeleccionada);
        d.setArguments(args);
        ft.replace(R.id.frameMostrarDinamico, d);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void mostrar(View v) {
        webView.setVisibility(View.GONE);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        _01_Mostrar m = new _01_Mostrar();
        ft.replace(R.id.frameagregar, m);
        ft.addToBackStack(null);
        ft.commit();
    }

}