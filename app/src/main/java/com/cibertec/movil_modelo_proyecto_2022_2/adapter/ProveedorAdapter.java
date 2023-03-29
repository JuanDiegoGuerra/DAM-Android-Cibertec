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
import com.cibertec.movil_modelo_proyecto_2022_2.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ProveedorAdapter extends ArrayAdapter<Proveedor>  {

    private Context context;
    private List<Proveedor> lista;

    public ProveedorAdapter(@NonNull Context context, int resource, @NonNull List<Proveedor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_proveedor_crud_item, parent, false);

        final Proveedor obj = lista.get(position);

        TextView txtID = row.findViewById(R.id.idItemProRazSoc);
        txtID.setText(String.valueOf(obj.getIdProveedor()));

        TextView  txtRazon = row.findViewById(R.id.idItemProRazSoc);
        txtRazon.setText(String.valueOf(obj.getRazonsocial()));

        TextView  txtPais = row.findViewById(R.id.idItemPaisProveedor);
        txtPais.setText(String.valueOf(obj.getPais()));

        return row;

    }

}
