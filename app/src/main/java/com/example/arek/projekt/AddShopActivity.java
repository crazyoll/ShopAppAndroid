package com.example.arek.projekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static com.example.arek.projekt.MainActivity.db;

public class AddShopActivity extends AppCompatActivity {
    EditText nameText, addressText;
    Button addBtn;
    private int id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        nameText = (EditText) findViewById(R.id.nameShopText);
        addressText = (EditText) findViewById(R.id.addressShopText);
        addBtn = (Button) findViewById(R.id.addShopBtn);
        final Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);
        if(id == -1)
        {
            //funkcja dodawania
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddShop();
                }
            });
        }
        else
        {
            nameText.setText(intent.getStringExtra("Name"));
            addressText.setText(intent.getStringExtra("Address"));
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditShop(id);
                }
            });
        }
    }
    private void AddShop()
    {
        Shop shop = new Shop();
        shop.setName(nameText.getText().toString());
        shop.setAddress(addressText.getText().toString());
        db.Dao().AddShop(shop);
        Toast.makeText(this,"Dodano sklep!",Toast.LENGTH_SHORT).show();
        ShopAdapter adapter = (ShopAdapter) ShopList.lv.getAdapter();
        adapter.RefreshDB();
        this.finish();
    }
    private void EditShop(int _id)
    {
        Shop s = new Shop();
        s.setId(_id);
        s.setName(nameText.getText().toString());
        s.setAddress(addressText.getText().toString());
        db.Dao().UpdateShop(s);
        ShopAdapter adapter = (ShopAdapter) ShopList.lv.getAdapter();
        adapter.RefreshDB();
        this.finish();
    }

}
