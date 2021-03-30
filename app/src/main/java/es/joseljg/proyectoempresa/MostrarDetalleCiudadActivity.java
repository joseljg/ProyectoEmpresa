package es.joseljg.proyectoempresa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import es.joseljg.proyectoempresa.clases.Ciudad;
import es.joseljg.proyectoempresa.clases.CiudadViewHolder;
import es.joseljg.proyectoempresa.utilidades.ImagenesBlobBitmap;

import static es.joseljg.proyectoempresa.clases.CiudadViewHolder.EXTRA_OBJETO_CIUDAD;

public class MostrarDetalleCiudadActivity extends AppCompatActivity {
    TextView txt_detalle_nombrec = null;
    TextView txt_detalle_habitantes = null;
    TextView txt_detalle_idprovincia = null;
    ImageView img_detalle_ciudad= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_detalle_ciudad);
        txt_detalle_nombrec = (TextView)findViewById(R.id.txt_detalle_nombrec);
        txt_detalle_habitantes = (TextView)findViewById(R.id.txt_detalle_habitantes);
        txt_detalle_idprovincia = (TextView)findViewById(R.id.txt_detalle_idprovincia);
        img_detalle_ciudad = (ImageView) findViewById(R.id.img_detalle_ciudad);
        Intent intent = getIntent();
        if(intent != null)
        {
            Ciudad c = (Ciudad) intent.getSerializableExtra(EXTRA_OBJETO_CIUDAD);
            txt_detalle_nombrec.setText(c.getNombre());
            txt_detalle_habitantes.setText("habitantes: " + String.valueOf(c.getHabitantes()));
            txt_detalle_idprovincia.setText("idprovincia: " + String.valueOf(c.getIdprovincia()));
            byte [] byteArray  = intent.getByteArrayExtra(CiudadViewHolder.EXTRA_IMAGEN_CIUDAD);
            if(byteArray!=null) {
                img_detalle_ciudad.setImageBitmap(ImagenesBlobBitmap.bytes_to_bitmap(byteArray));
            }
        }
    }
}