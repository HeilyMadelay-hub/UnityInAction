package org.meicode.MenuPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.meicode.handson.R;


public class MainActivity extends AppCompatActivity {

    Button buttonActivityDonaciones;
    Button buttonActivityIniciar;
    Button buttonActivityRegistrarBoton;
    Button buttonActivityVer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        buttonActivityDonaciones=findViewById(R.id.donateButton);
        buttonActivityIniciar=findViewById(R.id.loginButton);
        buttonActivityRegistrarBoton = findViewById(R.id.registerButton);
        buttonActivityVer= findViewById(R.id.viewNewsButton);



        buttonActivityRegistrarBoton.setOnClickListener(view -> {
            Intent intent = new Intent(org.meicode.MenuPrincipal.MainActivity.this, ActivityRegistrarse.class);
            startActivity(intent);
        });

        buttonActivityIniciar.setOnClickListener(view -> {
            Intent intent = new Intent(org.meicode.MenuPrincipal.MainActivity.this, ActivityIniciar.class);
            startActivity(intent);
        });

        buttonActivityDonaciones.setOnClickListener(view -> {
            Intent intent = new Intent(org.meicode.MenuPrincipal.MainActivity.this, ActivityDonaciones.class);
            startActivity(intent);
        });

        buttonActivityVer.setOnClickListener(view -> {
            Intent intent = new Intent(org.meicode.MenuPrincipal.MainActivity.this, ActivityVernoticias.class);
            startActivity(intent);
        });

    }
}