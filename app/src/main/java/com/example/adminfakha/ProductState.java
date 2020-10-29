package com.example.adminfakha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.adminfakha.Adapter.ProductListAdapter;
import com.example.adminfakha.Model.OfferModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductState extends AppCompatActivity implements ProductListAdapter.ProductListner {

    private FirebaseFirestore db ;
    private List<OfferModel> mMissionsList;
    private ListView mMissionsListView;
    private OnCompleteListener onCompleteListener;
    private String COLLECTION_NAME="products";
    private boolean isDelte= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_state);


        db= FirebaseFirestore.getInstance();

        onCompleteListener=new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mMissionsList = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        OfferModel miss = document.toObject(OfferModel.class);
                        mMissionsList.add(miss);
                    }
                    mMissionsListView = (ListView) findViewById(R.id.list_product);
                    ProductListAdapter mMissionAdapter = new ProductListAdapter(ProductState.this, R.layout.offer_state, mMissionsList, ProductState.this);
                    mMissionsListView.setAdapter(mMissionAdapter);
                } else {
                    Log.d("MissionActivity", "Error getting documents: ", task.getException());
                }
            }};

        db.collection("products").get().addOnCompleteListener(onCompleteListener);
    }


    @Override
    public void onClickDelte(int postion) {
        AlertDialog diaBox = AskOption(postion);
        diaBox.show();



    }
    private AlertDialog AskOption(int postion)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        dialog.dismiss();
                        OfferModel model = mMissionsList.get(postion);
                        db.collection(COLLECTION_NAME).document(model.getId()).delete();
                        db.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        isDelte= false;
                    }
                })
                .create();

        return myQuittingDialogBox;
    }

}
