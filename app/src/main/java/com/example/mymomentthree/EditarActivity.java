package com.example.mymomentthree;

import android.content.Intent;
import android.os.Bundle;

import com.example.mymomentthree.modelos.RegistroModelo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import android.widget.EditText;
import android.widget.Toast;

public class EditarActivity extends AppCompatActivity {

    private EditText editText_editar_correo,editText_editar_nombre,editText_editar_cedula,editText_editar_celular;
    private FloatingActionButton fab_editar_update;

    private final String registroRefrence="registro";
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference(registroRefrence);

    private RegistroModelo modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = findViewById(R.id.toolbar_editar);
        setSupportActionBar(toolbar);

        editText_editar_correo=findViewById(R.id.editText_editar_correo);
        editText_editar_nombre=findViewById(R.id.editText_editar_nombre);
        editText_editar_cedula=findViewById(R.id.editText_editar_cedula);
        editText_editar_celular=findViewById(R.id.editText_editar_celular);

        fab_editar_update=findViewById(R.id.fab_editar_update);

        modelo=new RegistroModelo();

        String id = getIntent().getStringExtra("id");
        if(id!=null&&!id.equals("")){
            databaseReference.child(id)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            modelo=snapshot.getValue(RegistroModelo.class);
                            if(modelo!=null){
                                editText_editar_correo.setText(modelo.getCorreo());
                                editText_editar_nombre.setText(modelo.getNombre());
                                editText_editar_cedula.setText(modelo.getCedula());
                                editText_editar_celular.setText(modelo.getCelular());

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditarActivity.this,"Error con Firebase",Toast.LENGTH_LONG).show();


                        }
                    });
        }

        fab_editar_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String correo =editText_editar_correo.getText().toString();
                String nombre =editText_editar_nombre.getText().toString();
                String cedula =editText_editar_cedula.getText().toString();
                String celular =editText_editar_celular.getText().toString();

                if(!correo.equals("")&&!nombre.equals("")&&!cedula.equals("")&&!celular.equals("")){
                    String id=modelo.getId();
                    if(modelo!=null){
                        if(id!=null&&!id.equals("")){
                            modelo.setCorreo(correo);
                            modelo.setNombre(nombre);
                            modelo.setCedula(cedula);
                            modelo.setCelular(celular);
                            databaseReference.child(id).setValue(modelo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            if(!modelo.getId().equals("")&&modelo.getId()!=null){
                                                Intent editarDetalle=new Intent(EditarActivity.this,DetalleActivity.class);
                                                editarDetalle.putExtra("id",modelo.getId());
                                                startActivity(editarDetalle);
                                                finish();
                                            }

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(view,"No actulaizo el registro",Snackbar.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    }
                    else {
                        Snackbar.make(view,"No hay conexion con la base de datos",Snackbar.LENGTH_LONG).show();

                    }


                }else{
                    Toast.makeText(EditarActivity.this,"llenar todos los campos",Toast.LENGTH_LONG).show();
                }
            }
        });




    }
}