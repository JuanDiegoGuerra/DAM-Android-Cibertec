package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.AlumnoCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AlumnoCrudListaActivity extends NewAppCompatActivity {

    ListView lstAlumno;
    ArrayList<Alumno> data = new ArrayList<Alumno>();
    AlumnoCrudAdapter adaptador;

    Button btnFiltrar;
    Button btnRegistrar;
    TextView txtFiltrar;

    ServiceAlumno serviceAlumno;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_crud_lista);

        btnFiltrar = findViewById(R.id.idCrudAlumnoBtnFiltrar);
        btnRegistrar = findViewById(R.id.idCrudAlumnoBtnRegistrar);
        txtFiltrar = findViewById(R.id.idCrudAlumnoTxtFiltrar);

        lstAlumno = findViewById(R.id.idCrudAlumnoListView);
        adaptador = new AlumnoCrudAdapter(this, R.layout.activity_alumno_crud_item, data);
        lstAlumno.setAdapter(adaptador);
        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filtro = txtFiltrar.getText().toString().trim();
                listaPorNombre(filtro);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlumnoCrudListaActivity.this, AlumnoCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "REGISTRAR");

                startActivity(intent);
            }
        });

        lstAlumno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Alumno objAlumno = data.get(i);

                Intent intent = new Intent(AlumnoCrudListaActivity.this, AlumnoCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "ACTUALIZAR");
                intent.putExtra("var_item", objAlumno);

                startActivity(intent);
            }
        });

        listaPorNombre("");
    }

    public void listaPorNombre(String filtro){
        Call<List<Alumno>> call = serviceAlumno.listaAlumnoPorNombre(filtro);
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if (response.isSuccessful()){
                    List<Alumno> lstSalida = response.body();

                    Collections.sort(lstSalida, new Comparator<Alumno>() {
                        @Override
                        public int compare(Alumno t0, Alumno t1) {
                            return t0.getNombres().toLowerCase().compareTo(t1.getNombres().toLowerCase());
                        }
                    });

                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {

            }
        });


    }





}