package com.cibertec.movil_modelo_proyecto_2022_2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno>  {

    private Context context;
    private List<Alumno> lista;

    public AlumnoAdapter(@NonNull Context context, int resource, @NonNull List<Alumno> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_alumno_consulta_item, parent, false);

        Alumno objAlumno = lista.get(position);

        TextView txtID = row.findViewById(R.id.txtConID);
        txtID.setText(String.valueOf(objAlumno.getIdAlumno()));

        TextView txtNombre = row.findViewById(R.id.txtConNombre);
        txtNombre.setText(objAlumno.getNombres()+" "+objAlumno.getApellidos());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String ruta ;
                    if (objAlumno.getIdAlumno() == 1){
                           ruta = "https://i.postimg.cc/SQ2S9hqH/Mujer.jpg";
                    }else if(objAlumno.getIdAlumno() == 2){
                        ruta ="https://i.postimg.cc/mhf6zMRZ/Hombre.jpg";
                    }else{
                        ruta = "https://i.postimg.cc/zX2CnHvg/no-disponible.png";
                    }

                    URL rutaImagen  = new URL(ruta);
                    InputStream is = new BufferedInputStream(rutaImagen.openStream());
                    Bitmap b = BitmapFactory.decodeStream(is);
                    ImageView vista = row.findViewById(R.id.idAlumnoItemImagen);
                    vista.setImageBitmap(b);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        return row;
    }
}
