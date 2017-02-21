package com.example.arthur.social;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arthur on 24/1/2017.
 */

public class businesspartner_prototype extends AppCompatActivity {

    EditText editName, editContact, editEmail, editAddress;
    CheckBox checkMarried, checkChildren, checkRace, checkReligion;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businesspartnerform_prototype);

        Intent i = getIntent();
        String parent_bp_row_id = i.getStringExtra("bp_row_id");
//        String parent_name = i.getStringExtra("name");
//        String parent_mobile = i.getStringExtra("mobile");

        createDatabaseBusinessPartner();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit BP");



        editName = (EditText) findViewById(R.id.editText_name);
        editContact = (EditText) findViewById(R.id.editText_contact);
        editEmail = (EditText) findViewById(R.id.editText_email);
        editAddress = (EditText) findViewById(R.id.editText_address);

        checkMarried = (CheckBox) findViewById(R.id.checkBox_married);
        checkChildren = (CheckBox) findViewById(R.id.checkBox_children);
        checkRace = (CheckBox) findViewById(R.id.checkBox_race);
        checkReligion = (CheckBox) findViewById(R.id.checkBox_religion);

//
//        Cursor c = db.rawQuery("SELECT * FROM bpartner WHERE bpartner_id = '"+parent_bp_row_id+"'", null);
//
//        c.moveToFirst();
//
//        editName.setText(c.getString(c.getColumnIndex("name")));

//        getDatabaseBusinessPartner(parent_bp_row_id);

//        String message = "Parent is " + parent + " Child is " + child;
//        editName.setText(message);
//
//        editName.setText(parent_name);
//        editContact.setText(parent_mobile);


    }

//    private static class GroupItem {
//        String agency_id;
//        String agent_id;
//        String isclient;
//        String name;
//        String address1;
//        String address2;
//        String city;
//        String state;
//        String country;
//        String zip;
//        String email;
//        String mobile;
//        String dateregistration;
//        String gender;
//        String occupation;
//        String race;
//        String religion;
//        String ismarried;
//        String numberofchildren;
//        String remark;
//        //profileimg
//    }

    protected void createDatabaseBusinessPartner(){
        db=openOrCreateDatabase("/data/data/com.example.arthur.social/bpmaster2", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS bpartner(bpartner_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, agency_id VARCHAR not null, agent_id VARCHAR NOT NULL,isclient varchar,name VARCHAR,address1 VARCHAR,address2 VARCHAR,city VARCHAR,state VARCHAR,country VARCHAR,zip VARCHAR,email VARCHAR,mobile VARCHAR,dateregistration TIMESTAMP DEFAULT CURRENT_TIMESTAMP,gender VARCHAR,occupation VARCHAR,race VARCHAR,religion VARCHAR,ismarried VARCHAR,numberofchildren VARCHAR,remark VARCHAR,profileimg VARCHAR);");
    }

//    protected void getDatabaseBusinessPartner(int parent_bp_row_id) {
//
//        GroupItem item;
//        dbHelper=new DatabaseHelper(this);
//
//        try {
//            dbHelper.checkAndCopyDatabase();
//            dbHelper.openDatabase();
//        }catch (SQLException e){
//
//        }
//
//        try {
////            Cursor cursor=dbHelper.QueryData("select agency_id, agent_id, isclient, name, address1, address2," +
////                    "city, state, country, zip, email, mobile, dateregistration, gender, occupation, race, religion, ismarried, numberofchildren, remark FROM opportunity " +
////                    "where bpartner_id=1" );
//
//            Cursor cursor=dbHelper.QueryData("select * from bpartner where bpartner_id='"+parent_bp_row_id+"';" );
//            if ( cursor !=null){
//                if (cursor.moveToFirst()) {
//                    item = new GroupItem();
//                    item.agency_id = cursor.getString(1);
//                    item.agent_id = cursor.getString(2);
//                    item.isclient = cursor.getString(3);
//                    item.name = cursor.getString(4);
//                    item.address1 = cursor.getString(5);
//                    item.address2 = cursor.getString(6);
//                    item.city = cursor.getString(7);
//                    item.state = cursor.getString(8);
//                    item.country = cursor.getString(9);
//                    item.zip = cursor.getString(10);
//                    item.email = cursor.getString(11);
//                    item.mobile = cursor.getString(12);
//                    item.dateregistration = cursor.getString(13);
//                    item.gender = cursor.getString(14);
//                    item.occupation = cursor.getString(15);
//                    item.race = cursor.getString(16);
//                    item.religion = cursor.getString(17);
//                    item.ismarried = cursor.getString(18);
//                    item.numberofchildren = cursor.getString(19);
//                    item.remark = cursor.getString(20);
//
//                    editName.setText(item.name);
//
////                    items.add(item);
//                }
//            }
//        }catch (SQLException e){}
//
////        return items;
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            finish();
            Intent i;
            i = new Intent(businesspartner_prototype.this, ExpandableSocialListViewActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
