package com.example.mymomentthree;

import android.content.Intent;
import android.os.Bundle;

import com.example.mymomentthree.conexion.FirebaseConexion;
import com.example.mymomentthree.modelos.RegistroModelo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleActivity extends AppCompatActivity {
    private TextView textView_correo_detalle,textView_nombre_detalle,textView_cedula_detalle,textView_celular_detalle;
    private FloatingActionButton fab_detalle_editar,fab_detalle_eliminar;
    protected FirebaseFirestore db;


//    private final String registroRefrence="registro";
//    private DatabaseReference databaseReference=firebaseDatabase.getReference(registroRefrence);

//    //    private DocumentReference documentReference;
////    final private String collection = "registro";
//
//    protected Query query;
//    protected CollectionReference collectionReference;
//    protected StorageReference mStorageReference,fileReference;
//
//    protected final String COLLECTION_NAME = "registro";

//    private FirebaseFirestore firebaseDatabase=FirebaseDatabase.getInstance();

//    db = FirebaseConexion.ConectionFirestore();



    private final String registro="registro";
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private FirebaseFirestore firebaseConexion=FirebaseConexion.ConectionFirestore(registro);

//

    private RegistroModelo modelo;
//    private ArrayList<RegistroModelo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar_detalle);
        setSupportActionBar(toolbar);

        init();

        fab_detalle_editar = findViewById(R.id.fab_detalle_editar);
        fab_detalle_eliminar = findViewById(R.id.fab_detalle_eliminar);

        textView_correo_detalle = findViewById(R.id.textView_correo_detalle);
        textView_nombre_detalle = findViewById(R.id.textView_nombre_detalle);
        textView_cedula_detalle = findViewById(R.id.textView_cedula_detalle);
        textView_celular_detalle = findViewById(R.id.textView_celular_detalle);
        modelo=new RegistroModelo();

        final String id = getIntent().getStringExtra("id");
        if(id!=null&&!id.equals("")){
            firebaseConexion.collection(id)
                    .add(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            modelo=snapshot.getValue(RegistroModelo.class);
                            if(modelo!=null){
                                textView_correo_detalle.setText(modelo.getCorreo());
                                textView_nombre_detalle.setText(modelo.getNombre());
                                textView_cedula_detalle.setText(modelo.getCedula());
                                textView_celular_detalle.setText(modelo.getCelular());

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(DetalleActivity.this,"Error con Firebase",Toast.LENGTH_LONG).show();
                            }
                    });
                }

        fab_detalle_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modelo!=null){
                    if(modelo.getId()!=null&&!modelo.getId().equals("")){
                        Intent editarRegistro = new Intent(DetalleActivity.this,EditarActivity.class);
                        editarRegistro.putExtra("id",modelo.getId());
                        startActivity(editarRegistro);
                        finish();
                    }
                }

            }
        });

        fab_detalle_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar=Snackbar.make(view,"Quieres eliminar este registro?",Snackbar.LENGTH_LONG);
                snackbar.setAction("Eso es lo que deseo", new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if(modelo!=null){
                            if(modelo.getId()!=null&&!modelo.getId().equals("")){
                                final Task<DocumentReference> documentReferenceTask = firebaseConexion.collection(modelo.getId()).add()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent principal = new Intent(DetalleActivity.this, MainActivity.class);
                                                startActivity(principal);
                                                finish();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Snackbar.make(view, "No elimino el registro verifique la informacion", Snackbar.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        }


                    }
                });
                snackbar.show();

            }
        });
    }
    protected void init(){
        modelo = new RegistroModelo();
        db = FirebaseConexion.ConectionFirestore(registro);
//        collectionReference = db.collection(COLLECTION_NAME);
    }
}