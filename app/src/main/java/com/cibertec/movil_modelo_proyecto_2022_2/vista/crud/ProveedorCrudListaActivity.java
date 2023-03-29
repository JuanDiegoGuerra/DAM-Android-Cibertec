package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.ProveedorAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;


public class ProveedorCrudListaActivity extends NewAppCompatActivity {


    EditText txtProveedor;
    Button btnFiltrar, btnRegistra;
    //Listview y Adaptador
    ListView lstProveedor;
    List<Proveedor> data = new ArrayList<Proveedor>();
    ProveedorAdapter adaptador;

    //COnexion
    ServiceProveedor api;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_crud_lista);

        txtProveedor = findViewById(R.id.txtCrudProvNombre);
        btnFiltrar = findViewById(R.id.btnCrudProvFiltrar);
        btnRegistra = findViewById(R.id.btnCrudProvRegistrar);
        //construir el listview y adaptador
        lstProveedor = findViewById(R.id.lstCrudProveedor);
        adaptador = new ProveedorAdapter(this,R.layout.activity_proveedor_crud_item, data);
        lstProveedor.setAdapter(adaptador);

        api = ConnectionRest.getConnection().create(ServiceProveedor.class);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filtro = txtProveedor.getText().toString();
                consulta(filtro);
            }
        });

        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mensajeAlert("En Registrar ");

                Intent intent = new Intent(ProveedorCrudListaActivity.this,ProveedorCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "REGISTRAR");
                startActivity(intent);


            }
        });

        lstProveedor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //mensajeAlert("En Actualizar  " + i);

                //Se obtiene los datos del Proveedor seleccionado
                Proveedor obj = data.get(i);

                Intent intent = new Intent(ProveedorCrudListaActivity.this,ProveedorCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "ACTUALIZAR");
                intent.putExtra("var_item", obj);
                startActivity(intent);
            }
        });

    }


    public void consulta(String filtro){
        /*Call<List<Proveedor>> call = api.listarProveedorPorRazonSocial(filtro);
        call.enqueue(new Callback<List<Proveedor>>() {
            @Override
            public void onResponse(Call<List<Proveedor>> call, Response<List<Proveedor>> response) {
                List<Proveedor> lstProveedor = response.body();
                mensajeToastLong("Llegaron " + lstProveedor.size() + " elementos ");
                data.clear();
                data.addAll(lstProveedor);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Proveedor>> call, Throwable t) {
                mensajeToastLong("ERROR -> Error en la respuesta" + t.getMessage());
            }
        });*/
    }






}