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
import androidx.recyclerview.widget.RecyclerView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class SalaAdapter extends ArrayAdapter<Sala>  {

    private Context context;
    private List<Sala> lista;


    public SalaAdapter(@NonNull Context context, int resource, @NonNull List<Sala> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_sala_consulta_item, parent, false);

        Sala obj = lista.get(position);

        TextView txtID = row.findViewById(R.id.idItemIdSala);
        txtID.setText(String.valueOf(obj.getIdSala()));

        TextView  txtNumero = row.findViewById(R.id.idItemNumeroSala);
        txtNumero.setText(String.valueOf(obj.getNumero()));

        TextView  txtRecursos = row.findViewById(R.id.idItemRecursoSala);
        txtRecursos.setText(String.valueOf(obj.getRecursos()));


        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String ruta ;
                    if (obj.getIdSala()==1){
                        ruta = "https://i.postimg.cc/ZqbxPYW4/cubiculo2.jpg";
                    } else if (obj.getIdSala()==2){
                        ruta = "https://i.postimg.cc/9QtBqwFL/laboratoriopcs.jpg";
                    } else if (obj.getIdSala()==3){
                        ruta = "https://i.postimg.cc/GmLM2J1h/Sala-Cinema-Games.jpg";
                    }else if (obj.getIdSala()==152){
                        ruta = "https://i.postimg.cc/BZ2NBwSn/salareuniones.jpg";
                    } else if (obj.getIdSala()==153){
                        ruta = "https://i.postimg.cc/GmLM2J1h/Sala-Cinema-Games.jpg";
                    } else if (obj.getIdSala()==154){
                        ruta = "https://i.postimg.cc/ZqbxPYW4/cubiculo2.jpg";
                    } else if (obj.getIdSala()==160){
                        ruta = "https://i.postimg.cc/BZ2NBwSn/salareuniones.jpg";
                    } else if (obj.getIdSala()==165){
                        ruta = "https://i.postimg.cc/9QtBqwFL/laboratoriopcs.jpg";
                    } else if (obj.getIdSala()==170){
                        ruta = "https://i.postimg.cc/GmLM2J1h/Sala-Cinema-Games.jpg";
                    } else if (obj.getIdSala()==199){
                        ruta = "https://i.postimg.cc/GmLM2J1h/Sala-Cinema-Games.jpg";
                    } else if (obj.getIdSala()==205){
                        ruta = "https://i.postimg.cc/GmLM2J1h/Sala-Cinema-Games.jpg";
                    } else {
                        ruta = "https://i.postimg.cc/YqSmpQzk/no-disponible.png";
                    }
                    URL rutaImagen  = new URL(ruta);
                    InputStream is = new BufferedInputStream(rutaImagen.openStream());
                    Bitmap b = BitmapFactory.decodeStream(is);
                    ImageView vista = row.findViewById(R.id.idSalaItemImg);
                    vista.setImageBitmap(b);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        return row;
    }
}
