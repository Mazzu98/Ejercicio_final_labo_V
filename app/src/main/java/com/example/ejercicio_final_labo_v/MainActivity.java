package com.example.ejercicio_final_labo_v;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, Handler.Callback {
    SearchView sv;
    TextView tvUsuarios;
    SharedPreferences prefs;
    List<Usuario> usuarios;
    List<Usuario> usuariosFiltrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.sv = super.findViewById(R.id.svSearch);
        this.tvUsuarios = super.findViewById(R.id.tvUsuarios);
        this.prefs = getSharedPreferences("miConfig", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putString("usuarios", "");
        editor.commit();

        String usuarios = prefs.getString("usuarios", "");

        if(usuarios.isEmpty()){
            Handler handler = new Handler(this);
            HiloConexion hilo = new HiloConexion(handler, "http://192.168.1.63:3001/usuarios");
            hilo.start();

        }else{
            this.tvUsuarios.setText(usuarios);
            this.usuarios = Usuario.parsearUsuarios(usuarios);
            this.usuariosFiltrados = Usuario.parsearUsuarios(usuarios);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.svSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.agregar){
            Dialogo dialogo = new Dialogo(this, this.usuarios, this.prefs);
            dialogo.show(getSupportFragmentManager(),"dialogo");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        String usuarios = message.obj.toString();
        this.tvUsuarios.setText(usuarios);
        SharedPreferences.Editor editor = this.prefs.edit();
        this.usuarios = Usuario.parsearUsuarios(usuarios);
        this.usuariosFiltrados = Usuario.parsearUsuarios(usuarios);
        editor.putString("usuarios", usuarios);
        editor.commit();

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        filter(s);
        Usuario usuario;
        if(usuariosFiltrados.size() > 0){
            usuario = usuariosFiltrados.get(0);
        }
        else{
            usuario = null;
        }
        DialogoFiltro dialogo = new DialogoFiltro(usuario, s);
        dialogo.show(getSupportFragmentManager(),"dialogoFiltro");

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filter(s);
        tvUsuarios.setText(Usuario.StringifyUsuarios(usuariosFiltrados));
        return false;
    }

    public void filter(String s){
        if(s.length() == 0){
            this.usuariosFiltrados.clear();
            this.usuariosFiltrados.addAll(this.usuarios);

        }else{
            this.usuariosFiltrados.clear();
            for(Usuario i : this.usuarios){
                if(i.nombre.toLowerCase().contains(s.toLowerCase())){
                    this.usuariosFiltrados.add(i);
                }
            }
        }
    }
}