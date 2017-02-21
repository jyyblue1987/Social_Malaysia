package com.example.arthur.social;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {


    Button btnAgency, btnAgent, btnOpportunity, btnActivity, btnBusinessPartner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);

        TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);

        btnAgency = (Button) findViewById(R.id.button_agency);
        btnAgency .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(UserAreaActivity.this, AgencyForm.class);
                startActivity(i);
            }
        });

        btnOpportunity = (Button) findViewById(R.id.button_opportunity);
        btnOpportunity .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(UserAreaActivity.this, OpportunityExpandableListView.class);
                startActivity(i);
            }
        });

        btnBusinessPartner = (Button) findViewById(R.id.button_business_partner);
        btnBusinessPartner .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(UserAreaActivity.this, ExpandableSocialListViewActivity.class);
                startActivity(i);
            }
        });


        // Display user details
        String message = name + " welcome to your user area";
        tvWelcomeMsg.setText(message);

    }
}
