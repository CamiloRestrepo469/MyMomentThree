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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    private EditText editText_correo,editText_nombre,editText_cedula,editText_celular;
    private FloatingActionButton fab_registro;
    private RegistroModelo modelo;
//    protected FirebaseFirestore db;

//    protected FirebaseFirestore db;
//    protected FirebaseAuth mAuth;
//    protected FirebaseStorage mFirebaseStorage;
//
//
//
//    protected Query query;
//    protected CollectionReference collectionReference;
//    protected StorageReference mStorageReference,fileReference;
//
//    protected final String COLLECTION_NAME = "registro";
//
//
//    private final String registroRefrence="registro";
//    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//    private DatabaseReference databaseReference=firebaseDatabase.getReference(registroRefrence);


    //    private DocumentReference documentReference;


//    final private String collection = "registro";
//    protected FirebaseFirestore db;
//    protected Query query;
//    protected CollectionReference collectionReference;
//    protected StorageReference mStorageReference,fileReference;
//
//    protected final String COLLECTION_NAME = "registro";

    private final String registro="registro";
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private FirebaseFirestore firebaseConexion=FirebaseConexion.ConectionFirestore(registro);
//    protected FirebaseFirestore db;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = findViewById(R.id.toolbar_registro);
        setSupportActionBar(toolbar);

        init();

        editText_correo=findViewById(R.id.editText_correo);
        editText_nombre=findViewById(R.id.editText_nombre);
        editText_cedula=findViewById(R.id.editText_cedula);
        editText_celular=findViewById(R.id.editText_celular);
        fab_registro=findViewById(R.id.fab_registro);


        fab_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String correo =editText_correo.getText().toString();
                String nombre =editText_nombre.getText().toString();
                String cedula =editText_cedula.getText().toString();
                String celular =editText_celular.getText().toString();

                if(!correo.equals("")&&!nombre.equals("")&&!cedula.equals("")&&!celular.equals("")){
                       String id=firebaseFirestore.hashCode().getKey();
                       if(id!=null&&!id.equals("")){
                           modelo=new RegistroModelo(id,correo,nombre,cedula,celular);
                           final Task<QuerySnapshot> querySnapshotTask = firebaseFirestore.collection(id).get(modelo)
                                   .addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void aVoid) {
                                           if (!modelo.getId().equals("") && modelo.getId() != null) {
                                               Intent editarDetalle = new Intent(RegistroActivity.this, DetalleActivity.class);
                                               editarDetalle.putExtra("id", modelo.getId());
                                               startActivity(editarDetalle);
                                               finish();

                                           }

                                       }
                                   })
                                   .addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Snackbar.make(view, "No guardo el registro", Snackbar.LENGTH_LONG).show();
                                       }
                                   });
                       }else {
                           Snackbar.make(view,"No hay conexion con la base de datos",Snackbar.LENGTH_LONG).show();

                       }


                }else{
                    Toast.makeText(RegistroActivity.this,"llenar todos los campos",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    protected void init(){
        modelo = new RegistroModelo();
        db = FirebaseConexion.ConectionFirestore(registro);
//        collectionReference = db.collection(COLLECTION_NAME);
    }


}