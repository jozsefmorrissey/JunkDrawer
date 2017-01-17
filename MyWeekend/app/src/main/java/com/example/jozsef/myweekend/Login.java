package com.example.jozsef.myweekend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Login extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        loginSubmission();


    }

    public void loginSubmission() {


        Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Toast.makeText(Login.this, "You clicked It!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Login.this, Event_List.class));

            }
        });

    }



    }

