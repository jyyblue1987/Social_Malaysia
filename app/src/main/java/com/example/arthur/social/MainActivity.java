package com.example.arthur.social;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.arthur.social.adapter.DrawerSocialAdapter;
import com.example.arthur.social.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.theartofdev.edmodo.cropper.CropImage;

public class MainActivity extends ActionBarActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        createDatabase();
    }
//
//    protected void createDatabase(){
//        db=openOrCreateDatabase("/data/data/com.example.arthur.social/bpmaster2", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING, null);
//        db.execSQL("CREATE TABLE IF NOT EXISTS bpartner(bpartner_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, agency_id VARCHAR not null, agent_id VARCHAR NOT NULL,isclient varchar,name VARCHAR,address1 VARCHAR,address2 VARCHAR,city VARCHAR,state VARCHAR,country VARCHAR,zip VARCHAR,email VARCHAR,mobile VARCHAR,dateregistration TIMESTAMP DEFAULT CURRENT_TIMESTAMP,gender VARCHAR,occupation VARCHAR,race VARCHAR,religion VARCHAR,ismarried VARCHAR,numberofchildren VARCHAR,remark VARCHAR,profileimg VARCHAR);");
//    }



}
