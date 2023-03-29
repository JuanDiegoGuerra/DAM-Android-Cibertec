package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorRegistraActivity extends NewAppCompatActivity {

    EditText txtRegProRazSoc, txtRegProruc, txtRegProgerente,
            txtRegProtel, txtRegProdirec, txtRegProFechaFundacion;
    Spinner spnPais;
    Button btnRegistrar;
    ServiceProveedor rest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_registra);

        rest = ConnectionRest.getConnection().create(ServiceProveedor.class);

        txtRegProRazSoc = findViewById(R.id.txtRegProRazSoc);
        txtRegProruc = findViewById(R.id.txtRegProruc);
        txtRegProgerente = findViewById(R.id.txtRegProgerente);
        txtRegProdirec = findViewById(R.id.txtRegProdirec);
        txtRegProtel = findViewById(R.id.txtRegProtel);
        spnPais = findViewById(R.id.spnRegProPais);
        txtRegProFechaFundacion = findViewById(R.id.txtRegProFechaFundacion);
        btnRegistrar = findViewById(R.id.btnRegProEnviar);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                //Se guarda la fecha que se escoge en el datepicker a la caja de texto
                Calendar myCalendar= Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                String myFormat="yyyy-MM-dd";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
                txtRegProFechaFundacion.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        txtRegProFechaFundacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar= Calendar.getInstance();

                //Al Inicio el Date picker empeiza con la fecha Actual
                new DatePickerDialog(ProveedorRegistraActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String razsoc = txtRegProRazSoc.getText().toString();
                String ruc = txtRegProruc.getText().toString();
                String ger = txtRegProgerente.getText().toString();
                String dir = txtRegProdirec.getText().toString();
                String tel = txtRegProtel.getText().toString();
                String pa = spnPais.getSelectedItem().toString();
                String fec = txtRegProFechaFundacion.getText().toString();

                if (!razsoc.matches(ValidacionUtil.RAZSOC)){
                    mensajeAlert("La razon social es de 3 a 300 caracteres");
                }else if (!ruc.matches(ValidacionUtil.RUC)){
                    mensajeAlert("El RUC es de 11 caracteres y sin espacios");
                }else if (!ger.matches(ValidacionUtil.NOMBRE)){
                    mensajeAlert("El gerente es de 2 a 30 caracteres");
                }else if (!dir.matches(ValidacionUtil.DIRECCION)){
                    mensajeAlert("La dirección es de 3 a 300 caracteres");
                }else if (!tel.matches(ValidacionUtil.TELEFONO)){
                    mensajeAlert("El telefono es de 7 a 9 caracteres");
                }else if (spnPais.getSelectedItemPosition() == 0){
                    mensajeAlert("Selecciona un país");
                }else if (!fec.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha tiene formato Año-Mes-Dia");
                }else{
                    Proveedor obj = new Proveedor();
                    obj.setRazonsocial(razsoc);
                    obj.setRuc(ruc);
                    obj.setContacto(ger);
                    obj.setDireccion(dir);
                    obj.setTelefono(tel);
                    obj.setCelular(pa);
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setEstado(FunctionUtil.ESTADO_ACTIVO);

                    registraProveedor(obj);

                }

            }
        });


    }

    public void registraProveedor(Proveedor obj){
        Call<Proveedor> call = rest.registraProveedor(obj);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()){
                    Proveedor obj = response.body();
                    mensajeAlert("Se registró el Proveedor : " + obj.getIdProveedor());
                }else{
                    mensajeAlert("Error de acceso al servicio");
                }
            }

            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeAlert("Error en el servicio Rest :" + t.getMessage());
            }
        });
    }

    }




