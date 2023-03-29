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

import androidx.appcompat.app.AlertDialog;

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


public class LibroCrudFormularioActivity extends NewAppCompatActivity {

    TextView txtTitulo;
    Button btnEnviar;
    Button btnRegresar;

    //Para el SPINER
    Spinner spnCat;
    ArrayAdapter<String> adaptador;
    ArrayList<String> categoria = new ArrayList<String>();

    //SERVICIO
    ServiceCategoria serviCate;
    ServiceLibro serviLibro;

    //COMPONENTES

    EditText txtTitu, txtAnio, txtSeri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_crud_formulario);

        txtTitulo = findViewById(R.id.idCrudLibroFrmTituloL);
        btnEnviar = findViewById(R.id.btnCrudRegistrarLibro);
        btnRegresar = findViewById(R.id.btnCrudRegresarLibro);

        serviLibro = ConnectionRest.getConnection().create(ServiceLibro.class);
        serviCate = ConnectionRest.getConnection().create(ServiceCategoria.class);

        //PARA EL ADAPTADOR
        adaptador = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categoria);
        spnCat = findViewById(R.id.spnCrudCategoriaLibro);
        spnCat.setAdapter(adaptador);

        cargaCate();


        txtTitu = findViewById(R.id.idCrudLibroFrmTitulo);
        txtAnio = findViewById(R.id.idCrudLibroFrmAño);
        txtSeri = findViewById(R.id.idCrudLibroFrmSerie);


        Bundle extras = getIntent().getExtras();
        String tipo = extras.getString("var_tipo");

        if(tipo.equals("REGISTRAR")){
            txtTitulo.setText("Mantenimiento Libro - Registrar");
            btnEnviar.setText("REGISTRA");
        }else if(tipo.equals("ACTUALIZAR")){
            txtTitulo.setText("Mantenimiento Libro - Actualizar");
            btnEnviar.setText("ACTUALIZA");

            Libro olibro = (Libro)extras.get("var_item");
            txtTitu.setText(olibro.getTitulo());
            txtAnio.setText(olibro.getAnio());
            txtSeri.setText(olibro.getSerie());

        }

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = txtTitu.getText().toString();
                String anio = txtAnio.getText().toString();
                String serie = txtSeri.getText().toString();

                if(!titulo.matches(ValidacionUtil.TITULO)) {
                    mensajeToast("El título debe contener de 2 a 30 carácteres");
                }else if(!anio.matches(ValidacionUtil.ANIO)){
                    mensajeToast("El año es inválido");

                }else {


                    String cate = spnCat.getSelectedItem().toString();
                    String idCate = cate.split(":")[0];
                    Categoria objCategoria = new Categoria();
                    objCategoria.setIdCategoria(Integer.parseInt(idCate));

                    Libro objLibro = new Libro();
                    objLibro.setTitulo(titulo);
                    objLibro.setAnio(anio);
                    objLibro.setSerie(serie);
                    objLibro.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objLibro.setEstado(1);
                    objLibro.setCategoria(objCategoria);

                    Bundle extras = getIntent().getExtras();
                    String tipo = extras.getString("var_tipo");

                    if(tipo.equals("REGISTRAR")){
                        insertaLibro(objLibro);
                    }else if(tipo.equals("ACTUALIZAR")){
                        Libro obj = (Libro)extras.get("var_item");
                        objLibro.setIdLibro(obj.getIdLibro());
                        actualizaLibro(objLibro);
                    }
                }

            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibroCrudFormularioActivity.this, LibroCrudListaActivity.class);
                startActivity(intent);
            }
        });


    }



    private void actualizaLibro(Libro objLibro) {

        Call<Libro> call = serviLibro.actualizaLibro(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if(response.isSuccessful()){
                    Libro objSalida = response.body();
                    mensajeAlert("Se actualizó exitosamente >> ID >>"+ objSalida.getIdLibro()+
                            ">>> Nro de Serie >>" + objSalida.getSerie());
                }else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                mensajeToast("Error al acceder Servicio REST >>> "+t.getMessage());

            }
        });

    }


    public void insertaLibro(Libro objLibro){
        Call<Libro> call = serviLibro.registrarLibro(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if(response.isSuccessful()){
                    Libro objSalida = response.body();
                    mensajeAlert("Registro Exitoso >> ID >>"+ objSalida.getIdLibro()+
                            ">>> Nro de Serie >>" + objSalida.getSerie());
                }else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                mensajeToast("Error al acceder Servicio REST >>> "+t.getMessage());

            }
        });
    }


    public void cargaCate(){
        Call<List<Categoria>> call = serviCate.listaTodos();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    List<Categoria> lstCate = response.body();
                    for (Categoria cat : lstCate){
                        categoria.add(cat.getIdCategoria()+":"+cat.getDescripcion());
                    }
                    Bundle extras = getIntent().getExtras();
                    String tipo = extras.getString("var_tipo");
                    if(tipo.equals("ACTUALIZAR")){
                        Libro obj = (Libro)extras.get("var_item");
                        int posicion = -1;
                        String item = obj.getCategoria().getIdCategoria() + ":"+ obj.getCategoria().getDescripcion();
                        for(int i=0; i<categoria.size();i++){
                            if(categoria.get(i).equals(item)){
                                posicion = i;
                                break;
                            }
                        }
                        if(posicion != -1){
                            spnCat.setSelection(posicion);
                        }
                    }
                    adaptador.notifyDataSetChanged();
                }else {
                    mensajeToast("Error al acceder al servicio REST >>>");
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                mensajeToast("Error al acceder al servicio REST >>>+ "+ t.getMessage());

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