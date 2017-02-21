package com.example.arthur.social;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arthur.social.util.ImageUtil;
import com.example.arthur.social.view.AnimatedExpandableListView;
import com.example.arthur.social.view.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpandableSocialListViewActivity extends AppCompatActivity {

	// private AnimatedExpandableListView listView;
	private AnimatedExpandableListView listView;
	private ExampleAdapter adapter;

	private DatabaseHelper dbHelper;
	private ImageView mImage;
	private TextView mName;
	private TextView mPlace;

	private SQLiteDatabase db;
	private List<GroupItem> list_Backup = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
		ImageLoader.getInstance().init(config);

		setContentView(R.layout.activity_expandable_list_view_social);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
				Intent i;
				i = new Intent(ExpandableSocialListViewActivity.this, BusinessPartnerForm.class);
				startActivity(i);

			}
		});

		createDatabaseBusinessPartner();

		mImage = (ImageView) findViewById(R.id.expandable_lv_social_image);
		mName = (TextView) findViewById(R.id.expandable_lv_social_name);
		mPlace = (TextView) findViewById(R.id.expandable_lv_social_place);

		ImageUtil.displayRoundImage(mImage,
				"http://pengaja.com/uiapptemplate/newphotos/profileimages/2.jpg", null);

		List<GroupItem> items = new ArrayList<GroupItem>();
		items = fillData(items);

		list_Backup = items;

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Leads and Prospects");

		adapter = new ExampleAdapter(this);
		adapter.setData(items);

		listView = (AnimatedExpandableListView) findViewById(R.id.expandable_lv_social_list_view);
		listView.setAdapter(adapter);

		// In order to show animations, we need to use a custom click handler
		// for our ExpandableListView.
		listView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
										int groupPosition, long id) {
				// We call collapseGroupWithAnimation(int) and
				// expandGroupWithAnimation(int) to animate group
				// expansion/collapse.
				if (listView.isGroupExpanded(groupPosition)) {
					listView.collapseGroupWithAnimation(groupPosition);
				} else {
					listView.expandGroupWithAnimation(groupPosition);
				}
				return true;
			}

		});

		// Set indicator (arrow) to the right
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		Resources r = getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				50, r.getDisplayMetrics());
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
			listView.setIndicatorBounds(width - px, width);
		} else {
			listView.setIndicatorBoundsRelative(width - px, width);
		}
	}

	private static class GroupItem {
		String title;
		int icon = R.string.material_icon_friends;
		int parID;
		String surname;
		String days;
		String status;
		String thumbnail;
		String imageUrl;
		String subject;
		String bpartner_id;

		List<ChildItem> items = new ArrayList<ChildItem>();
	}

	private static class ChildItem {
		String title;
		int childparent;
	}

	private static class ChildHolder {
		TextView title;
		ImageView image;
		LinearLayout layout;
	}

	private static class GroupHolder {
		TextView title;
		TextView surname;
		ImageView icon;
		TextView days;
		TextView status;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
//			finish();
			Intent i;
			i = new Intent(ExpandableSocialListViewActivity.this, UserAreaActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void createDatabaseBusinessPartner(){
		db=openOrCreateDatabase("/data/data/com.example.arthur.social/bpmaster2", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS bpartner(bpartner_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, agency_id VARCHAR not null, agent_id VARCHAR NOT NULL,isclient varchar,name VARCHAR,address1 VARCHAR,address2 VARCHAR,city VARCHAR,state VARCHAR,country VARCHAR,zip VARCHAR,email VARCHAR,mobile VARCHAR,dateregistration TIMESTAMP DEFAULT CURRENT_TIMESTAMP,gender VARCHAR,occupation VARCHAR,race VARCHAR,religion VARCHAR,ismarried VARCHAR,numberofchildren VARCHAR,remark VARCHAR,profileimg VARCHAR);");
	}

	private List<GroupItem> fillData(List<GroupItem> items) {

		GroupItem item;
		ChildItem child;
		dbHelper=new DatabaseHelper(this);

		try {
			dbHelper.checkAndCopyDatabase();
			dbHelper.openDatabase();
		}catch (SQLException e){

		}
		try {
//			Cursor cursor=dbHelper.QueryData("select bpartner.name, opportunity.name, opportunity.created , opportunity.status, activity.activitytype_id, bpartner.profileimg, activity.subject FROM activity" +
//					" LEFT OUTER JOIN opportunity ON opportunity.opportunity_id = activity.opportunity_id" +
//					" LEFT OUTER JOIN bpartner ON opportunity.bpartner_id=bpartner.bpartner_id");

			Cursor cursor=dbHelper.QueryData("select bpartner.name, bpartner.bpartner_id FROM bpartner");
			if ( cursor !=null){
				if (cursor.moveToFirst()){
					do {
						item = new GroupItem();
						item.surname = cursor.getString(0);
						item.parID = cursor.getPosition();
						item.bpartner_id = cursor.getString(1);

						//item.title = cursor.getString(1);
						//item.parID = cursor.getPosition();
						//item.surname = cursor.getString(4);
						//item.days = cursor.getString(2);
						//item.status = cursor.getString(4);
						//item.thumbnail = cursor.getString(5);
						//item.subject = cursor.getString(6);

						child = new ChildItem();
//						child.title = "Proceed to Next Phase";
						child.title = "Edit Profile";
						child.childparent = item.parID;
						item.items.add(child);

						child = new ChildItem();
//						child.title = "Delete Prospect";
						child.title = "Create Opportunity";
						child.childparent = item.parID;
						item.items.add(child);

						child = new ChildItem();
						child.title = "Update Prospect Details";
						child.childparent = item.parID;
						item.items.add(child);

						items.add(item);

						// Item item=new Item();
						//  item.setId(cursor.getString(0));
						// item.setTitle(cursor.getString(1));
						//  item.setContent(cursor.getString(2));
						//  arrayList.add(item);
					}while (cursor.moveToNext());
				}
			}
		}catch (SQLException e){}

		return items;
	}

	private void filterList(String filter) {
		if( filter == null || filter.length() < 1 )
		{
			adapter.setData(list_Backup);
		}
		else {
			if (list_Backup != null) {
				List<GroupItem> list = new ArrayList<>();
				for (int i = 0; i < list_Backup.size(); i++) {
					GroupItem item = list_Backup.get(i);
					if( item.surname.contains(filter) )
						list.add(item);
				}

				adapter.setData(list);
			}
		}

		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_search, menu);
		MenuItem item = menu.findItem(R.id.menuSearch);
		SearchView searchView = (SearchView)item.getActionView();

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
//				adapter.getFilter().filter(newText);
				filterList(newText);

				return false;
			}
		});

//		getSupportActionBar().setDisplayHomeAsUpEnabled(false);


		return super.onCreateOptionsMenu(menu);
	}


	private class ExampleAdapter extends AnimatedExpandableListAdapter implements OnClickListener{
		private LayoutInflater inflater;

		private List<GroupItem> items;

		public ExampleAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void setData(List<GroupItem> items) {
			this.items = items;
		}
		public List<GroupItem> getData() {
			return this.items;
		}

		@Override
		public ChildItem getChild(int groupPosition, int childPosition) {
			return items.get(groupPosition).items.get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getRealChildView(int groupPosition, int childPosition,
									 boolean isLastChild, View convertView, ViewGroup parent) {
			ChildHolder holder;
			ChildItem item = getChild(groupPosition, childPosition);
			if (convertView == null) {
				holder = new ChildHolder();
				convertView = inflater.inflate(
						R.layout.list_item_expandable_social_child, parent,
						false);
				holder.title = (TextView) convertView
						.findViewById(R.id.expandable_item_social_child_name);
				holder.image = (ImageView) convertView
						.findViewById(R.id.expandable_item_social_child_image);
				holder.layout = (LinearLayout) convertView
						.findViewById(R.id.list_item_expandable_social_child_layout);
				holder.layout.setOnClickListener(this);
				convertView.setTag(holder);
			} else {
				holder = (ChildHolder) convertView.getTag();
			}
			holder.layout.setTag(R.id.child, childPosition);
			holder.layout.setTag(R.id.parent, item.childparent);

			holder.title.setText(item.title);
			ImageUtil.displayRoundImage(holder.image,
					"http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg", null);

			return convertView;
		}

		@Override
		public int getRealChildrenCount(int groupPosition) {
			return items.get(groupPosition).items.size();
		}

		@Override
		public GroupItem getGroup(int groupPosition) {
			return items.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return items.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
								 View convertView, ViewGroup parent) {
			GroupHolder holder;
			GroupItem item = getGroup(groupPosition);
			if (convertView == null) {
				holder = new GroupHolder();
				convertView = inflater.inflate(
						R.layout.list_item_expandable_social, parent, false);
				holder.title = (TextView) convertView
						.findViewById(R.id.expandable_item_social_name);
				holder.surname = (TextView) convertView
						.findViewById(R.id.expandable_item_social_number_of_people);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.expandable_item_social_icon);
				holder.days = (TextView) convertView
						.findViewById(R.id.expandable_item_social_number_of_days);
				holder.status = (TextView) convertView
						.findViewById(R.id.expandable_item_social_number_of_status);
				convertView.setTag(holder);
			} else {
				holder = (GroupHolder) convertView.getTag();
			}

			//holder.title.setText(item.title);
			holder.title.setText(item.bpartner_id);
			holder.surname.setText(item.surname);
			holder.days.setText(item.days);
			holder.status.setText(item.status);

			ImageUtil.displayRoundImage(holder.icon,
					"file://" + item.thumbnail, null);

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return true;
		}


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int position = (Integer) v.getTag(R.id.child);
			int parent = (Integer) v.getTag(R.id.parent);


			switch (v.getId()) {
				case R.id.list_item_expandable_social_child_layout:
					Toast.makeText(getApplicationContext(),
							"List option: parent is "+ parent + "child is" + position, Toast.LENGTH_SHORT).show();
//							"List option: parent is "+ item.surname + "child is" + position, Toast.LENGTH_SHORT).show();
//					Intent i;
//					i = new Intent(ExpandableSocialListViewActivity.this, businesspartner_prototype.class);
//					i.putExtra("parent",parent);
//					i.putExtra("child",position);
//					ExpandableSocialListViewActivity.this.startActivity(i);
					break;
			}
		}
	}
}
