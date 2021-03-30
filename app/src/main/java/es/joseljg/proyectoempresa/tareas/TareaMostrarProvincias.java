package es.joseljg.proyectoempresa.tareas;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import es.joseljg.proyectoempresa.clases.Provincia;
import es.joseljg.proyectoempresa.modelos.ProvinciaDB;

public class TareaMostrarProvincias implements Callable<ArrayList<Provincia>> {

    private ArrayList<Provincia> provincias = null;
    private int pagina;
    public TareaMostrarProvincias(int pagina) {
        this.pagina = pagina;

    }

    @Override
    public ArrayList<Provincia> call() throws Exception {
        provincias = ProvinciaDB.obtenerProvincias(this.pagina);
        return provincias;
    }
}
