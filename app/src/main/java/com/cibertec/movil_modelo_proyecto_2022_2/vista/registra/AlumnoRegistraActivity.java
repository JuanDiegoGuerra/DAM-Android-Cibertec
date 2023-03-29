package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoRegistraActivity extends NewAppCompatActivity {

    private Spinner spnPais;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> paises = new ArrayList<String>();

    //Servicios
    private ServiceAlumno serviceAlumno;
    private ServicePais servicePais;

    //Componentes del formulario
    Button btnRegistrar;
    EditText txtNombres, txtApellidos, txtTelefono, txtDni, txtCorreo, txtFechaNacimiento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_registra);

        //Para conectar al servicio REST
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);

        //Para el adapatador
        adaptador = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnRegEdiPais);
        spnPais.setAdapter(adaptador);


        txtNombres = findViewById(R.id.txtRegEdiNombres);
        txtApellidos = findViewById(R.id.txtRegEdiApellidos);
        txtTelefono = findViewById(R.id.txtRegEdiTelefono);
        txtDni = findViewById(R.id.txtRegEdiDNI);
        txtCorreo = findViewById(R.id.txtRegEdiCorreo);
        txtFechaNacimiento = findViewById(R.id.txtRegEdiFechaNacimiento);
        btnRegistrar = findViewById(R.id.btnRegEdiEnviar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todos los datos los recibimos como String
                String nombres = txtNombres.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String dni= txtDni.getText().toString();
                String correo= txtCorreo.getText().toString();
                String fechaNacimiento = txtFechaNacimiento.getText().toString();

                if (!nombres.matches(ValidacionUtil.NOMBRE)){
                    mensajeToast("Los nombres son de 3 a 30 caracteres");
                }else if (!apellidos.matches(ValidacionUtil.APELLIDO)){
                    mensajeToast("Los apellidos son de 3 a 30 caracteres");
                }else if (!telefono.matches(ValidacionUtil.TELEFONO)){
                    mensajeToast("El telefono es de 9 dígitos");
                }else if (!dni.matches(ValidacionUtil.DNI)){
                    mensajeToast("El DNI es de 8 dígitos");
                }else if (!correo.matches(ValidacionUtil.CORREO)){
                    mensajeToast("El correo es de la forma expample@com");
                }else if (!fechaNacimiento.matches(ValidacionUtil.FECHA)){
                    mensajeToast("La fecha de nacimiento es YYYY-MM-dd");
                }else{
                    String pais = spnPais.getSelectedItem().toString();
                    String idPais = pais.split(":")[0];
                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));

                    Alumno objAlumno= new Alumno();

                    objAlumno.setNombres(nombres);
                    objAlumno.setApellidos(apellidos);
                    objAlumno.setTelefono(telefono);
                    objAlumno.setDni(dni);
                    objAlumno.setCorreo(correo);
                    objAlumno.setFechaNacimiento(fechaNacimiento);
                    objAlumno.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objAlumno.setEstado(1);
                    objAlumno.setPais(objPais);

                    registrarAlumno(objAlumno);
                }

            }
        });
        cargaPais();
    }

    public  void registrarAlumno(Alumno objAlumno){
            Call<Alumno> call= serviceAlumno.insertaAlumno(objAlumno);
            call.enqueue(new Callback<Alumno>() {
                @Override
                public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                    if (response.isSuccessful()){
                        Alumno objSalida = response.body();
                        mensajeAlert(" Registro exitoso "+"\nID >> " + objSalida.getIdAlumno()
                                + "\nAlumno >>> " +  objSalida.getNombres()+ " "+ objSalida.getApellidos());
                    }else{
                        mensajeAlert(response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Alumno> call, Throwable t) {
                    mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
                }
            });

    }


    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lstPaises =  response.body();
                    for(Pais obj: lstPaises){
                        paises.add(obj.getIdPais() +":"+ obj.getNombre());
                    }
                    adaptador.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
}