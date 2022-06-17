package com.example.ejercicio_final_labo_v;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.DialogFragment;

import java.util.List;

public class Dialogo extends DialogFragment {

    private EditText edNombre;
    private Spinner spRol;
    private ToggleButton tbAdmin;
    SharedPreferences prefs;
    Activity a;
    List<Usuario> usuarios;

    public Dialogo(Activity a, List<Usuario> usuarios, SharedPreferences prefs){
        this.a = a;
        this.usuarios = usuarios;
        this.prefs = prefs;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View viewAlert = li.inflate(R.layout.layout_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewAlert);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newNombre = edNombre.getText().toString();
                if(!"".equals(newNombre)){

                    String newRol = spRol.getSelectedItem().toString();
                    Boolean newAdin = tbAdmin.isChecked();
                    int newId = Usuario.getHigherId(usuarios) + 1;
                    Usuario newUser = new Usuario(newId, newNombre, newRol, newAdin);
                    usuarios.add(newUser);
                    TextView tvUsuarios =  a.findViewById(R.id.tvUsuarios);

                    String usuariosActualizados = Usuario.StringifyUsuarios(usuarios);
                    tvUsuarios.setText(usuariosActualizados);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("usuarios", usuariosActualizados);
                    editor.commit();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        this.edNombre = viewAlert.findViewById(R.id.etNombre);
        this.spRol = viewAlert.findViewById(R.id.spRol);
        this.tbAdmin = viewAlert.findViewById(R.id.tbAdmin);

        AlertDialog ad = builder.create();
        return ad;
    }
}
