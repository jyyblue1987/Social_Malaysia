package com.example.arthur.social;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.arthur.social.adapter.DrawerSocialAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class AgencyForm extends ActionBarActivity {
    DatabaseHelper myDb;
    EditText editName,editAddress, editTel, editAgencyGroup;
    Button btnAddData, btnnext;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private SQLiteDatabase db;

   // Button  btnInsertImage;
   // ImageView imgThumbnail;

    //private static final int    PICK_CAMERA = 1001;
   // String          m_imagePath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agencyform_main);
        //myDb = new DatabaseHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.list_view);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        View headerView = getLayoutInflater().inflate(
                R.layout.header_navigation_drawer_social, mDrawerList, false);
        mDrawerList.addHeaderView(headerView);// Add header before adapter (for
        // pre-KitKat)
        mDrawerList.setAdapter(new DrawerSocialAdapter(this));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        int color = getResources().getColor(R.color.material_grey_100);
        color = Color.argb(0xCD, Color.red(color), Color.green(color),
                Color.blue(color));
        mDrawerList.setBackgroundColor(color);
        mDrawerList.getLayoutParams().width = (int) getResources()
                .getDimension(R.dimen.drawer_width_social);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        if (savedInstanceState == null) {
            mDrawerLayout.openDrawer(mDrawerList);
        }

//        createDatabaseAgency();
//
//        editName = (EditText)findViewById(R.id.editText_agencyname);
//        editAddress = (EditText)findViewById(R.id.editText_agencyaddress1);
//        editTel = (EditText)findViewById(R.id.editText_agencytel);
//        editAgencyGroup = (EditText)findViewById(R.id.editText_agencygroup);
//
//        // editMarks = (EditText)findViewById(R.id.editText_Marks);
//        btnAddData = (Button)findViewById(R.id.button_add);
//        btnnext = (Button)findViewById(R.id.button_next);
//        btnnext .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i;
//                i = new Intent(AgencyForm.this, ExpandableSocialListViewActivity.class);
//                startActivity(i);
//            }
//        });
//
//       // btnInsertImage = (Button) findViewById(R.id.button_insert);
//      //  imgThumbnail = (ImageView) findViewById(R.id.img_thumbnail);
//      //  imgThumbnail.setVisibility(View.GONE);
//
//        initData();
//        AddData();
    }

    private void initData() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getApplicationContext()).build();
        ImageLoader.getInstance().init(config);

    }

    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this,editName.getText().toString() + "|" + editSurname.getText().toString() + "|" + editDays.getText().toString() + "|" + editStatus.getText().toString() + "|",Toast.LENGTH_LONG).show();
                        insertIntoDBAgency();

              /*          boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString() );
                        if(isInserted =true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show(); */
                    }
                }
        );

    //    btnInsertImage.setOnClickListener(new View.OnClickListener() {
       //     public void onClick(View v) {
         //       ImageUtil.doTakePhotoFromGallery(AgencyForm.this, PICK_CAMERA + 1);
       //     }
      //  });
    }


    protected void createDatabaseAgency(){
        db=openOrCreateDatabase("/data/data/com.example.arthur.social/bpmaster2", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS agency(agency_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR,address1 VARCHAR,address2 VARCHAR,city VARCHAR,state VARCHAR,country VARCHAR,zip VARCHAR,tel VARCHAR,fax VARCHAR,created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,insurer VARCHAR,agencygroup_id VARCHAR);");
    }


    protected void insertIntoDBAgency(){
        String name = editName.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        String tel = editTel.getText().toString().trim();
        String agencygroup = editAgencyGroup.getText().toString().trim();

        if(name.equals("") || address.equals("") || tel.equals("") || agencygroup.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

     //   if( m_imagePath.equals("") )
     //   {
     //       Toast.makeText(getApplicationContext(),"Please select image", Toast.LENGTH_LONG).show();
     //       return;
      //  }

        String query = "INSERT INTO agency (name, address1, tel, agencygroup_id) VALUES('"+name+"', '"+address+"', '"+tel+"' , '"+agencygroup+"');";
        db.execSQL(query);
        Toast.makeText(getApplicationContext(),"Saved Successfully", Toast.LENGTH_LONG).show();
    }


    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(AgencyForm .this,
                    "You selected position: " + position, Toast.LENGTH_SHORT)
                    .show();
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }



}