package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.LibroAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.LibroCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroCrudListaActivity extends NewAppCompatActivity {

    Button btnFiltrar;
    Button btnRegistrar;
    ListView lstLibro;
    List<Libro> data = new ArrayList<Libro>();
    LibroCrudAdapter adaptador;
    TextView txtFiltrar;

    ServiceLibro serviceLibro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_crud_lista);

        btnFiltrar = findViewById(R.id.idCrudLibroBtnFiltrar);
        btnRegistrar = findViewById(R.id.idCrudLibroBtnRegistrar);
        txtFiltrar = findViewById(R.id.idCrudLibroTxtFiltrar);

        lstLibro = findViewById(R.id.idCrudLibroListView);
        adaptador = new LibroCrudAdapter(this,R.layout.activity_libro_crud_item,data);
        lstLibro.setAdapter(adaptador);

        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String filtro = txtFiltrar.getText().toString().trim();
                listaPorTitulo(filtro);

            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibroCrudListaActivity.this,LibroCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "REGISTRAR");
                startActivity(intent);
            }
        });

        lstLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Libro olibro = data.get(i);
                Intent intent = new Intent(LibroCrudListaActivity.this,LibroCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "ACTUALIZAR");
                intent.putExtra("var_item", olibro);
                startActivity(intent);
            }
        });

        listaPorTitulo("");



    }

    public void listaPorTitulo(String filtro){

        Call<List<Libro>> call = serviceLibro.listaLibroPorTitulo(filtro);
        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if(response.isSuccessful()){
                    List<Libro> lstSalida = response.body();
                    Collections.sort(lstSalida, new Comparator<Libro>() {
                        @Override
                        public int compare(Libro t0, Libro t1) {
                            return t0.getTitulo().toLowerCase().compareTo(t1.getTitulo().toLowerCase(Locale.ROOT));
                        }
                    });
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {

            }
        });

    }

}