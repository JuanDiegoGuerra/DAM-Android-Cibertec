package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSala;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaCrudFormularioActivity extends NewAppCompatActivity {

    //Connection and Components

    ServiceSala api;

    TextView txtTitulo, txtTextoEstado;

    EditText txtNumero, txtCapacidad, txtRecursos, txtFecha;

    Button btnEnviar, btnEliminar, btnRegresar;

    Spinner spnPiso, spnEstado; //spnSede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_crud_formulario);

        txtTitulo = findViewById(R.id.txtCrudRegistraSalaTitle);
        txtNumero = findViewById(R.id.txtCrudRegistraSalaNumero);
        txtCapacidad = findViewById(R.id.txtCrudRegistraSalaCapacidad);
        txtRecursos = findViewById(R.id.txtCrudRegistraSalaRecursos);
        txtFecha = findViewById(R.id.txtCrudRegistraSalaFechaSep);
        btnEnviar = findViewById(R.id.btnCrudSalaRegistrar);
        spnPiso = findViewById(R.id.spnCrudRegistraSalaPiso);
        //spnSede = findViewById(R.id.spnRegSalaSede);
        spnEstado = findViewById(R.id.spnCrudRegistraSalaEstado);
        btnEliminar = findViewById(R.id.btnCrudSalaEliminar);
        txtTextoEstado = findViewById(R.id.txtCrudRegistraSalaTextoEstado);
        btnRegresar = findViewById(R.id.btnCrudSalaRegresar);

        api = ConnectionRest.getConnection().create(ServiceSala.class);

        Bundle extras = getIntent().getExtras();
        String tipo = extras.getString("var_tipo");

        //REG AND UPDATE
        if ("REGISTRAR".equals(tipo)) {
            txtTitulo.setText("Mantenimiento Sala - Registrar");
            btnEnviar.setText("Registrar ✅");
            txtTextoEstado.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.GONE);
            spnEstado.setVisibility(View.GONE);
        } else if ("ACTUALIZAR".equals(tipo)) {
            txtTitulo.setText("Mantenimiento Sala - Actualizar");
            btnEnviar.setText("Actualizar 🔄");
            txtTextoEstado.setVisibility(View.VISIBLE);
            btnEliminar.setVisibility(View.VISIBLE);
            spnEstado.setVisibility(View.VISIBLE);

            Sala objSala = (Sala) extras.get("var_item");
            txtNumero.setText(objSala.getNumero());
            txtCapacidad.setText(Integer.toString(objSala.getCapacidad()));
            txtRecursos.setText(objSala.getRecursos());
            txtFecha.setText(objSala.getFechaSeparacion());

            int pos = FunctionUtil.getIndex(spnPiso, Integer.toString(objSala.getPiso()));
            spnPiso.setSelection(pos);
            /*int poss = FunctionUtil.getIndex(spnSede, Integer.toString(objSala.getSede()));
            spnSede.setSelection(poss);*/

            spnEstado.setSelection(objSala.getEstado() == 1 ? 1 : 2);


        }

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaCrudFormularioActivity.this, SalaCrudListaActivity.class);
                startActivity(intent);
            }
        });



        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = txtNumero.getText().toString();
                String cap = txtCapacidad.getText().toString();
                String rec = txtRecursos.getText().toString();
                String fec = txtFecha.getText().toString();
                String piso = spnPiso.getSelectedItem().toString();
                //String sede = spnSede.getSelectedItem().toString();

                if (!num.matches(ValidacionUtil.NUMERO)) {
                    mensajeAlert("NumLetraNumNumNuM 5 caracteres, ejm:2A578");
                } else if (!cap.matches(ValidacionUtil.CAPACIDAD)) {
                    mensajeAlert("Capacidad: Solo se admiten números");
                } else if (!rec.matches(ValidacionUtil.RECURSOS)) {
                    mensajeAlert("Recursos: De 3 a 30 caracteres");
                } else if (!fec.matches(ValidacionUtil.FECHA)) {
                    mensajeAlert("La Fecha es yyyy-MM-dd");
                } else if (spnPiso.getSelectedItemPosition() == 0) {
                    mensajeAlert("Seleccione un Piso");
                } /* if (spnSede.getSelectedItemPosition() == 0) {
                    mensajeAlert("Seleccione una Sede");
                } */else {
                    if ("REGISTRAR".equals(tipo)) {
                        Sala obj = new Sala();

                        obj.setNumero(num);
                        obj.setCapacidad(Integer.parseInt(cap));
                        obj.setRecursos(rec);
                        obj.setFechaSeparacion((fec));
                        obj.setPiso(Integer.parseInt(piso));
                        //obj.setSede(Integer.parseInt(sede));
                        obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                        obj.setEstado(FunctionUtil.ESTADO_ACTIVO);

                        inserta(obj);
                    } else if ("ACTUALIZAR".equals(tipo)) {
                        Sala obj = (Sala) extras.get("var_item");

                        obj.setNumero(num);
                        obj.setCapacidad(Integer.parseInt(cap));
                        obj.setRecursos(rec);
                        obj.setFechaSeparacion((fec));
                        obj.setPiso(Integer.parseInt(piso));
                        //obj.setSede(Integer.parseInt(sede));
                        obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());

                        int posEstado = spnEstado.getSelectedItemPosition();
                        obj.setEstado(posEstado == 1 ? 1 : 0);
                        actualiza(obj);
                    }
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sala objSala = (Sala) extras.get("var_item");
                elimina(objSala.getIdSala());
            }
        });
    }

    public void inserta(Sala obj) {
        Call<Sala> call = api.insertaSala(obj);
        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if (response.isSuccessful()) {
                    Sala objSalida = response.body();
                    if (objSalida == null) {
                        mensajeToastLong("ERROR -> No se insertó");
                    } else {
                        mensajeToastLong("ÉXITO -> Se insertó correctamente : " + objSalida.getIdSala());
                    }
                } else {
                    mensajeToastLong("ERROR -> Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<Sala> call, Throwable t) {
                mensajeToastLong("ERROR");
            }
        });
    }

    public void actualiza(Sala obj) {
        Call<Sala> call = api.actualizaSala(obj);
        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if (response.isSuccessful()) {
                    Sala objSalida = response.body();
                    if (objSalida == null) {
                        mensajeToastLong("ERROR -> No se actualizó");
                    } else {
                        mensajeToastLong("ÉXITO -> Se actualizó correctamente");
                    }
                }
            }

            @Override
            public void onFailure(Call<Sala> call, Throwable t) {
                mensajeToastLong("ERROR");
            }
        });
    }

    public void elimina(int id) {
        Call<Sala> call = api.eliminaSala(id);
        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if (response.isSuccessful()) {
                    Sala objSalida = response.body();
                    if (objSalida == null) {
                        mensajeToastLong("ERROR -> NO se eliminó");
                    } else {
                        mensajeToastLong("ÉXITO -> Se eliminó correctamente : " + objSalida.getIdSala());
                    }
                } else {
                    mensajeToastLong("ERROR -> Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<Sala> call, Throwable t) {
                mensajeToastLong("ERROR -> " + t.getMessage());
            }
        });
    }
}