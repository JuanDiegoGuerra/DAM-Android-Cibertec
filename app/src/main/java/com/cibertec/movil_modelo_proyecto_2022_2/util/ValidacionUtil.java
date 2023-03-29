package com.cibertec.movil_modelo_proyecto_2022_2.util;

public class ValidacionUtil {

    public static final String TEXTO = "[a-zA-ZáéíóúñüÁÉÍÓÚÑÜ\\s]{2,20}";
    public static final String DNI = "[0-9]{8}";
    public static final String TELEFONO = "[0-9]{9}";
    public static final String NUM_HIJOS = "[0-9]|[1][0]";
    public static final String SUELDO = "(\\d+)|(\\d+[.]\\d{1,2})";
    public static final String PLACA = "[A-Z]{2}\\d{4}";
    public static final String CORREO = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";


    public static final String DESCRIPCION = "[a-zA-ZáéíóúñüÁÉÍÓÚÑÜ\\s]{3,200}";
    public static final String PRECIO = "\\d+[.]\\d";
    public static final String PLACA_FORMA_DOS = "[A-Z]{3}\\d{3}";
    public static final String STOCK = "\\d+";
    public static final String FECHA = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
    public static final String CORREO_GMAIL = "[a-zA-Z]+(@gmail.com)";

    public static final String NOMBRE = "[a-zA-ZáéíóúñüÁÉÍÓÚÑÜ\\s]{3,30}";
    public static final String APELLIDO = "[a-zA-ZáéíóúñüÁÉÍÓÚÑÜ\\s]{3,30}";
    public static final String DIRECCION = "[a-zA-ZáéíóúñüÁÉÍÓÚÑÜ\\s0-9]{3,30}";
    public static final String EDAD = "\\d{2}";
    public static final String SEXO = "[FM]";


    //Validaciones para Sala
    public static final String NUMERO = "[0-9][A-Z][0-9][0-9][0-9]";
    public static final String CAPACIDAD = "[0-9]{1,2}";
    public static final String RECURSOS = "[a-zA-ZáéíóúñüÁÉÍÓÚÑÜ\\s]{3,30}";
    public static final String SALA_NUM = "[0-9]{7}";

    //Validacion proveedor
    public static final String RAZSOC = "[a-zA-ZáéíóúñüÁÉÍÓÚÑÜ\\s0-9\\.]{3,300}";
    public static final String RUC = "[0-9]{11}";

    //Validaciones para Libro
    public static final String ANIO = "[0-9]{4}";
    public static final String SERIE = "[a-zA-ZáéíóúñüÁÉÍÓÚÑÜ\\s]{2,12}";
    public static final String TITULO = "[a-zA-ZáéíóúñüÁÉÍÓÚÑÜ\\s]{2,30}";

}
