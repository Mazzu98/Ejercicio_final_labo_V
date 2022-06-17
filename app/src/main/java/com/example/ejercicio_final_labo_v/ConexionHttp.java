package com.example.ejercicio_final_labo_v;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConexionHttp {
    public static byte[] obtenerInformacion(String urlEndpoint){
        try {
            URL url = new URL(urlEndpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.connect();
            int responseCode = connection.getResponseCode();
            if(responseCode==200){
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int cantaidadLeidas =0;
                while ((cantaidadLeidas = is.read(buffer))!=-1){
                    baos.write(buffer,0,cantaidadLeidas);
                }
                is.close();

                return baos.toByteArray();
            }else{
                Log.d("Error","salio algo mal en consulta "+responseCode);
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
