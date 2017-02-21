package com.example.arthur.social.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.example.arthur.social.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

public class ImageUtil {
	
	public static void displayImage(ImageView view, String path, ImageLoadingListener listener) {
		ImageLoader loader = ImageLoader.getInstance();
		try {
			loader.displayImage(path, view, DEFAULT_DISPLAY_IMAGE_OPTIONS, listener);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			loader.clearMemoryCache();
		}
	}

	public static void displayRoundImage(ImageView view, String path, ImageLoadingListener listener) {
		ImageLoader loader = ImageLoader.getInstance();

		try {
			loader.displayImage(path, view, ROUND_DISPLAY_IMAGE_OPTIONS, listener);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			loader.clearMemoryCache();
		}
	}
	
	public static void loadImage(String path, ImageLoadingListener listener) {
		ImageLoader loader = ImageLoader.getInstance();
		try {
			loader.loadImage(path, DEFAULT_DISPLAY_IMAGE_OPTIONS, listener);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
	}
	
	//TODO Change default image
	private static final DisplayImageOptions.Builder DEFAULT_DISPLAY_IMAGE_OPTIONS_BUIDLER = new DisplayImageOptions.Builder()
			.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
			.displayer(new FadeInBitmapDisplayer(300, true, false, false))
			.showImageForEmptyUri(R.drawable.default_image)
			.showImageOnLoading(R.drawable.default_image)
			.showImageOnFail(R.drawable.default_image).cacheOnDisk(true)
			.cacheInMemory(true).bitmapConfig(Config.ARGB_8888);

	private static final DisplayImageOptions DEFAULT_DISPLAY_IMAGE_OPTIONS = DEFAULT_DISPLAY_IMAGE_OPTIONS_BUIDLER
			.build();
	private static final DisplayImageOptions ROUND_DISPLAY_IMAGE_OPTIONS = DEFAULT_DISPLAY_IMAGE_OPTIONS_BUIDLER
			.displayer(new RoundedBitmapDisplayer(500)).build();

	static int picture_mode = 0;
	public static void showCameraGalleryPage(final Context context, final int requestCode, final String output)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);

		String items[] = {"Camera", "Gallery"};

		dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				picture_mode = whichButton;
				if (picture_mode == 0) {
					doTakePhotoFromCamera(context, requestCode, output+".jpg" );
				} else if (picture_mode == 1) {
					doTakePhotoFromGallery(context, requestCode + 1 );
				} else if (picture_mode == 2) {
					doTakeVideoFromCamera(context, requestCode + 2, output+".mp4" );
				}else if (picture_mode == 3) {
					doTakeVideoFromGallery(context, requestCode + 3);
				}
				dialog.dismiss();
			}
		});

		dialog.create();
		AlertDialog alertDialog = dialog.show();

		alertDialog.show();
		alertDialog.setCanceledOnTouchOutside(true);
	}


	private static final String IMAGE_UNSPECIFIED = "image/*";
	private static final String VIDEO_UNSPECIFIED = "video/*";
	private static final String AUDIO_UNSPECIFIED = "audio/*";

	public static void doTakeVideoFromCamera(Context context, int requestCode, String output )
	{
		Activity activity = (Activity) context;

		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

		File photo = new File(output);

		Uri imageUri = Uri.fromFile(photo);

		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);   // does file create?
		intent.putExtra(android.provider.MediaStore.EXTRA_SCREEN_ORIENTATION,
				activity.getWindowManager().getDefaultDisplay().getOrientation());
		intent.putExtra("return-data", true);

		activity.startActivityForResult(intent, requestCode);
	}

	public static void doTakePhotoFromCamera(Context context, int requestCode, String output )
	{
		Activity activity = (Activity) context;

		Intent 	intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File photo = new File(output);

		Uri imageUri = Uri.fromFile(photo);

		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);   // does file create?
		intent.putExtra(android.provider.MediaStore.EXTRA_SCREEN_ORIENTATION,
				activity.getWindowManager().getDefaultDisplay().getOrientation());
		intent.putExtra("return-data", true);

		activity.startActivityForResult(intent, requestCode);
	}

	public static void doTakePhotoFromGallery(Context context, int requestCode )
	{
		Activity activity = (Activity) context;

		Intent intent = null;

		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_PICK);
		} else {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
		}

		intent.setType(IMAGE_UNSPECIFIED);

		activity.startActivityForResult(intent, requestCode );// to connect onActivityResult in activity
	}

	public static void doTakeVideoFromGallery(Context context, int requestCode )
	{
		Activity activity = (Activity) context;

		Intent intent = null;

		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_PICK);
		} else {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
		}

		intent.setType(VIDEO_UNSPECIFIED);

		activity.startActivityForResult(intent, requestCode );// to connect onActivityResult in activity
	}

	public static String getPathFromURI(Context context, Uri uri) {
		// just some safety built in
		if( uri == null ) {
			// TODO perform some logging or show user feedback
			return null;
		}
		// try to retrieve the image from the media store first
		// this will only work for images selected from gallery
		Cursor cursor = null;

		try {
			String[] projection = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(uri, projection, null, null, null);
			if( cursor != null ){
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				String path = cursor.getString(column_index);
				if( path == null || path.length() < 1 )
					path = uri.getPath();
				return path;
			}
		}
		finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		// this is our fallback here
		return uri.getPath();
	}
}
