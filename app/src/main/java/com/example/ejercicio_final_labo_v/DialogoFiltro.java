package com.example.ejercicio_final_labo_v;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

public class DialogoFiltro extends DialogFragment {

    Usuario usuario;
    String busqueda;
    public DialogoFiltro(Usuario usuario, String busqueda){
        this.usuario = usuario;
        this.busqueda = busqueda;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(usuario != null){
            builder.setTitle("Usuario encontrado");
            builder.setMessage("El rol del usuario es " + usuario.rol);
        }
        else{
            builder.setTitle("Usuario no encontrado");
            builder.setMessage("El usuario " + busqueda + " no esta dentro de la lista");
        }

        builder.setPositiveButton("cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog ad = builder.create();

        return ad;
    }
}
