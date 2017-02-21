package com.example.arthur.social;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class OpportunityForm extends AppCompatActivity{

    DatabaseHelper myDb;
    EditText editName, editTel, editProduct, editPCValue;
//    Button btnAddData, btnnext;
//    private ListView mDrawerList;
//    private DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mDrawerToggle;
//
//
//    private CharSequence mDrawerTitle;
//    private CharSequence mTitle;

    private SQLiteDatabase db;

//    Button btnInsertImage;
//    ImageView imgThumbnail;
//
//    private static final int PICK_CAMERA = 1001;
//    String m_imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opportunityform_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Opportunity");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_32dp);

        createDatabaseOpportunity();


        editProduct = (EditText) findViewById(R.id.editText_product);
//        editAddress = (EditText) findViewById(R.id.editText_address1);
        editPCValue = (EditText) findViewById(R.id.editText_pcvalue);
//        editRemark = (EditText) findViewById(R.id.editText_remark);

//        btnAddData = (Button)findViewById(R.id.button_add);
//
//        btnnext = (Button)findViewById(R.id.button_next);
//        btnnext .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i;
//                i = new Intent(BusinessPartnerForm.this, ExpandableSocialListViewActivity.class);
//                startActivity(i);
//            }
//        });
//
//        btnInsertImage = (Button) findViewById(R.id.button_insert);
//        imgThumbnail = (ImageView) findViewById(R.id.img_thumbnail);
//        imgThumbnail.setVisibility(View.GONE);

        initData();
//        AddData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    private void initData() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getApplicationContext()).build();
        ImageLoader.getInstance().init(config);
    }

//    public void AddData() {
//        btnAddData.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        insertIntoDBBusinessPartner();
//                    }
//                }
//        );
//
//        btnInsertImage.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                ImageUtil.doTakePhotoFromGallery(BusinessPartnerForm.this, PICK_CAMERA + 1);
//            }
//        });
//    }

    protected void createDatabaseOpportunity(){
        db=openOrCreateDatabase("/data/data/com.example.arthur.social/bpmaster2", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS opportunity(opportunity_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, agency_id VARCHAR, agent_id VARCHAR, bpartner_id VARCHAR, name VARCHAR, description VARCHAR,status VARCHAR,potentialclosingvalue int,product_id VARCHAR,created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,remark VARCHAR);");
    }

    protected void insertIntoDBOpportunity() {
        String product = editProduct.getText().toString().trim();
//        String address = editAddress.getText().toString().trim();
        String pcvalue = editPCValue.getText().toString().trim();
//        String remark = editRemark.getText().toString().trim();

//        if(name.equals("") || address.equals("") || tel.equals("") || remark.equals("")){
        if(product.equals("") ||  pcvalue.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields",Toast.LENGTH_LONG).show();
            return;
        }

//        if (m_imagePath.equals(""))
//        {
//            Toast.makeText(getApplicationContext(),"Please select image",Toast.LENGTH_LONG).show();
//            return;
//        }

        String query = "INSERT INTO opportunity (product_id , potentialclosingvalue , agency_id, agent_id) VALUES('"+product+"', '"+pcvalue+"' , '1', '1' );";
        db.execSQL(query);
        Toast.makeText(getApplicationContext(),"Saved Successfully", Toast.LENGTH_LONG).show();

        Intent i;
        i = new Intent(OpportunityForm.this, Opportunity_prototype.class);
        i.putExtra("product",product);
        i.putExtra("pcvalue",pcvalue);
        OpportunityForm.this.startActivity(i);

    }

//    private class DrawerItemClickListener implements
//            ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position,
//                                long id) {
//            Toast.makeText(BusinessPartnerForm .this,
//                    "You selected position: " + position, Toast.LENGTH_SHORT)
//                    .show();
//            mDrawerLayout.closeDrawer(mDrawerList);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
//        return super.onOptionsItemSelected(item);

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            insertIntoDBOpportunity();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == 0)
//            return;
//
//        if (requestCode == PICK_CAMERA + 1){
//            Uri selectedImage = data.getData();
//
//            CropImage.activity(selectedImage).start(this);
//        }
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == Activity.RESULT_OK){
//                Uri resultUri = result.getUri();
//
//                m_imagePath = ImageUtil.getPathFromURI(this, resultUri);
//
//                ImageUtil.displayRoundImage(imgThumbnail, "file://" + m_imagePath, null);
//
//                imgThumbnail.setVisibility(View.VISIBLE);
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
