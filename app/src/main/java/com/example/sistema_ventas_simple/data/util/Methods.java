package com.example.sistema_ventas_simple.data.util;

import com.example.sistema_ventas_simple.data.model.Producto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Methods {

    //método para la concatenación del objetos
    public static String concat(Object[] lista) {
        String cadena = "";
        for (Object item : lista) {
            cadena += item.toString();
        }
        return cadena;
    }

    //componer lista de objetos con un identificador. ejm = abc@dfe@123
    public static String cadenaComponer(String caracter, Object[] lista) {
        String cadena = "";

        if (!caracter.isEmpty()) {
            for (Object item : lista) {
                cadena += item.toString() + caracter;
            }
        }
        return cadena.substring(0, cadena.length() - 1);
    }

    //descomponer una lista, buscando un caracter identificador, devuelve el text segun la posicion del
    public static String cadenaDescomponer(String cadena, int posicion, String caracter) {
        String cadenaRetorno = cadena;

        //si se pide una posicion mas
        if (posicion > 1) {
            //recorrido para quitar datos
            for (int i = 0; i < posicion - 1; i++) {
                //descomposicion 123@456@abc
                cadenaRetorno = cadenaRetorno.substring(cadenaRetorno.indexOf(caracter) + 1);
            }
        }

        //cuando encuentre el valor
        if (cadenaRetorno.indexOf(caracter) > 0) {
            cadenaRetorno = cadenaRetorno.substring(0, cadenaRetorno.indexOf(caracter));
        }

        return cadenaRetorno;
    }

    public static String getHora() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public static String getFecha() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    public static String convertProductoListToText(List<Producto> lista) {
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    public static List<Producto> convertProductoTextoToLista(String cadena) {
        Gson gson = new Gson();
        Type lista = new TypeToken<List<Producto>>() {}.getType();

        return gson.fromJson(cadena, lista);
    }


}
