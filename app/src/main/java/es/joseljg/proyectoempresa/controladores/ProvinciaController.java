package es.joseljg.proyectoempresa.controladores;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import es.joseljg.proyectoempresa.clases.Provincia;
import es.joseljg.proyectoempresa.tareas.TareaCantidadProvincias;
import es.joseljg.proyectoempresa.tareas.TareaMostrarProvincias;

public class ProvinciaController {
    public static ArrayList<Provincia> obtenerProvincias(int pagina)
    {
        ArrayList<Provincia> provinciasDevueltas = null;
        FutureTask t = new FutureTask (new TareaMostrarProvincias(pagina));
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(t);
        try {
            provinciasDevueltas= (ArrayList<Provincia>)t.get();
            es.shutdown();
            try {
                if (!es.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    es.shutdownNow();
                }
            } catch (InterruptedException e) {
                es.shutdownNow();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return provinciasDevueltas;
    }
    //---------------------------------------------------------------------------
    public static int obtenerCantidadProvincias( )
    {
        int cantidadProvincias = 0;
        FutureTask t = new FutureTask (new TareaCantidadProvincias());
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(t);
        try {
            cantidadProvincias = (int)t.get();
            es.shutdown();
            try {
                if (!es.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    es.shutdownNow();
                }
            } catch (InterruptedException e) {
                es.shutdownNow();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cantidadProvincias;
    }
}
