package com.example.ejercicio_final_labo_v;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    public int id;
    public String nombre;
    public String rol;
    public Boolean admin;

    public Usuario(int id, String nombre, String rol, Boolean admin) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.admin = admin;
    }

    public static List<Usuario> parsearUsuarios(String usuarios){
        List<Usuario> aux = new ArrayList<>();

        JSONArray json = null;
        try {
            json = new JSONArray(usuarios);
            for(int i = 0; i < json.length(); i++){
                JSONObject elemento = json.getJSONObject(i);
                Usuario usuario = new Usuario(
                        Integer.parseInt(elemento.getString("id")),
                        elemento.getString("username"),
                        elemento.getString("rol"),
                        Boolean.parseBoolean(elemento.getString("admin")));
                aux.add(usuario);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return aux;
    }

    public static String StringifyUsuarios(List<Usuario> usuarios){

        String ret = "[";

        for ( Usuario usuario : usuarios) {
            ret += "{\"id\":\"" + usuario.id + "\",";
            ret += "\"username\":\"" + usuario.nombre + "\",";
            ret += "\"rol\":\"" + usuario.rol + "\",";
            ret += "\"admin\":\"" + usuario.admin + "\"},";
        }
        ret = ret.substring(0,ret.length() - 1);
        ret += "]";

        return ret;
    }

    public static int getHigherId(List<Usuario> usuarios){

        int ret = 0;

        for ( Usuario usuario : usuarios) {
            if(usuario.id > ret){
                ret = usuario.id;
            }
        }
        return ret;
    }
}
