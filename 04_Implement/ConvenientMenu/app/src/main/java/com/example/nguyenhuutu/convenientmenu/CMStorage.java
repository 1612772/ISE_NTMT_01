package com.example.nguyenhuutu.convenientmenu;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CMStorage {
    static StorageReference storage = FirebaseStorage.getInstance().getReference();
}
