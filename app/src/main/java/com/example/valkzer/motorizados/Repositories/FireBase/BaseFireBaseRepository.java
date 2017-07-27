package com.example.valkzer.motorizados.Repositories.FireBase;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

abstract class BaseFireBaseRepository {

    DatabaseReference repositoryReference = null;

    BaseFireBaseRepository(String resourcePath)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.repositoryReference = database.getReference(resourcePath);
    }

}
