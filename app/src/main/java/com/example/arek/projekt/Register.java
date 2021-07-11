package com.example.arek.projekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.arek.projekt.MainActivity.db;

public class Register extends AppCompatActivity
{
    private Button registerBTN;
    EditText nameText;
    EditText surnameText;
    EditText addressText;
    EditText loginText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerBTN = (Button) findViewById(R.id.RegisterUserBtn);
        nameText = (EditText) findViewById(R.id.nameText);
        surnameText = (EditText) findViewById(R.id.surnameText);
        addressText = (EditText) findViewById(R.id.addressText);
        loginText = (EditText) findViewById(R.id.loginText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        registerBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RegisterNewUser();

            }
        });
    }
    private void RegisterNewUser()
    {
        Client client = new Client();
        Client findClient = db.Dao().FindClient(loginText.getText().toString());
        if(findClient == null)
        {}
        else
        if(loginText.getText().toString().equals(findClient.getLogin()))
        {
            Toast.makeText(this,"Uzytkownik juz istnieje!",Toast.LENGTH_SHORT).show();
            return;
        }
        client.setFirstName(nameText.getText().toString());
        client.setLastName(nameText.getText().toString());
        client.setAddress(addressText.getText().toString());
        client.setLogin(loginText.getText().toString());
        client.setPassword(passwordText.getText().toString());

        db.Dao().AddClient(client);
        Toast.makeText(this,"Dodano uzytkownika!",Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
