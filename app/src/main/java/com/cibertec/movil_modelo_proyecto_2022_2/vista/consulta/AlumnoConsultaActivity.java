package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.AlumnoAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoConsultaActivity extends NewAppCompatActivity {

    Button btnFiltrar;

    ListView lstAlumno;
    ArrayList<Alumno> data = new ArrayList<Alumno>();
    AlumnoAdapter adaptador;

    ServiceAlumno serviceAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alumno_consulta);

        btnFiltrar = findViewById(R.id.btnConAlumEnviar);

        lstAlumno = findViewById(R.id.lstConAlum);
        adaptador = new AlumnoAdapter(this, R.layout.activity_alumno_consulta_item, data);
        lstAlumno.setAdapter(adaptador);
        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lista();
            }
        });
    }

    public void lista(){

        Call<List<Alumno>> call = serviceAlumno.listaAlumno();
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {

                if (response.isSuccessful()){
                    List<Alumno> lstSalida = response.body();
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {
                mensajeToastLong("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

}