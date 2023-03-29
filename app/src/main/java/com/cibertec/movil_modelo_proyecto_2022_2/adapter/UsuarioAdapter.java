package com.cibertec.movil_modelo_proyecto_2022_2.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Usuario;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<Usuario>  {

    private Context context;
    private List<Usuario> lista;

    public UsuarioAdapter(@NonNull Context context, int resource, @NonNull List<Usuario> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

}
