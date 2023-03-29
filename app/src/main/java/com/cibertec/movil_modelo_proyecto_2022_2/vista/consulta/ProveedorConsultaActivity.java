package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.ProveedorAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;



public class ProveedorConsultaActivity extends NewAppCompatActivity {

    EditText txtRazon;
    Button btnFiltrar;

    ListView lstProveedor;
    List<Proveedor> data = new ArrayList<>();
    ProveedorAdapter adaptador;

    ServiceProveedor api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_consulta);


        txtRazon = findViewById(R.id.txtConsProvRazon);

        lstProveedor = findViewById(R.id.lstConsultaProveedor);
        adaptador = new ProveedorAdapter(this, R.layout.activity_proveedor_crud_item, data);
        lstProveedor.setAdapter(adaptador);

        api = ConnectionRest.getConnection().create(ServiceProveedor.class);

        btnFiltrar = findViewById(R.id.btnConsProvFiltrar);


        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filtro = txtRazon.getText().toString();
                consulta(filtro);
            }
        });
    }

    public void consulta(String filtro){
        Call<List<Proveedor>> call = api.consulta(filtro);
        call.enqueue(new Callback<List<Proveedor>>() {
            @Override
            public void onResponse(Call<List<Proveedor>> call, Response<List<Proveedor>> response) {
                if (response.isSuccessful()) {
                    List<Proveedor> lstSalida = response.body();
                    mensajeAlert("Resulta " + lstSalida.size() + " elementos");
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Proveedor>> call, Throwable t) {
                mensajeAlert("ERROR -> Error en la respuesta" + t.getMessage());
            }
        });
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

}
