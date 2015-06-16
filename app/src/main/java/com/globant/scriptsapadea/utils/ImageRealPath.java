package com.globant.scriptsapadea.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * Created by leonel.mendez on 6/11/2015.
 */
public class ImageRealPath {

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
        cursor.close();
        return cursor.getString(column_index);
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
}
