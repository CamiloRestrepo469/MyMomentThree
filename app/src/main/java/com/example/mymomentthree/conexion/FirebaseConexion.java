package com.example.mymomentthree.conexion;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseConexion {
    private static FirebaseAuth mAuth;
    //    private static StorageReference mStorageRef;
    private static FirebaseFirestore db;

    private static FirebaseStorage mFirebaseStorage;


    public static FirebaseAuth ConectionAuth(){
        return mAuth = FirebaseAuth.getInstance();
    }

//    private static StorageReference ConectionStorage(){
//        return mStorageRef = FirebaseStorage.getInstance().getReference();
//    }

    public static FirebaseFirestore ConectionFirestore(){
        return db = FirebaseFirestore.getInstance();
    }

    public static FirebaseStorage ConectionStorage(){
        return mFirebaseStorage=FirebaseStorage.getInstance();
    }
}
