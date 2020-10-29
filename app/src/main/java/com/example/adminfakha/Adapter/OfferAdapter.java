package com.example.adminfakha.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.adminfakha.Model.OfferModel;
import com.example.adminfakha.R;

import java.util.List;

public class OfferAdapter extends ArrayAdapter<OfferModel> {

private int resourceLayout;
private Context mContext;
private OfferListner offerListner;

public OfferAdapter(Context context, int resource, List<OfferModel> items,OfferListner offerListner) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.offerListner=offerListner;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
        LayoutInflater vi;
        vi = LayoutInflater.from(mContext);
        v = vi.inflate(resourceLayout, null);
        }

         OfferModel item  = getItem(position);

        if ( item != null) {
        TextView name = (TextView) v.findViewById(R.id.offer_name);
        TextView state = (TextView) v.findViewById(R.id.offer_state);
        TextView delete = (TextView) v.findViewById(R.id.offer_delete);

        if (name != null) {
            name.setText(String.valueOf(item.getProduct_name()));

        }

        if (state != null) {
            state.setText(String.valueOf(item.isActive()));
        }

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerListner.onClickState(position);
                Log.e("idadapter",item.getId());
            }
        });

         delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 offerListner.onClickDelte(position);
             }
         });

        }

        return v;
        }
        public interface  OfferListner{
        void onClickState(int postion);
        void  onClickDelte(int postion);
        }

        }