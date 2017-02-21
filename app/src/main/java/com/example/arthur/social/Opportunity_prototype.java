package com.example.arthur.social;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Arthur on 17/2/2017.
 */

public class Opportunity_prototype extends AppCompatActivity{

    DatabaseHelper myDb;
    EditText editName,editDescription, editStatus, editPotentialClosingValue, editProduct, editRemark;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opportunityform_prototype);

        Intent i = getIntent();
        String parent_product = i.getStringExtra("product");
        String parent_pcvalue = i.getStringExtra("pcvalue");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Opportunity");

        createDatabaseOppoturnity();

        editName = (EditText)findViewById(R.id.editText_oppname);
        editDescription = (EditText)findViewById(R.id.editText_oppdesc);
        editStatus = (EditText)findViewById(R.id.editText_oppstatus);
        editPotentialClosingValue = (EditText)findViewById(R.id.editText_opppcv);
        editProduct = (EditText) findViewById(R.id.editText_oppproduct);
        editRemark = (EditText) findViewById(R.id.editText_oppremark);

        editProduct.setText(parent_product);
        editPotentialClosingValue.setText(parent_pcvalue);

        initData();
    }

    private void initData() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getApplicationContext()).build();
        ImageLoader.getInstance().init(config);

    }

    protected void createDatabaseOppoturnity(){
        db=openOrCreateDatabase("/data/data/com.example.arthur.social/bpmaster2", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS opportunity(opportunity_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, agency_id VARCHAR, agent_id VARCHAR, bpartner_id VARCHAR, name VARCHAR, description VARCHAR,status VARCHAR,potentialclosingvalue int,product_id VARCHAR,created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,remark VARCHAR);");
    }

    protected void insertIntoDBOppoturnity(){
        String name = editName.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String status = editStatus.getText().toString().trim();
        String pcv = editPotentialClosingValue.getText().toString().trim();
        String product = editProduct.getText().toString().trim();
        String remark = editRemark.getText().toString().trim();

        if(name.equals("") || description.equals("") || status.equals("") || pcv.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        //   if( m_imagePath.equals("") )
        //   {
        //       Toast.makeText(getApplicationContext(),"Please select image", Toast.LENGTH_LONG).show();
        //       return;
        //  }

        String query = "INSERT INTO opportunity (agency_id, agent_id, bpartner_id, name, description, status, potentialclosingvalue, product_id, remark) VALUES('1','1','1', '"+name+"', '"+description+"', '"+status+"' , '"+pcv+"', '"+product+"', '"+remark+"');";
        db.execSQL(query);
        Toast.makeText(getApplicationContext(),"Saved Successfully " , Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            finish();
            Intent i;
            i = new Intent(Opportunity_prototype.this, OpportunityExpandableListView.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
