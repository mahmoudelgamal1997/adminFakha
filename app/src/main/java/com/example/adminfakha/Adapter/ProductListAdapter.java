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

public class ProductListAdapter extends ArrayAdapter<OfferModel> {

    private int resourceLayout;
    private Context mContext;
    private ProductListner productListner;

    public ProductListAdapter(Context context, int resource, List<OfferModel> items,ProductListner offerListner) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.productListner =offerListner;
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




            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    productListner.onClickDelte(position);
                }
            });

        }

        return v;
    }
    public interface  ProductListner{
        void  onClickDelte(int postion);
    }

}