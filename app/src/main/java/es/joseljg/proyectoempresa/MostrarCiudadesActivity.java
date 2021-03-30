package es.joseljg.proyectoempresa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import es.joseljg.proyectoempresa.clases.Ciudad;
import es.joseljg.proyectoempresa.clases.ConfiguracionesGenerales;
import es.joseljg.proyectoempresa.clases.ListaCiudadesAdapter;
import es.joseljg.proyectoempresa.utilidades.PaginationListener;
import es.joseljg.proyectoempresa.controladores.CiudadController;


public class MostrarCiudadesActivity extends AppCompatActivity {

    private static final int PETICION1 = 1;
    private RecyclerView mRecyclerView;
    private ListaCiudadesAdapter mAdapter;
    private ArrayList<Ciudad> ciudades;
    private int pagina_actual;
    private int total_registros;
    private int total_paginas;
    private int num_columnas_landscape;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_ciudades);
        //-----------------------------------------------------------
        total_registros = CiudadController.obtenerCantidadCiudades();
        Log.i("sql", "total registros -> " + String.valueOf(total_registros));

        total_paginas = (total_registros / ConfiguracionesGenerales.ELEMENTOS_POR_PAGINA) +  1;
        Log.i("sql", "total paginas -> " + String.valueOf(total_paginas));

        pagina_actual=0;
        num_columnas_landscape = 2;
       ciudades = CiudadController.obtenerCiudades(pagina_actual);
        Log.i("sql", "pagina actual -> " + String.valueOf(pagina_actual));
        Log.i("sql", "ciudades leidas -> " + String.valueOf(this.ciudades.size()));
        pagina_actual++;
        if(ciudades != null) {
            mRecyclerView = findViewById(R.id.rv_ciudades);
            mAdapter = new ListaCiudadesAdapter(this, ciudades);
            mRecyclerView.setAdapter(mAdapter);
            int orientation = 1;
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, ConfiguracionesGenerales.LANDSCAPE_NUM_COLUMNAS));
            }

        // paginacion
            mRecyclerView.addOnScrollListener(new PaginationListener((LinearLayoutManager) mRecyclerView.getLayoutManager()) {
                private int ciudades_leidas = 0;
                @Override
                protected void loadMoreItems() {

                             int total_registros_leidos = mRecyclerView.getLayoutManager().getItemCount();
                            if (total_registros_leidos < total_registros) {
                                ArrayList<Ciudad> nuevasCiudades = CiudadController.obtenerCiudades(pagina_actual);
                                ciudades_leidas = nuevasCiudades.size();
                                pagina_actual++;

                               Boolean resultado = mRecyclerView.post(new Runnable()
                                {
                                    @Override
                                    public void run() {
                                        ListaCiudadesAdapter mAdapter1 =(ListaCiudadesAdapter) mRecyclerView.getAdapter();
                                        ArrayList<Ciudad> ciudades_rv = mAdapter1.getListaCiudades();
                                        ciudades_rv.addAll(nuevasCiudades);
                                        mRecyclerView.getAdapter().notifyDataSetChanged();
                                   }});
                                Log.i("sql", "siguiente pagina -> " + String.valueOf(pagina_actual));
                                Log.i("sql", "total registros -> " + String.valueOf(total_registros));
                                Log.i("sql", "total registros leidos -> " + String.valueOf(total_registros_leidos));
                                Log.i("sql", "ciudades leidas -> " + String.valueOf(this.ciudades_leidas));

                            }
                            else{
                                ciudades_leidas = 0;
                            }
                }
                @Override
                public boolean isLastPage() {
                    if(pagina_actual > total_paginas -1 ) {
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            });
        }
        else{
            mostrarToast("no se pudo establecer la conexion con la base de datos");
            Log.i("sql", "error en el sql");
        }
    }

    private void mostrarToast(String texto) {
        Toast.makeText(this,texto, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}