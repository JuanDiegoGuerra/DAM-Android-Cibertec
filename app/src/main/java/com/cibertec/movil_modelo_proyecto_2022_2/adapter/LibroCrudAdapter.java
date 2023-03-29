package com.cibertec.movil_modelo_proyecto_2022_2.adapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;

import java.util.List;

public class LibroCrudAdapter extends ArrayAdapter<Libro> {

    private Context context;
    private List<Libro> lista;

    public LibroCrudAdapter(@NonNull Context context, int resource, @NonNull List<Libro> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_libro_crud_item,parent,false);

        Libro obj = lista.get(position);


        TextView txtID = row.findViewById(R.id.idCrudLibroItemID);
        txtID.setText(String.valueOf(obj.getIdLibro()));

        TextView titulo = row.findViewById(R.id.idCrudLibroItemTitulo);
        titulo.setText(obj.getTitulo());

        if (position % 2 == 0) {
            titulo.setBackgroundColor(Color.rgb(47, 49, 54));
            titulo.setTextColor(Color.rgb(248, 250, 255));
            txtID.setBackgroundColor(Color.rgb(70, 196, 110));
            txtID.setTextColor(Color.rgb(248, 250, 255));
        }

        return row;
    }


}
