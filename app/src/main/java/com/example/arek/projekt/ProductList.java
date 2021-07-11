package com.example.arek.projekt;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.arek.projekt.MainActivity.db;

public class ProductList extends AppCompatActivity {
    public static ListView lv;
    private List<Product> lista;
    //private Button addBtn;
    private int id;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        lv = findViewById(R.id.listViewProducts);
        final Intent intent = getIntent();
        //id sklepu
        id = intent.getIntExtra("Id", -1);
        lista = db.Dao().GetAllProducts(id);
        this.lv.setAdapter(new ProductAdapter(ProductList.this, lista));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent detailstActivity = new Intent(ProductList.this, Details.class);
                detailstActivity.putExtra("Id",lista.get(arg2).getId());
                startActivity(detailstActivity);
            }

        });
        Button addBtn = findViewById(R.id.addItemBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProduct();
            }
        });
        ImageView mapBtn = findViewById(R.id.mapBtn);
        if(CheckServices())
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapActivity = new Intent(ProductList.this, MapActivity.class);
                mapActivity.putExtra("Id",id);
                startActivity(mapActivity);

            }
        });
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                handleShakeEvent();
            }
        });

    }
    private void handleShakeEvent()
    {
        if(lista.size() == 0)
            return;
        Random random = new Random();
        int index = random.nextInt(lista.size());
        Product radomProduct = lista.get(index);
        Intent radomActivity = new Intent(ProductList.this, Details.class);
        radomActivity.putExtra("Id",radomProduct.getId());
        startActivity(radomActivity);
    }
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
    private void AddProduct()
    {
        Intent addProductActivity = new Intent(this, AddProductActivity.class);
        addProductActivity.putExtra("ShopId", id);
        startActivity(addProductActivity);
    }
    public  boolean CheckServices()
    {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ProductList.this);
        if(available == ConnectionResult.SUCCESS)
        {
            Toast.makeText(this, "mozna robic map requesty",Toast.LENGTH_SHORT).show();
            return true;
        }

        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ProductList.this, available, 9001);
            dialog.show();
        }
        else Toast.makeText(this, "nie mozna robic map requestow",Toast.LENGTH_SHORT).show();
        return false;
    }
}
