package com.example.arek.projekt;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.List;

import static com.example.arek.projekt.MainActivity.db;

public class ShopList extends AppCompatActivity {
    public static ListView lv;

    private List<Shop> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lista = db.Dao().GetAllShops();

        setContentView(R.layout.activity_shop_list);
        lv = findViewById(R.id.lista);
        lv.setAdapter(new ShopAdapter(this, lista));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent productActivity = new Intent(ShopList.this, ProductList.class);
                //id sklepu
                productActivity.putExtra("Id",lista.get(arg2).getId());
                startActivity(productActivity);
            }

        });
        Button addBTN = findViewById(R.id.addShopBtn);
        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddShopBtn();
            }
        });
        lv.setLongClickable(true);
        //if(CheckServices())
        /*lv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent registerActivity = new Intent(ShopList.this, MapActivity.class);
                startActivity(registerActivity);
                return true;
            }
        });*/

    }
    private void AddShopBtn()
    {
        Intent addShopActivity = new Intent(this, AddShopActivity.class);
        startActivity(addShopActivity);
    }

}
