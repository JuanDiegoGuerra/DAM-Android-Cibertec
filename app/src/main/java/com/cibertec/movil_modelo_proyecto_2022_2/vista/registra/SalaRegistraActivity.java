package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.movil_modelo_proyecto_2022_2.MainActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSala;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta.SalaConsultaActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.vista.crud.SalaCrudFormularioActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.vista.crud.SalaCrudListaActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaRegistraActivity extends NewAppCompatActivity {

    //Connection and Components

    ServiceSala service;

    EditText txtNumero, txtCapacidad, txtRecursos, txtFechaSeparacion;

    Button btnEnviar, btnRegresar;            Spinner spnPiso; //spnSede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sala_registra);

        txtNumero = findViewById(R.id.txtRegSalaNumero);
        txtCapacidad = findViewById(R.id.txtRegSalaCapa);
        txtRecursos = findViewById(R.id.txtRegSalaRec);
        txtFechaSeparacion = findViewById(R.id.txtRegSalaFechaSep);
        spnPiso = findViewById(R.id.spnRegSalaPiso);
        //spnSede = findViewById(R.id.spnRegSalaSede);
        btnEnviar = findViewById(R.id.btnRegSalaEnviar);
        btnRegresar = findViewById(R.id.btnRegSalaRegresar);

        service = ConnectionRest.getConnection().create(ServiceSala.class);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = txtNumero.getText().toString();
                String cap = txtCapacidad.getText().toString();
                String rec = txtRecursos.getText().toString();
                String fecsep = txtFechaSeparacion.getText().toString();
                String piso = spnPiso.getSelectedItem().toString();
                //String sede = spnSede.getSelectedItem().toString();

                if (!num.matches(ValidacionUtil.NUMERO)) {
                    mensajeAlert("NumLetraNumNumNuM 5 caracteres, ejm:2A578");
                } else if (!cap.matches(ValidacionUtil.CAPACIDAD)) {
                    mensajeAlert("Capacidad: Solo se admiten números");
                } else if (!rec.matches(ValidacionUtil.RECURSOS)) {
                    mensajeAlert("Recursos: De 3 a 30 caracteres");
                } else if (!fecsep.matches(ValidacionUtil.FECHA)) {
                    mensajeAlert("La fecha es yyyy-MM-dd");
                } else if (spnPiso.getSelectedItemPosition() == 0) {
                    mensajeAlert("Seleccione un Piso");
                } /*else if (spnSede.getSelectedItemPosition() == 0) {
                    mensajeAlert("Seleccione una Sede");
                }*/ else {
                    Sala obj = new Sala();
                    obj.setNumero(num);
                    obj.setCapacidad(Integer.parseInt(cap));
                    obj.setRecursos(rec);
                    obj.setFechaSeparacion((fecsep));
                    obj.setPiso(Integer.parseInt(piso));
                    //obj.setSede(1);
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setEstado(1);
                    //Register Inserted!
                    insertaSala(obj);
                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaRegistraActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void insertaSala(Sala obj) {
        Call<Sala> call = service.insertaSala(obj);
        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if (response.isSuccessful()) {
                    Sala obj = response.body();
                    mensajeAlert("Se registró la sala => " + obj.getIdSala() + " - " + obj.getNumero());
                }
            }

            @Override
            public void onFailure(Call<Sala> call, Throwable t) {
                mensajeAlert("Error >>" + t.getMessage());
            }
        });
    }
}