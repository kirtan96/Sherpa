package com.sherpa.sherpa.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.sherpa.sherpa.R;

public class PayPal_Selection extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal__selection);

        Button button_paypal_sdk= (Button) findViewById(R.id.button_paypal_sdk);
        button_paypal_sdk.setOnClickListener(this);

       Button button_paypal_mpl= (Button) findViewById(R.id.button_paypal_mpl);
        button_paypal_mpl.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_paypal_sdk:
                // call paypal sdk intent
                startActivity(new Intent(PayPal_Selection.this,PayPal_sdk.class));

                break;

           case R.id.button_paypal_mpl:
                //call mpl activity

                startActivity(new Intent(PayPal_Selection.this,checkout.class));

                break;

            default:

                break;



        }

    }

}
