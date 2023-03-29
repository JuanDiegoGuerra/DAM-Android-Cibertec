package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;


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

public class RevistaRegistraActivity extends NewAppCompatActivity {

    Spinner  spnModalidad;
    ArrayAdapter<String> adaptador;
    ArrayList<String> modalidades = new ArrayList<String>();

    // call service
    ServiceRevista serviceRevista;
    ServiceModalidad serviceModalidad;

    Button btnRegistrar;
    EditText txtnombre, txtfechaCreacion, txtfrecuencia, txtfechaRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revista_registra);

        /*serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);
        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        //Adaptador
        adaptador = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,categorias);
        spnModalidad = findViewById(R.id.spnModalidad);
        spnModalidad.setAdapter(adaptador);

        txtnombre = findViewById(R.id.txtnombre);
        txtfechaCreacion = findViewById(R.id.txtfechaCreacion);
        txtfrecuencia = findViewById(R.id.txtfrecuencia);
        txtfechaRegistro = findViewById(R.id.txtfechaRegistro);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String txtnombre = txtnombre.getText().toString();
            String txtfechaCreacion = txtfechaCreacion.getText().toString();
            String txtfechaRegistro = txtfechaRegistro.getText().toString();
            String txtfrecuencia = txtfrecuencia.getText().toString();

            if (!txtnombre.matches(ValidacionUtil.nombre)){
                mensajeToast("El nombre es requerido");
            }else if(!txtfechaCreacion.matches(ValidacionUtil.fechaCreacion)){
                mensajeToast("La fecha de creacion es requerido");
            }else if(!txtfechaRegistro.matches(ValidacionUtil.fechaRegistro)){
                mensajeToast("La fecha de creacion es requerido");
            }
            else if(!txtfrecuencia.matches(ValidacionUtil.frecuencia)){
                mensajeToast("La frecuencia es requerido");
            }else{
                String modalidades = spnModalidades.getSelectedItem().toString();
                String idModalidades = modalidades.split(":")[0];
                Modalidad objModalidades = new Modalidad();
                objModalidades.setIdModalidad(Integer.parseInt(idModalidades));

                Revista objRevista = new Revista();
                objRevista.setNombre(txtnombre);
                objRevista.setFechaCreacion(FunctionUtil.getFechaActualStringDateTime());
                objRevista.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objRevista.setFrecuencia(txtfrecuencia);
                objRevista.setEstado(1);
                objRevista.setModalidad(objModalidades);

                registrarRevista(objRevista);
            }
        }
    });
    */
    }


    /*public void registrarRevista(Revista objRevista){
        Call<Revista> call = serviceRevista.registrarRevista(objRevista);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()){
                    Revista objRevista = response.body();
                    mensajeAlert(" Registro existoso >>> ID >> " + objRevista.getIdRevista() + " >>> Nombre >>> " + objRevista.getNombre());
                }else{
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensajeToast("Error al acceder al Serivico Rest >>> " + t.getMessage());
            }
        });
    }*/


    /*public void modalidad(){
        Call<List<Modalidad>> call = serviceModalidad.listaTodos();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()){
                    List<Modalidad> lstmodalidad = response.body();
                    for(Modalidad obj:lstmodalidad){
                        modalidades.add(obj.getIdModalidad());
                    }
                    adaptador.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeToast(" Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }*/

    /*public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }*/

}