package es.joseljg.proyectoempresa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import es.joseljg.proyectoempresa.controladores.CiudadController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void mostrarCiudad(View view) {
        Intent intent = new Intent(this, MostrarCiudadesActivity.class);
        startActivity(intent);
    }

    public void mostrarProvincias(View view) {
        Intent intent = new Intent(this, MostrarProvinciasActivity.class);
        startActivity(intent);
    }
}