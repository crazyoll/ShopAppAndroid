package com.example.arek.projekt;

import android.app.Dialog;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
{
    public static AppDatabase db;
    private Button registerBTN;
    private Button loginBTN;
    private TextView login;
    private TextView passowrd;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"ShopDB").allowMainThreadQueries().build();
        setContentView(R.layout.activity_main);
        registerBTN = (Button) findViewById(R.id.RegisterBtn);
        loginBTN = (Button) findViewById(R.id.LoginBtn);
        registerBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenActivityRegister();
            }
        });
        loginBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenActivityLogin();
            }
        });
    }
    private void OpenActivityRegister()
    {
        Intent registerActivity = new Intent(this, Register.class);
        startActivity(registerActivity);
    }
    private void OpenActivityLogin()
    {
        login = (TextView) findViewById(R.id.LoginText);
        passowrd = (TextView) findViewById(R.id.PasswordText);
        Client loginAtempt = db.Dao().FindClient(login.getText().toString());
        if(loginAtempt == null)
            Toast.makeText(this,"Podaj login i haslo!",Toast.LENGTH_SHORT).show();
        else
        if(loginAtempt.getPassword().equals(passowrd.getText().toString()))
        {
            Toast.makeText(this,"Zalogowano!",Toast.LENGTH_SHORT).show();
            Intent shopActivity = new Intent(this, ShopList.class);
            startActivity(shopActivity);
        }
    }

}