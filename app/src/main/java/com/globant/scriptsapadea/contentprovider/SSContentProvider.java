package com.globant.scriptsapadea.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.globant.scriptsapadea.sql.SQLiteHelper;

/**
 * This class provides the respective ContentProvider for this app.
 *
 * @author nicolas.quartieri
 */
public class SSContentProvider extends ContentProvider {

	private SQLiteHelper mDBHelper;
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private static final String PROVIDER = "com.globant.scriptsapadea.contentprovider";
	private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + PROVIDER);
	public  static final Uri CONTENT_URI;
	private static final int ALL = 10;
	private static final int SINGLE_ID = 15;
	
	static {
		CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath("patient").build();
		
		uriMatcher.addURI("com.globant.scriptsapadea.contentprovider", "patient", ALL);
		uriMatcher.addURI("com.globant.scriptsapadea.contentprovider", "patient/*", SINGLE_ID);
	}
	
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		int count = 0;
		
		switch (uriMatcher.match(uri)) {
		case ALL:
			count = db.delete(SQLiteHelper.TABLE_PATIENT, where, whereArgs);
			break;			
			
		case SINGLE_ID:
			String id = uri.getLastPathSegment();
			count = db.delete(SQLiteHelper.TABLE_PATIENT, SQLiteHelper.PATIENT_ID + " = " + uri.getPathSegments().get(1), whereArgs);
			break;		
			
		default:
			throw new IllegalArgumentException("Error trying to delete a register. " + uri);
		}
		
		return count;
	}

	@Override
	public String getType(Uri uri) {	        
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		
		long id = db.insert(SQLiteHelper.TABLE_PATIENT, null, values);
		
		if (id > 0) {
			return CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
		}

		throw new IllegalArgumentException("Error trying to insert a register. " + uri);
	}

	@Override
	public boolean onCreate() {
		mDBHelper = new SQLiteHelper(this.getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
		queryBuilder.setTables(SQLiteHelper.TABLE_PATIENT);
		
		switch (uriMatcher.match(uri)) {
		case ALL:
			
			break;			
		case SINGLE_ID:
        	queryBuilder.appendWhere(SQLiteHelper.TABLE_PATIENT + " = " + uri.getPathSegments().get(1));
			break;		
		default:
			break;
		}
		
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		
		Cursor c = queryBuilder.query(db, projection, selection, selectionArgs, null, null, null);
		
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues cv, String arg2, String[] arg3) {
		return 0;
	}
}
