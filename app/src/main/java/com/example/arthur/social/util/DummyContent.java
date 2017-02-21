package com.example.arthur.social.util;

import com.example.arthur.social.R;
import com.example.arthur.social.model.DummyModel;

import java.util.ArrayList;

public class DummyContent {
	
	/* This method gives us just a dummy content - array list
	 * of ImageGalleryCategoryModels. Every model has id that is
	 * need for some classes (e.g. DefaultAdapter.java).
	 * Favourites are randomly chosen to be true or false.
	 * Last category is randomly added to the list so you could
	 * see when there are even or odd numbers of categories in
	 * ImageGalleryActivity.
	 */

	
	//TODO Change to social
	public static ArrayList<DummyModel> getSocialDummyList() {
		ArrayList<DummyModel> list = new ArrayList<>();
		list.add(new DummyModel(0, "", "Prospect", R.string.material_icon_account));
		list.add(new DummyModel(1, "", "Client", R.string.material_icon_account));

		return list;
	}
	

}
