package com.example.ejercicio_final_labo_v;

import android.os.Handler;
import android.os.Message;

public class HiloConexion extends Thread{
    Handler colaMensajes;
    String url;
    boolean texto;
    int position;

    public HiloConexion(Handler colaMensajes, String url){
        this.colaMensajes = colaMensajes;
        this.url = url;
        this.texto = texto;
    }

    public void run() {
        ConexionHttp con = new ConexionHttp();
        byte[] respueta = con.obtenerInformacion(this.url);
        String respuetaS = new String(respueta);
        Message message = new Message();
        message.obj = respuetaS;
        this.colaMensajes.sendMessage(message);
    }
}
