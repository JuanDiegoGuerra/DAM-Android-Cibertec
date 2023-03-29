package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.vista.registra.ProveedorRegistraActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ProveedorCrudFormularioActivity extends NewAppCompatActivity {

    //COnexion
    ServiceProveedor api;

    //Componentes
    TextView txtTitulo, txtTextoEstado;
    EditText txtRazSoc, txtRuc, txtGerente, txtTel, txtDirec, txtFecha;
    Button btnEnviar, btnEliminar;
    Spinner spnPais, spnEstado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_crud_formulario);

        //se obtiene los componentes
        txtTitulo = findViewById(R.id.txtCrudRegistraProvTitulo);
        txtRazSoc = findViewById(R.id.txtCrudRegistraProvRazSoc);
        txtRuc = findViewById(R.id.txtCrudRegistraProvRuc);
        txtGerente = findViewById(R.id.txtCrudRegistraProvGerente);
        txtTel = findViewById(R.id.txtCrudRegistraProvTel);
        txtDirec = findViewById(R.id.txtCrudRegistraProvDirec);
        txtFecha = findViewById(R.id.txtCrudRegistraProvFechaFundacion);
        btnEnviar = findViewById(R.id.btnCrudRegistraProvEnviar);
        spnPais = findViewById(R.id.spnCrudRegistraProvPais);
        spnEstado = findViewById(R.id.spnCrudRegistraProvEstado);
        btnEliminar = findViewById(R.id.btnCrudRegistraProvEliminar);
        txtTextoEstado = findViewById(R.id.txtCrudRegistraProvEstado);



        api = ConnectionRest.getConnection().create(ServiceProveedor.class);

        Bundle extras = getIntent().getExtras();
        String tipo = extras.getString("var_tipo");

        if ("REGISTRAR".equals(tipo)){
            txtTitulo.setText("Mantenimiento Proveedor - Registrar");
            btnEnviar.setText("Registrar");
            txtTextoEstado.setVisibility(View.GONE);
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
                    txtFecha.setText(dateFormat.format(myCalendar.getTime()));
                }
            };

            txtFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar myCalendar= Calendar.getInstance();

                    //Al Inicio el Date picker empeiza con la fecha Actual
                    new DatePickerDialog(ProveedorCrudFormularioActivity.this, date,
                            myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                }
            });

            btnEliminar.setVisibility(View.GONE);
            spnEstado.setVisibility(View.GONE);
        }else if ("ACTUALIZAR".equals(tipo)){
            txtTitulo.setText("Mantenimiento Proveedor - Actualizar");
            btnEnviar.setText("Actualizar");
            txtTextoEstado.setVisibility(View.VISIBLE);
            btnEliminar.setVisibility(View.VISIBLE);
            spnEstado.setVisibility(View.VISIBLE);

            Proveedor objProveedor = (Proveedor) extras.get("var_item");
            //txtRazSoc.setText(objProveedor.getRazonSocial());
            txtRuc.setText(objProveedor.getRuc());
            //txtGerente.setText(objProveedor.getGerente());
            txtDirec.setText(objProveedor.getDireccion());
            txtTel.setText(objProveedor.getTelefono());
            //txtFecha.setText(objProveedor.getFechaFundacion());

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
                    txtFecha.setText(dateFormat.format(myCalendar.getTime()));
                }
            };

            txtFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar myCalendar= Calendar.getInstance();

                    //Al Inicio el Date picker empeiza con la fecha Actual
                    new DatePickerDialog(ProveedorCrudFormularioActivity.this, date,
                            myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                }
            });

            //int pos = FunctionUtil.getIndex(spnPais, objProveedor.getPais());
            //spnPais.setSelection(pos);

            spnEstado.setSelection(objProveedor.getEstado() ==1?1:2);



        }

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String razsoc = txtRazSoc.getText().toString();
                String ruc = txtRuc.getText().toString();
                String ger = txtGerente.getText().toString();
                String dir = txtDirec.getText().toString();
                String tel = txtTel.getText().toString();
                String pa = spnPais.getSelectedItem().toString();
                String fec = txtFecha.getText().toString();

                if (!razsoc.matches(ValidacionUtil.RAZSOC)){
                    mensajeToastLong("La razon social es de 3 a 300 caracteres");
                }else if (!ruc.matches(ValidacionUtil.RUC)){
                    mensajeToastLong("El RUC es de 11 caracteres y sin espacios");
                }else if (!ger.matches(ValidacionUtil.NOMBRE)){
                    mensajeToastLong("El gerente es de 2 a 30 caracteres");
                }else if (!dir.matches(ValidacionUtil.DIRECCION)){
                    mensajeToastLong("La dirección es de 3 a 300 caracteres");
                }else if (!tel.matches(ValidacionUtil.TELEFONO)){
                    mensajeToastLong("El telefono es de 7 a 9 caracteres");
                }else if (spnPais.getSelectedItemPosition() == 0){
                    mensajeToastLong("Selecciona un país");
                }else if (!fec.matches(ValidacionUtil.FECHA)){
                    mensajeToastLong("La fecha tiene formato Año-Mes-Dia");
                }else{

                    if ("REGISTRAR".equals(tipo)){
                        Proveedor obj = new Proveedor();
                        //obj.setRazonSocial(razsoc);
                        obj.setRuc(ruc);
                        //obj.setGerente(ger);
                        obj.setDireccion(dir);
                        obj.setTelefono(tel);
                        //obj.setPais(pa);
                        //obj.setFechaFundacion(txtFecha.getText().toString());
                        obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                        obj.setEstado(FunctionUtil.ESTADO_ACTIVO);
                        inserta(obj);
                    } else if ("ACTUALIZAR".equals(tipo)){
                        Proveedor obj = (Proveedor) extras.get("var_item");
                        //obj.setRazonSocial(razsoc);
                        obj.setRuc(ruc);
                        //obj.setGerente(ger);
                        obj.setDireccion(dir);
                        obj.setTelefono(tel);
                        //obj.setPais(pa);
                        //obj.setFechaFundacion(txtFecha.getText().toString());
                        obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                        int posEstado = spnEstado.getSelectedItemPosition();
                        obj.setEstado(posEstado==1?1:0);
                        actualiza(obj);

                    }
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Proveedor objProveedor = (Proveedor) extras.get("var_item");
                elimina(objProveedor.getIdProveedor());

            }
        });


    }


    public void inserta(Proveedor obj){
        Call<Proveedor> call = api.registraProveedor(obj);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()){
                    Proveedor objSalida = response.body();
                    if (objSalida == null){
                        mensajeToastLong("ERROR -> NO se insertó");
                    }else{
                        mensajeToastLong("ÉXITO -> Se insertó correctamente : " + objSalida.getIdProveedor());
                    }
                }else{
                    mensajeToastLong("ERROR -> Error en la respuesta");
                }
            }
            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeToastLong("ERROR -> " +   t.getMessage());
            }
        });
    }

    public void actualiza(Proveedor obj){
        Call<Proveedor> call = api.actualizaProveedor(obj);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()){
                    Proveedor objSalida = response.body();
                    if (objSalida == null){
                        mensajeToastLong("ERROR -> NO se actualizó");
                    }else{
                        mensajeToastLong("ÉXITO -> Se actualizó correctamente : " + objSalida.getIdProveedor());
                    }
                }else{
                    mensajeToastLong("ERROR -> Error en la respuesta");
                }
            }
            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeToastLong("ERROR -> " +   t.getMessage());
            }
        });
    }

    public void elimina(int id) {
        Call<Proveedor> call = api.eliminaProveedor(id);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()) {
                    Proveedor objSalida = response.body();
                    if (objSalida == null){
                        mensajeToastLong("ERROR -> NO se eliminó");
                    }else{
                        mensajeToastLong("ÉXITO -> Se eliminó correctamente : " + objSalida.getIdProveedor());
                    }
                }else{
                    mensajeToastLong("ERROR -> Error en la respuesta");
                }
            }
            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeToastLong("ERROR -> " +   t.getMessage());
            }
        });
    }



}