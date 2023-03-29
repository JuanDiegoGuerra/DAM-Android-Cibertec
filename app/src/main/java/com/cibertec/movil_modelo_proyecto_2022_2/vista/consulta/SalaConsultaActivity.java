package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.cibertec.movil_modelo_proyecto_2022_2.MainActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.SalaAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSala;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.vista.crud.SalaCrudFormularioActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.vista.crud.SalaCrudListaActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaConsultaActivity extends NewAppCompatActivity {

    EditText txtNumero;
    Button btnFiltrar, btnRegresar;

    ListView lstSala;
    List<Sala> data = new ArrayList<Sala>();
    SalaAdapter adaptador;

    ServiceSala api;

    View recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_consulta);

        //recycler = findViewById(R.id.recycler);

        txtNumero = findViewById(R.id.txtConsSalaNumero);

        lstSala = findViewById(R.id.idConsSalaListView);
        adaptador = new SalaAdapter(this, R.layout.activity_sala_consulta_item, data);
        lstSala.setAdapter(adaptador);


        lstSala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sala objSala =  data.get(i);
                String msg = "📝DATOS DE SALA : \n\n";
                msg += "🆔 ID : " + objSala.getIdSala() + "\n";
                msg += "🔢 NÚMERO : " + objSala.getNumero() + "\n";
                msg += "🏢 PISO : " + objSala.getPiso() + "\n";
                msg += "💻 RECURSOS : " + objSala.getRecursos() + "\n";
                msg += "📅 F.REGISTRO : " + objSala.getFechaRegistro() + "\n";
                msg += "🔒 ESTADO : " + (objSala.getEstado()==1?"Activo":"Inactivo") ;

                mensajeAlert(msg);
            }
        });


        api = ConnectionRest.getConnection().create(ServiceSala.class);

        btnFiltrar = findViewById(R.id.idConsSalaFiltrar);
        btnRegresar = findViewById(R.id.btnConsSalaRegresar);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String filtro = txtNumero.getText().toString();
                if (filtro.matches(ValidacionUtil.SALA_NUM)) {
                    mensajeAlert("Solo se permiten del 1 a 6 caracteres");
                }
                else {
                    consulta(filtro);}
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaConsultaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


    public void consulta(String filtro){
        Call<List<Sala>> call = api.listaSalaPorNumero(filtro);
        call.enqueue(new Callback<List<Sala>>() {
            @Override
            public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                if (response.isSuccessful()) {
                    List<Sala> lstSalida = response.body();
                    mensajeAlert("Resulta " + lstSalida.size() + " elementos");
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Sala>> call, Throwable t) {
                mensajeAlert("ERROR -> Error en el numero de sala" + t.getMessage());
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