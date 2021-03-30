package es.joseljg.proyectoempresa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import es.joseljg.proyectoempresa.controladores.CiudadController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuciudades, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //
        switch (item.getItemId()) {
            case R.id.menu_mostrarciudades:
                Intent intent = new Intent(this, MostrarCiudadesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_mostrarprovincias:
                Intent intent2 = new Intent(this, MostrarProvinciasActivity.class);
                startActivity(intent2);
                return true;
            case R.id.menu_irhome:
                Intent intent3 = new Intent(this, MainActivity.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    //---------------------------------------------------------------------------------
    public void mostrarCiudad(View view) {
        Intent intent = new Intent(this, MostrarCiudadesActivity.class);
        startActivity(intent);
    }

    public void mostrarProvincias(View view) {
        Intent intent = new Intent(this, MostrarProvinciasActivity.class);
        startActivity(intent);
    }

}