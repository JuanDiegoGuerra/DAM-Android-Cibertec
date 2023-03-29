package com.cibertec.movil_modelo_proyecto_2022_2.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;

import java.util.List;

public class AutorAdapter extends ArrayAdapter<Autor>  {

    private Context context;
    private List<Autor> lista;

    public AutorAdapter(@NonNull Context context, int resource, @NonNull List<Autor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

}
