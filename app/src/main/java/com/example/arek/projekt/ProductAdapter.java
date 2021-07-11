package com.example.arek.projekt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.arek.projekt.MainActivity.db;

public class ProductAdapter extends BaseAdapter {
    private List<Product> products;
    private final LayoutInflater inflater;
    private Activity activity;
    public ProductAdapter(Context context, List<Product> lista) {
        super();
        products = lista;
        this.activity = (Activity)context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View rowView = inflater.inflate(R.layout.shop_list_single_row, parent, false);
        final Product product = (Product) getItem(position);

        TextView name = rowView.findViewById(R.id.textViewSingle);
        name.setText(product.getName());

        Button removeView = rowView.findViewById(R.id.deleteShopBtn);
        removeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products.remove(product);
                db.Dao().deleteProduct(product);
                ProductAdapter adapter = (ProductAdapter)ProductList.lv.getAdapter();
                adapter.RefreshDB();
            }
        });

        Button editView = rowView.findViewById(R.id.editShopBtn);
        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddShopActivity.class);


                intent.putExtra("Name", product.getName());
                intent.putExtra("Price", product.getPrice());
                intent.putExtra("Quantity", product.getQuantity());
                intent.putExtra("ShopId", product.getIdShop());
                intent.putExtra("Id", product.getId());
                activity.startActivity(intent);
            }
        });


        return rowView;
    }
    public void RefreshDB(){
        products = db.Dao().GetAllProducts(2);

        super.notifyDataSetChanged();
    }
}
