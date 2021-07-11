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

public class ShopAdapter extends BaseAdapter {
    private List<Shop> shops;
    private final LayoutInflater inflater;
    private Activity activity;
    public ShopAdapter(Context context, List<Shop> lista) {
        super();
        shops = lista;
        this.activity = (Activity)context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return shops.size();
    }

    @Override
    public Object getItem(int position) {
        return shops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View rowView = inflater.inflate(R.layout.shop_list_single_row, parent, false);
        final Shop shop = (Shop) getItem(position);

        TextView name = rowView.findViewById(R.id.textViewSingle);
        name.setText(shop.getName());

        Button removeView = rowView.findViewById(R.id.deleteShopBtn);
        removeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shops.remove(shop);
                db.Dao().deleteShop(shop);
                ShopAdapter adapter = (ShopAdapter)ShopList.lv.getAdapter();
                adapter.RefreshDB();
            }
        });

        Button editView = rowView.findViewById(R.id.editShopBtn);
        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddShopActivity.class);

                String stare = shop.getName();
                intent.putExtra("Name", stare);
                stare = shop.getAddress();
                intent.putExtra("Address", stare);
                intent.putExtra("Id", shop.getId());
                ShopAdapter adapter = (ShopAdapter)ShopList.lv.getAdapter();
                activity.startActivity(intent);
                adapter.RefreshDB();
            }
        });


        return rowView;
    }
    public void RefreshDB(){
        shops = db.Dao().GetAllShops();
        super.notifyDataSetChanged();
    }
}
