package com.globant.scriptsapadea.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.globant.scriptsapadea.models.Image;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    //constants
    public static final String DATABASE_NAME = "scriptapadea.db";
    public static final String TABLE_PATIENT = "patient";
    public static final String TABLE_SCRIPT = "script";
    public static final String TABLE_IMAGE = "image";
    public static final String _ID = "_id";
    public static final String IMAGE_COLUMN_NAME = "androidId";
    public static final String IMAGE_COLUMN_KEY_SCRIPT = "script_id";
    public static final String PATIENT_COLUMN_NAME = "name_patient";
    public static final String PATIENT_COLUMN_AVATAR = "avatar";
    public static final String SCRIPT_COLUMN_NAME = "name_script";
    public static final String SCRIPT_COLUMN_KEY_PATIENT = "patient_id";
    public static final int DATABASE_VERSION = 1;


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_PATIENT + "(" + _ID + " integer primary key autoincrement, " +
                PATIENT_COLUMN_NAME + " text not null, " +
                PATIENT_COLUMN_AVATAR + " integer not null ); ");
        db.execSQL("create table " + TABLE_SCRIPT + "(" + _ID + " integer primary key autoincrement, " +
                SCRIPT_COLUMN_NAME + " text not null, " +
                SCRIPT_COLUMN_KEY_PATIENT + " integer not null );");
        db.execSQL("create table " + TABLE_IMAGE + "(" + _ID + " integer primary key autoincrement, " +
                IMAGE_COLUMN_NAME + " integer not null, " +
                IMAGE_COLUMN_KEY_SCRIPT + " integer not null );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        Log.e("INFO", "DB updated.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCRIPT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
        onCreate(db);
    }


    public long createScript(Script script, long patient_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SCRIPT_COLUMN_NAME, script.getName());
        values.put(SCRIPT_COLUMN_KEY_PATIENT, patient_id);
        long script_id = db.insert(TABLE_SCRIPT, null, values);
        return script_id;
    }


    public long addImageToScript(Script script, int androidId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IMAGE_COLUMN_NAME, androidId);
        values.put(IMAGE_COLUMN_KEY_SCRIPT, script.getId());
        long patient_id = db.insert(TABLE_PATIENT, null, values);
        return patient_id;
    }


    public List<Image> getImagesFromScript(Script script) {
        List<Image> images = new ArrayList<Image>();
        String selectQuery = "SELECT  * FROM " + TABLE_SCRIPT + " s, "
                + TABLE_IMAGE + " i WHERE i."
                + SCRIPT_COLUMN_NAME + " = '" + script.getName() + "'" + " AND s." + _ID
                + " = " + "i." + _ID;
        Log.d("Query", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Image image = new Image(String.valueOf(c.getInt((c.getColumnIndex(_ID)))), (c.getInt(c.getColumnIndex(IMAGE_COLUMN_NAME))));
                images.add(image);
            } while (c.moveToNext());
        }
        return images;
    }


    public long createPatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PATIENT_COLUMN_NAME, patient.getName());
        values.put(PATIENT_COLUMN_AVATAR, patient.getAvatar());
        long patient_id = db.insert(TABLE_PATIENT, null, values);
        return patient_id;
    }


    public List<Script> getAllScriptsFromPatient(String patientName) {
        List<Script> scripts = new ArrayList<Script>();
        String selectQuery = "SELECT  * FROM " + TABLE_SCRIPT + " s, "
                + TABLE_PATIENT + " p WHERE p."
                + PATIENT_COLUMN_NAME + " = '" + patientName + "'" + " AND s." + _ID
                + " = " + "p." + _ID;
        Log.d("Query", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Script script = new Script(String.valueOf(c.getInt((c.getColumnIndex(_ID)))), (c.getString(c.getColumnIndex(SCRIPT_COLUMN_NAME))));
                scripts.add(script);
            } while (c.moveToNext());
        }
        return scripts;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<Patient>();
        String selectQuery = "SELECT  * FROM " + TABLE_PATIENT;
        Log.d("Query", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Patient patient = new Patient(String.valueOf(c.getInt((c.getColumnIndex(_ID)))), (c.getString(c.getColumnIndex(PATIENT_COLUMN_NAME))), (c.getInt(c.getColumnIndex(PATIENT_COLUMN_AVATAR))));
                patients.add(patient);
            } while (c.moveToNext());
        }
        return patients;
    }

    public void deletePatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Script> scripts = getAllScriptsFromPatient(patient.getName());
        for (Script script : scripts) {

            deleteScript(script);
        }
        db.delete(TABLE_PATIENT, _ID + " = ?",
                new String[]{String.valueOf(patient.getId())});
    }


    public void deleteScript(Script script) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCRIPT, _ID + " = ?",
                new String[]{String.valueOf(script.getId())});
    }


    public void deleteImage(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGE, _ID + " = ?",
                new String[]{String.valueOf(image.getId())});
    }


    public void deleteScriptImages(Script script) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCRIPT, IMAGE_COLUMN_KEY_SCRIPT + " = ?",
                new String[]{String.valueOf(script.getId())});
    }

    public int updateScript(Script script) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SCRIPT_COLUMN_NAME, script.getName());
        return db.update(TABLE_SCRIPT, values, _ID + " = ?",
                new String[]{String.valueOf(script.getId())});
    }

    public int updatePatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PATIENT_COLUMN_NAME, patient.getName());
        values.put(PATIENT_COLUMN_AVATAR, patient.getAvatar());
        return db.update(TABLE_PATIENT, values, _ID + " = ?",
                new String[]{String.valueOf(patient.getId())});
    }

    public int updateImage(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IMAGE_COLUMN_NAME, image.getAndroidId());
        return db.update(TABLE_PATIENT, values, _ID + " = ?",
                new String[]{String.valueOf(image.getId())});
    }

}
