package com.cibertec.movil_modelo_proyecto_2022_2.util;

import android.widget.Spinner;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;



public class FunctionUtil {

	public static int ESTADO_ACTIVO = 1;
	public static int ESTADO_INACTIVO = 0;

	public static String getFechaActualStringDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	public static String getFechaActualString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	public static String getFechaStringDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String getFechaString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static int getIndex(Spinner spinner, String myString){
		for (int i=0;i<spinner.getCount();i++){
			if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
				return i;
			}
		}
		return -1;
	}

}
