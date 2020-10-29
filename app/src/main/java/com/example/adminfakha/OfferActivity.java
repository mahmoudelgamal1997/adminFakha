package com.example.adminfakha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OfferActivity extends AppCompatActivity  {

    private ImageView img_product;
    private EditText edit_price,edit_name,edit_unit,edit_offer,edit_limit;
    private Button button;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    String[] category;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    private DocumentReference documentId;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore db ;
    Map<String,Object> map ;
    String SelectedCategory="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        img_product=findViewById(R.id.img_product);
        edit_name=findViewById(R.id.edit_name);
        edit_price=findViewById(R.id.edit_price);
        edit_offer=findViewById(R.id.edit_offer);
        edit_unit=findViewById(R.id.edit_unit);
        edit_limit=findViewById(R.id.edit_offer_limit);
        button=findViewById(R.id.upload);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db= FirebaseFirestore.getInstance();
        map = new HashMap<>();
        documentId = db.collection("offer").document();

        img_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=edit_name.getText().toString();
                String price=edit_price.getText().toString();
                String price_offer=edit_offer.getText().toString();
                String unit=edit_unit.getText().toString();
                String limit=edit_limit.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price)   && !TextUtils.isEmpty(unit) && filePath!=null) {
                    uploadImage();
                    map.put("unit", unit);
                    map.put("product_name", name);
                    map.put("price",Double.parseDouble(price));
                    map.put("limit",Integer.parseInt(limit));
                    map.put("order_quantity",0);
                    map.put("price_offer", Double.parseDouble(price_offer));
                    map.put("active", true);

                    edit_name.setText("");
                    edit_limit.setText("");
                    edit_price.setText("");
                    edit_offer.setText("");
                    edit_unit.setText("");
                    img_product.setImageDrawable(null);
                }else{
                    Toast.makeText(OfferActivity.this, "Fill data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(),
                        filePath);
                img_product.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            } } }

    // UploadImage method
    private void uploadImage() {
    if (filePath != null) {

    // Code for showing progressDialog while uploading
    ProgressDialog progressDialog
    = new ProgressDialog(this);
    progressDialog.setTitle("Uploading...");
    progressDialog.setCancelable(false);
    progressDialog.show();

    // Defining the child of storageReference
    StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

    Task<Uri> urlTask =  ref.putFile(filePath).continueWithTask(new
    Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
    @Override
    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
        if (!task.isSuccessful()) {
            throw task.getException();
        }

        // Continue with the task to get the download URL
        return ref.getDownloadUrl();
    }
    }).addOnCompleteListener(new OnCompleteListener<Uri>() {

    @Override
    public void onComplete(@NonNull Task<Uri> task) {
    if (task.isSuccessful()) {
    Uri downloadUri = task.getResult();

    map.put("image",downloadUri.toString());
    map.put("id",documentId.getId());
    documentId.set(map);
    progressDialog.dismiss();
    Toast.makeText(OfferActivity.this, "Done", Toast.LENGTH_SHORT).show();
    } else {
    // Handle failures
    // ...
    }
    }
    });

        }
    }

    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

}