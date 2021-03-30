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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import es.joseljg.proyectoempresa.clases.Provincia;
import es.joseljg.proyectoempresa.clases.ConfiguracionesGenerales;
import es.joseljg.proyectoempresa.clases.ListaProvinciasAdapter;
import es.joseljg.proyectoempresa.utilidades.PaginationListener;
import es.joseljg.proyectoempresa.controladores.ProvinciaController;

public class MostrarProvinciasActivity extends AppCompatActivity {


        private static final int PETICION1 = 1;
        private RecyclerView mRecyclerView;
        private ListaProvinciasAdapter mAdapter;
        private ArrayList<Provincia> provincias;
        private int pagina_actual;
        private int total_registros;
        private int total_paginas;
        private int num_columnas_landscape;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mostrar_provincias);
            //-----------------------------------------------------------
            total_registros = ProvinciaController.obtenerCantidadProvincias();
            Log.i("sql", "total registros -> " + String.valueOf(total_registros));

            total_paginas = (total_registros / ConfiguracionesGenerales.ELEMENTOS_POR_PAGINA) +  1;
            Log.i("sql", "total paginas -> " + String.valueOf(total_paginas));

            pagina_actual=0;
            num_columnas_landscape = 2;
            provincias = ProvinciaController.obtenerProvincias(pagina_actual);

            pagina_actual++;
            if(provincias != null) {
                Log.i("sql", "pagina actual -> " + String.valueOf(pagina_actual));
                Log.i("sql", "provincias leidas -> " + String.valueOf(this.provincias.size()));
                mRecyclerView = findViewById(R.id.rv_provincias);
                mAdapter = new ListaProvinciasAdapter(this, provincias);
                mRecyclerView.setAdapter(mAdapter);
                int orientation = 1;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                } else {
                    mRecyclerView.setLayoutManager(new GridLayoutManager(this, ConfiguracionesGenerales.LANDSCAPE_NUM_COLUMNAS));
                }

                // paginacion
                mRecyclerView.addOnScrollListener(new PaginationListener((LinearLayoutManager) mRecyclerView.getLayoutManager()) {
                    private int provincias_leidas = 0;
                    @Override
                    protected void loadMoreItems() {

                        int total_registros_leidos = mRecyclerView.getLayoutManager().getItemCount();
                        if (total_registros_leidos < total_registros) {
                            ArrayList<Provincia> nuevasProvincias = ProvinciaController.obtenerProvincias(pagina_actual);
                            provincias_leidas = nuevasProvincias.size();
                            pagina_actual++;

                            Boolean resultado = mRecyclerView.post(new Runnable()
                            {
                                @Override
                                public void run() {
                                    ListaProvinciasAdapter mAdapter1 =(ListaProvinciasAdapter) mRecyclerView.getAdapter();
                                    ArrayList<Provincia> provincias_rv = mAdapter1.getListaProvincias();
                                    provincias_rv.addAll(nuevasProvincias);
                                    mRecyclerView.getAdapter().notifyDataSetChanged();
                                }});
                            Log.i("sql", "siguiente pagina -> " + String.valueOf(pagina_actual));
                            Log.i("sql", "total registros -> " + String.valueOf(total_registros));
                            Log.i("sql", "total registros leidos -> " + String.valueOf(total_registros_leidos));
                            Log.i("sql", "provincias leidas -> " + String.valueOf(this.provincias_leidas));

                        }
                        else{
                            provincias_leidas = 0;
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
        //----------------------------------------------------------------------------
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
}