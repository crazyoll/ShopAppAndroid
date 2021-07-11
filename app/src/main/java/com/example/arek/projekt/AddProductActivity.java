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

import static com.example.arek.projekt.MainActivity.db;

public class AddProductActivity extends AppCompatActivity {
    Button addBtn;
    int idShop, id;
    EditText nameText, quantityText, priceText, photoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        nameText = (EditText) findViewById(R.id.nameProductText);
        quantityText = (EditText) findViewById(R.id.quantityProductText);
        priceText = (EditText) findViewById(R.id.priceProductText);
        photoText = (EditText) findViewById(R.id.photoProductText);
        addBtn = (Button) findViewById(R.id.addProductBtn);

        final Intent intent = getIntent();
        //idsklepu
        idShop = intent.getIntExtra("ShopId", -1);
        //id produktu
        id = intent.getIntExtra("Id", -1);
        if(id == -1)
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProduct();
            }
        });
        else
        {
            nameText.setText(intent.getStringExtra("Name"));
            quantityText.setText(intent.getStringExtra("Address"));
            priceText.setText(intent.getStringExtra("Address"));
            photoText.setText(intent.getStringExtra("Address"));
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditProduct(id);
                }
            });
        }
    }
    private void AddProduct()
    {
        Product p = new Product();
        p.setIdShop(idShop);
        p.setName(nameText.getText().toString());
        p.setQuantity(Integer.parseInt(quantityText.getText().toString()));
        p.setPrice(Double.parseDouble(priceText.getText().toString()));
        //p.setphoyo
        db.Dao().AddProduct(p);
        Toast.makeText(this,"Dodano produkt!",Toast.LENGTH_SHORT).show();
        ProductAdapter adapter = (ProductAdapter) ProductList.lv.getAdapter();
        adapter.RefreshDB();
        this.finish();
    }
    private void EditProduct(int _id)
    {

        Product p = new Product();
        p.setIdShop(idShop);
        p.setId(_id);
        p.setName(nameText.getText().toString());
        p.setQuantity(Integer.parseInt(quantityText.getText().toString()));
        p.setPrice(Double.parseDouble(priceText.getText().toString()));
        db.Dao().UpdateProduct(p);
        ProductAdapter adapter = (ProductAdapter) ProductList.lv.getAdapter();
        adapter.RefreshDB();
        Toast.makeText(this,"edytowano produkt!",Toast.LENGTH_SHORT).show();
        this.finish();
    }

}
