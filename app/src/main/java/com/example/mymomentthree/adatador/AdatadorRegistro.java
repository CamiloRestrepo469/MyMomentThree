package com.example.mymomentthree.adatador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mymomentthree.R;
import com.example.mymomentthree.modelos.RegistroModelo;

import java.util.ArrayList;

public class AdatadorRegistro extends BaseAdapter {

    private final Context context;
    private RegistroModelo modelo;
    private ArrayList<RegistroModelo> list;

    public AdatadorRegistro(Context context, ArrayList<RegistroModelo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View listaView =view;
        if(view==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listaView=inflater.inflate(R.layout.listado_registro_,viewGroup,false);

        }
        TextView textView_correo=listaView.findViewById(R.id.textView_correo);
        TextView textView_nombre=listaView.findViewById(R.id.textView_nombre);
        TextView textView_cedula=listaView.findViewById(R.id.textView_cedula);
        TextView textView_celular=listaView.findViewById(R.id.textView_celular);
        modelo=list.get(i);
        textView_correo.setText(modelo.getCorreo());
        textView_nombre.setText(modelo.getNombre());
        textView_cedula.setText(modelo.getCedula());
        textView_celular.setText(modelo.getCelular());


        return listaView;
    }
}
