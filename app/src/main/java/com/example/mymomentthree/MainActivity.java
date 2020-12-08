package com.example.mymomentthree;

import android.content.Intent;
import android.os.Bundle;

import com.example.mymomentthree.adatador.AdatadorRegistro;
import com.example.mymomentthree.modelos.RegistroModelo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lv_lista_registro;
     private  FloatingActionButton fab_principal_registro;

    private RegistroModelo modelo;
    private ArrayList<RegistroModelo> list;

    private final String registroRefrence="registro";
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference(registroRefrence);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_principal);
        setSupportActionBar(toolbar);

        lv_lista_registro=findViewById(R.id.lv_lista_registro);

        fab_principal_registro= findViewById(R.id.fab_principal_registro);

        list=new ArrayList<>();
        modelo=new RegistroModelo();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()){
                    modelo=child.getValue(RegistroModelo.class);
                    if(modelo!=null){
                        list.add(modelo);
                    }



                }
                lv_lista_registro.setAdapter(new AdatadorRegistro(MainActivity.this, list));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Error con Firebase",Toast.LENGTH_LONG).show();


            }
        });

        lv_lista_registro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                modelo= (RegistroModelo) adapterView.getItemAtPosition(i);
                if(modelo!=null){
                    if(!modelo.getId().equals("")&&modelo.getId()!=null){
                        Intent editarDetalle=new Intent(MainActivity.this,DetalleActivity.class);
                        editarDetalle.putExtra("id",modelo.getId());
                        startActivity(editarDetalle);

                    }
                }

            }
        });

        fab_principal_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(MainActivity.this,RegistroActivity.class);
                startActivity(registro);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}