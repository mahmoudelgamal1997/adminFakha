package com.example.adminfakha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.adminfakha.Adapter.OfferAdapter;
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

public class OfferState extends AppCompatActivity implements OfferAdapter.OfferListner {

    FirebaseFirestore db ;
    List<OfferModel> mMissionsList;
    ListView mMissionsListView;
    OnCompleteListener onCompleteListener;
    private String COLLECTION_NAME = "offer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_state);


        db= FirebaseFirestore.getInstance();

         onCompleteListener=new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mMissionsList = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        OfferModel miss = document.toObject(OfferModel.class);
                        mMissionsList.add(miss);
                    }
                    mMissionsListView = (ListView) findViewById(R.id.list_offer);
                    OfferAdapter mMissionAdapter = new OfferAdapter(OfferState.this,R.layout.offer_state, mMissionsList,OfferState.this);
                    mMissionsListView.setAdapter(mMissionAdapter);
                } else {
                    Log.d("MissionActivity", "Error getting documents: ", task.getException());
                }
            }
        };

        db.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void onClickState(int postion) {
    //    db.collection("offer").document();
        if (mMissionsList.get(postion).isActive()){
            OfferModel model = mMissionsList.get(postion);

            db.collection(COLLECTION_NAME).document(model.getId()).set(SetMap(model,false));
            db.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);

        }else {
            OfferModel model = mMissionsList.get(postion);
            db.collection(COLLECTION_NAME).document(model.getId()).set(SetMap(model,true));
            db.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);
        }
    }

    @Override
    public void onClickDelte(int postion) {
        AlertDialog diaBox = AskOption(postion);
        diaBox.show();


    }

    public Map<String,Object> SetMap(OfferModel model,boolean state){
        Map<String,Object> map= new HashMap<>();
        map.put("unit", model.getUnit());
        map.put("id", model.getId());
        map.put("product_name", model.getProduct_name());
        map.put("price",model.getPrice());
        map.put("limit",model.getLimit());
        map.put("image",model.getImage());
        map.put("order_quantity",model.getOrder_quantity());
        map.put("price_offer", model.getPrice_offer());
        map.put("active", state);
        return map;
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
                        OfferModel model = mMissionsList.get(postion);
                        db.collection(COLLECTION_NAME).document(model.getId()).delete();
                        db.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);
                        dialog.dismiss();

                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .create();

        return myQuittingDialogBox;
    }

}