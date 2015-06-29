package com.globant.scriptsapadea.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by leonel.mendez on 6/11/2015.
 */
public class PictureUtils {

    @SuppressLint("NewApi")
    private static String getRealPathFromURI_API19(Context context, Uri uri) {

        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);
        String id = wholeID.split(":")[1];
        String[] column = {MediaStore.Images.Media.DATA};
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }

        cursor.close();
        return filePath;
    }


    @SuppressLint("NewApi")
    private static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;
        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }

        return result;
    }

    private static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();

        return filePath;
    }


    public static String getImagePath(Context context, Uri contentUri) {

        String realPath;
        if (Build.VERSION.SDK_INT < 11) {
            realPath = getRealPathFromURI_BelowAPI11(context, contentUri);

        } else if (Build.VERSION.SDK_INT < 19) {
            realPath = getRealPathFromURI_API11to18(context, contentUri);
        } else {
            realPath = getRealPathFromURI_API19(context, contentUri);
        }
        return realPath;
    }

    public static void pickPhotoFromGallery(Fragment fragment, int requestCode) {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            fragment.startActivityForResult(intent, requestCode);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    public static void takePhotoFromCamera(Fragment fragment,int requestCode) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fragment.startActivityForResult(cameraIntent, requestCode);
    }

}
