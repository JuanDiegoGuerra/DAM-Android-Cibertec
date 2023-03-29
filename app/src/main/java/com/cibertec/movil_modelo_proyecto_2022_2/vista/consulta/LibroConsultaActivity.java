package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.LibroAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroConsultaActivity extends NewAppCompatActivity {

    Button btnFiltrar;
    ListView lstLibro;
    List<Libro> data = new ArrayList<>();
    LibroAdapter adaptador;
    ServiceLibro serviceLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_consulta);
        btnFiltrar = findViewById(R.id.btnConLibro);

        lstLibro = findViewById(R.id.lstConLibro);
        adaptador = new LibroAdapter(this,R.layout.activity_libro_consulta_item,data);
        lstLibro.setAdapter(adaptador);

        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lista();
            }
        });
    }
    public  void lista(){
        Call<List<Libro>> call =  serviceLibro.listaLibro();
        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {

                if(response.isSuccessful()){
                    List<Libro> lstSalida = response.body();
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {
                mensajeToastLong("Error al acceder al servicio Res "+ t.getMessage());
            }
        });
    }

}