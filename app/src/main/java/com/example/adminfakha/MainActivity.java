package com.example.adminfakha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button but_ads,but_product,but_offer,but_change_offer,but_change_product,but_add_area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but_ads=(Button)findViewById(R.id.but_ads);
        but_product=findViewById(R.id.but_product);
        but_offer=findViewById(R.id.but_offer);
        but_change_offer=findViewById(R.id.but_change_offer);
        but_change_product=findViewById(R.id.but_change_product);
        but_add_area=findViewById(R.id.but_add_area);

        but_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AdsActivity.class);
                startActivity(intent);
            }
        });

        but_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProductActivity.class);
                startActivity(intent);
            }
        });


        but_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OfferActivity.class);
                startActivity(intent);
            }
        });


        but_change_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OfferState.class);
                startActivity(intent);
            }
        });


        but_change_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProductState.class);
                startActivity(intent);
            }
        });

        but_add_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AreaActivity.class);
                startActivity(intent);
            }
        });

    }
}