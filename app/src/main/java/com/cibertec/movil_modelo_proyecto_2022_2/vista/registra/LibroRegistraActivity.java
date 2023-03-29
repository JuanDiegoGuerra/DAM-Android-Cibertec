package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroRegistraActivity extends NewAppCompatActivity {

    Spinner spnCategoria;
    ArrayAdapter<String> adaptador;
    ArrayList<String> categorias = new ArrayList<String>();

    //Servicio
    ServiceLibro serviceLibro;
    ServiceCategoria serviceCategoria;

    //Componentes
    Button btnRegistrar;
    EditText txtTitulo, txtAnio, txtSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_registra);

        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);
        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);

        //Adaptador
        adaptador = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,categorias);
        spnCategoria = findViewById(R.id.spnCategoria);
        spnCategoria.setAdapter(adaptador);

        cargaCategoria();

        txtTitulo = findViewById(R.id.txtTitulo);
        txtAnio = findViewById(R.id.txtAnio);
        txtSerie = findViewById(R.id.txtSerie);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Recibimos los datos como Sting
                String titulo = txtTitulo.getText().toString();
                String anio = txtAnio.getText().toString();
                String serie = txtSerie.getText().toString();

                if (!titulo.matches(ValidacionUtil.TEXTO)){
                    mensajeToast("El título es de 2 a 20 caracteres");
                }else if(!anio.matches(ValidacionUtil.ANIO)){
                    mensajeToast("El año es númerico y maximo 4 caracteres");
                }else if(!serie.matches(ValidacionUtil.SERIE)){
                    mensajeToast("La serie debe ser de 12 caracteres");
                }else{
                    String categoria = spnCategoria.getSelectedItem().toString();
                    String idCategoria = categoria.split(":")[0];
                    Categoria objCategoria = new Categoria();
                    objCategoria.setIdCategoria(Integer.parseInt(idCategoria));

                    Libro objLibro = new Libro();
                    objLibro.setTitulo(titulo);
                    objLibro.setAnio(anio);
                    objLibro.setSerie(serie);
                    objLibro.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objLibro.setEstado(1);
                    objLibro.setCategoria(objCategoria);

                    registrarLibro(objLibro);
                }
            }
        });
    }
    public void registrarLibro(Libro objLibro){
        Call<Libro> call = serviceLibro.registrarLibro(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if (response.isSuccessful()){
                    Libro objLibro = response.body();
                    mensajeAlert(" Registro existoso >>> ID >> " + objLibro.getIdLibro() + " >>> Titulo >>> " + objLibro.getTitulo());
                }else{
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                mensajeToast("Error al acceder al Serivico Rest >>> " + t.getMessage());
            }
        });
    }

    public void cargaCategoria(){
        Call<List<Categoria>> call = serviceCategoria.listaTodos();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    List<Categoria> lstCategoria = response.body();
                    for(Categoria obj:lstCategoria){
                        categorias.add(obj.getIdCategoria()+":"+obj.getDescripcion());
                    }
                    adaptador.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                mensajeToast(" Error al acceder al Servicio Rest >>> " + t.getMessage());
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