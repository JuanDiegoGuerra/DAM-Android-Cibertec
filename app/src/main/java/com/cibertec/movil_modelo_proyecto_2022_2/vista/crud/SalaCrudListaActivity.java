package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.SalaAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSala;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SalaCrudListaActivity extends NewAppCompatActivity {

    //Connection - LSview & Adapter
    ServiceSala api;
    EditText txtNumero;
    Button btnFiltrar, btnRegistra;
    ListView lstSala;
    List<Sala> data = new ArrayList<>();
    SalaAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_crud_lista);

        txtNumero = findViewById(R.id.txtCrudSalaNumero);
        btnFiltrar = findViewById(R.id.btnCrudSalaRegistrar);
        btnRegistra = findViewById(R.id.btnCrudSalaRegistrar);

        lstSala = findViewById(R.id.lstCrudSala);
        adaptador = new SalaAdapter(this, R.layout.activity_sala_crud_lista, data);
        lstSala.setAdapter(adaptador);

        api = ConnectionRest.getConnection().create(ServiceSala.class);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filtro = txtNumero.getText().toString();
                filtraPorNumero(filtro);
            }
        });
        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //form in Register Mode
                Intent intent = new Intent(SalaCrudListaActivity.this, SalaCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "REGISTRAR");
                startActivity(intent);
            }
        });

        lstSala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Get "Sala" data and Show Register Mode
                //MessageAlert >> ("En actualizar " + i); <<
                Sala obj = data.get(i);
                Intent intent = new Intent(SalaCrudListaActivity.this, SalaCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "ACTUALIZAR");
                intent.putExtra("var_item", obj);
                startActivity(intent);
            }
        });
        //Show data at start
        filtraPorNumero("");
    }

    public void filtraPorNumero(String filtro){
        Call<List<Sala>> call = api.listaSalaPorNumero(filtro);
        call.enqueue(new Callback<List<Sala>>() {
            @Override
            public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                if (response.isSuccessful()){
                    List<Sala> salida = response.body();
                    Collections.sort(salida, new Comparator<Sala>() {
                        @Override
                        public int compare(Sala s1, Sala s2) {
                            return s1.getNumero().toLowerCase().compareTo(s2.getNumero().toLowerCase());
                        }
                    });
                    data.clear();;
                    data.addAll(salida);
                    adaptador.notifyDataSetChanged();
                }

            }
            @Override
            public void onFailure(Call<List<Sala>> call, Throwable t) {
                mensajeAlert("ERROR" + t.getMessage());
            }
        });
    }
}