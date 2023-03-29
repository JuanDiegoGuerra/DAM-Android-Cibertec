package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Locale;

public class AlumnoCrudFormularioActivity extends NewAppCompatActivity {

    TextView txtTitulo;
    Button btnEnviar, btnRegresar;


    private Spinner spnPais;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> paises = new ArrayList<String>();

    //Servicios
    private ServiceAlumno serviceAlumno;
    private ServicePais servicePais;

    //Componentes del formulario
    EditText txtNombres, txtApellidos, txtTelefono, txtDni, txtCorreo, txtFechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_crud_formulario);

        //Para conectar al servicio REST
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);

        //Para el adapatador
        adaptador = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.idCrudAlumnoFrmPais);
        spnPais.setAdapter(adaptador);

        cargaPais();

        txtNombres = findViewById(R.id.idCrudAlumnoFrmNombres);
        txtApellidos = findViewById(R.id.idCrudAlumnoFrmApellidos);
        txtTelefono = findViewById(R.id.idCrudAlumnoFrmTelefono);
        txtDni = findViewById(R.id.idCrudAlumnoFrmDNI);
        txtCorreo = findViewById(R.id.idCrudAlumnoFrmCorreo);
        txtFechaNacimiento = findViewById(R.id.idCrudAlumnoFrmFechaNacimiento);


        txtTitulo = findViewById(R.id.idCrudAlumnoFrmTitulo);
        btnEnviar = findViewById(R.id.idCrudAlumnoFrmBtnRegistrar);
        btnRegresar = findViewById(R.id.idCrudAlumnoFrmBtnRegresar);

        txtFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(AlumnoCrudFormularioActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, day);
                        txtFechaNacimiento.setText(dateFormat.format(myCalendar.getTime()));
                    }
                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Bundle extras = getIntent().getExtras();
        String tipo = extras.getString("var_tipo");

        if (tipo.equals("REGISTRAR")) {
            txtTitulo.setText("Mantenimiento Alumno - REGISTRA");
            btnEnviar.setText("REGISTRA");
        } else if (tipo.equals("ACTUALIZAR")) {
            txtTitulo.setText("Mantenimiento Alumno - ACTUALIZA");
            btnEnviar.setText("ACTUALIZA");

            Alumno obj = (Alumno) extras.get("var_item");

            txtNombres.setText(obj.getNombres());
            txtApellidos.setText(obj.getApellidos());
            txtTelefono.setText(obj.getTelefono());
            txtDni.setText(obj.getDni());
            txtCorreo.setText(obj.getCorreo());
            txtFechaNacimiento.setText(obj.getFechaNacimiento());
        }

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlumnoCrudFormularioActivity.this, AlumnoCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todos los datos los recibimos como String
                String nombres = txtNombres.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String dni = txtDni.getText().toString();
                String correo = txtCorreo.getText().toString();
                String fechaNacimiento = txtFechaNacimiento.getText().toString();

                if (!nombres.matches(ValidacionUtil.NOMBRE)) {
                    mensajeToast("Los nombres son de 3 a 30 caracteres");
                } else if (!apellidos.matches(ValidacionUtil.APELLIDO)) {
                    mensajeToast("Los apellidos son de 3 a 30 caracteres");
                } else if (!telefono.matches(ValidacionUtil.TELEFONO)) {
                    mensajeToast("El telefono es de 9 dígitos");
                } else if (!dni.matches(ValidacionUtil.DNI)) {
                    mensajeToast("El DNI es de 8 dígitos");
                } else if (!correo.matches(ValidacionUtil.CORREO)) {
                    mensajeToast("El correo es de la forma expample@com");
                } else if (!fechaNacimiento.matches(ValidacionUtil.FECHA)) {
                    mensajeToast("La fecha de nacimiento es YYYY-MM-dd");
                } else {
                    String pais = spnPais.getSelectedItem().toString();
                    String idPais = pais.split(":")[0];
                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));

                    Alumno objAlumno = new Alumno();

                    objAlumno.setNombres(nombres);
                    objAlumno.setApellidos(apellidos);
                    objAlumno.setTelefono(telefono);
                    objAlumno.setDni(dni);
                    objAlumno.setCorreo(correo);
                    objAlumno.setFechaNacimiento(fechaNacimiento);
                    objAlumno.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objAlumno.setEstado(1);
                    objAlumno.setPais(objPais);

                    Bundle extras = getIntent().getExtras();
                    String tipo = extras.getString("var_tipo");
                    if (tipo.equals("REGISTRAR")) {
                        registrarAlumno(objAlumno);
                    } else if (tipo.equals("ACTUALIZAR")) {
                        Alumno obj = (Alumno) extras.get("var_item");
                        objAlumno.setIdAlumno(obj.getIdAlumno());
                        actualizaAlumno(objAlumno);
                    }
                }
            }
        });
    }


    public void cargaPais() {
        Call<List<Pais>> call = servicePais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()) {
                    List<Pais> lstPaises = response.body();
                    for (Pais obj : lstPaises) {
                        paises.add(obj.getIdPais() + ":" + obj.getNombre());
                    }

                    Bundle extras = getIntent().getExtras();
                    String tipo = extras.getString("var_tipo");
                    if (tipo.equals("ACTUALIZAR")) {
                        Alumno obj = (Alumno) extras.get("var_item");
                        int posicion = -1;
                        String item = obj.getPais().getIdPais() + ":" + obj.getPais().getNombre();

                        for (int i = 0; i < paises.size(); i++) {
                            if (paises.get(i).equals(item)) {
                                posicion = i;
                                break;
                            }
                        }
                        if (posicion != -1) {
                            spnPais.setSelection(posicion);
                        }
                    }

                    adaptador.notifyDataSetChanged();
                } else {
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void registrarAlumno(Alumno objAlumno) {
        Call<Alumno> call = serviceAlumno.insertaAlumno(objAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()) {
                    Alumno objSalida = response.body();
                    mensajeAlert(" Se registro el alumno " + "\nID >> " + objSalida.getIdAlumno()
                            + "\nAlumno >>> " + objSalida.getNombres() + " " + objSalida.getApellidos());
                } else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

    public void actualizaAlumno(Alumno objAlumno) {
        Call<Alumno> call = serviceAlumno.actualizaAlumno(objAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()) {
                    Alumno objSalida = response.body();
                    mensajeAlert(" Se actualizo el alumno " + "\nID >> " + objSalida.getIdAlumno()
                            + "\nAlumno >>> " + objSalida.getNombres() + " " + objSalida.getApellidos());
                } else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

    public void mensajeToast(String mensaje) {
        Toast toast1 = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

}