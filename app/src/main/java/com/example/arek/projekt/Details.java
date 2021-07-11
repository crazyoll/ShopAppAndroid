package com.example.arek.projekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.arek.projekt.MainActivity.db;

public class Details extends AppCompatActivity {
    private int id;
    private TextView quant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);
        final Product product = db.Dao().GetProductById(id);
        TextView name = findViewById(R.id.productNameText);
        name.setText(product.getName());
        TextView price = findViewById(R.id.productPriceText);
        String Text = "Cena: " + Double.toString(product.getPrice());
        price.setText(Text);
        quant = findViewById(R.id.productQuantityText );
        Text = "Pozostało: " + Integer.toString(product.getQuantity());
        quant.setText(Text);
        Button buyBtn = findViewById(R.id.productBuyBtn);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyProduct(product);
            }
        });
    }
    private void BuyProduct(Product product)
    {
        int quantity = product.getQuantity() - 1;
        if(quantity > 0)
        {
            product.setQuantity(quantity);
            db.Dao().UpdateProduct(product);
            String Text = "Pozostało: " + Integer.toString(product.getQuantity());
            quant.setText(Text);
        }
        else
        {
            db.Dao().deleteProduct(product);
            finish();
        }
    }

}
