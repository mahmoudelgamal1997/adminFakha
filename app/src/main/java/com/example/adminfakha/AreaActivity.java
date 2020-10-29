package com.example.adminfakha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaActivity extends AppCompatActivity {

    EditText editText_araName;
    Button but_add;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore db;
    Map<String,Object> map;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        editText_araName=findViewById(R.id.add_area);
        but_add=findViewById(R.id.but_add_area);
        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db= FirebaseFirestore.getInstance();

        but_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String area=editText_araName.getText().toString();
                if (!TextUtils.isEmpty(area) && !area.startsWith("  ")) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("area", area.trim());
                    List<Map<String, Object>> list = new ArrayList<>();

                    list.add(map);
                    DocumentReference washingtonRef = db.collection("selectedarea").document("3SxysN4Z3lDMblLNZLaO");

                    washingtonRef.update("selectedareas", FieldValue.arrayUnion(area)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                    showToast("Area Added Successfully");
                    editText_araName.setText("");
                        }
                    });
                }else {
                    showToast("Please write an area");
                } }
        });
    }
    void showToast(String msg){
        if (toast!=null){
            toast.cancel();
        }
        toast= Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
        toast.show();
    }
}