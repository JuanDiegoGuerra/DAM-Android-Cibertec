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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class LibroAdapter extends ArrayAdapter<Libro>  {

    private Context context;
    private List<Libro> lista;

    public LibroAdapter(@NonNull Context context, int resource, @NonNull List<Libro> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_libro_consulta_item, parent, false);

        Libro obj = lista.get(position);


        TextView txtID = row.findViewById(R.id.idLibroItemId);
        txtID.setText(String.valueOf(obj.getIdLibro()));

        TextView titulo = row.findViewById(R.id.idLibTitulo);
        titulo.setText(obj.getTitulo());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String ruta;

                    if(obj.getIdLibro() == 1){
                        ruta = "https://i.postimg.cc/bNz1KbDx/trilce-primera-edicion.jpg";
                    }
                    else if (obj.getIdLibro() == 2){
                        ruta = "https://i.postimg.cc/cCcwv1jt/Fuenteovejuna.jpg";
                    }
                    else if (obj.getIdLibro() == 3){
                        ruta = "https://i.postimg.cc/7h4SPJBy/la-ciudad-y-los-perros.jpg";
                    }else{
                        ruta = "https://i.postimg.cc/FRd9MY5r/portadas-libros-big-2019101610.jpg";
                    }

                    URL rutaImagen = new URL(ruta);
                    InputStream is = new BufferedInputStream(rutaImagen.openStream());
                    Bitmap b = BitmapFactory.decodeStream(is);
                    ImageView vista = row.findViewById(R.id.idLibroImagen);
                    vista.setImageBitmap(b);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


        return row;
    }
}
