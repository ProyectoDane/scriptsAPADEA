package com.globant.scriptsapadea.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillermo.rosales on 19/05/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper{


    public static final String DATABASE_NOMBRE = "scriptapadea.db";


    //Table Names
    public static final String TABLE_PATIENT = "patient";
    public static final String TABLE_SCRIPT = "script";

    // Common column names
    public static final String _ID = "_id";


    //Patient colum names
    public static final String PATIENT_COLUMNA_NAME = "name_patient";
    public static final String PATIENT_COLUMNA_AVATAR = "avatar";

    //Script colum names
    public static final String SCRIPT_COLUMNA_NAME = "name_script";
    public static final String SCRIPT_COLUMNA_IMAGE = "image";
    public static final String SCRIPT_COLUMNA_KEY_PATIENT = "patient_id";


    public static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // Si la base no existe.
        db.execSQL("create table " + TABLE_PATIENT + "(" + _ID + " integer primary key autoincrement, " +
                PATIENT_COLUMNA_NAME + " text not null, " +
                PATIENT_COLUMNA_AVATAR + " integer not null ); ");
        db.execSQL("create table " + TABLE_SCRIPT + "(" + _ID + " integer primary key autoincrement, " +
                SCRIPT_COLUMNA_NAME + " text not null, " +
                SCRIPT_COLUMNA_IMAGE + " integer not null, " +
                SCRIPT_COLUMNA_KEY_PATIENT + " integer not null );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) { // Actualizar el schema.
        Log.e("INFO", "DB Actualizada.");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCRIPT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
        onCreate(db);
    }


    public long createScript(Script script, long patient_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCRIPT_COLUMNA_NAME, script.getName());
        values.put(SCRIPT_COLUMNA_IMAGE, script.getImageScripts());
        values.put(SCRIPT_COLUMNA_KEY_PATIENT, patient_id);

        long script_id = db.insert(TABLE_SCRIPT, null, values);

        return script_id;
    }


    public long createPatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PATIENT_COLUMNA_NAME, patient.getName());
        values.put(PATIENT_COLUMNA_AVATAR, patient.getAvatar());

        long patient_id = db.insert(TABLE_PATIENT, null, values);

        return patient_id;
    }


    /**
     * getting all todos under single tag
     */
    public List<Script> getAllScriptsFromPatient(String patientName) {
        List<Script> scripts = new ArrayList<Script>();

        String selectQuery = "SELECT  * FROM " + TABLE_SCRIPT + " s, "
                + TABLE_PATIENT + " p WHERE p."
                + PATIENT_COLUMNA_NAME + " = '" + patientName + "'" + " AND s." + _ID
                + " = " + "p." + _ID;

        Log.d("Query", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            do {
                Script script = new Script();
                script.setId(String.valueOf(c.getInt((c.getColumnIndex(_ID)))));
                script.setName((c.getString(c.getColumnIndex(SCRIPT_COLUMNA_NAME))));
                script.setImage((c.getInt(c.getColumnIndex(SCRIPT_COLUMNA_IMAGE))));


                scripts.add(script);
            } while (c.moveToNext());
        }

        return scripts;
    }


    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PATIENT;

        Log.d("Query", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            do {
                Patient patient = new Patient();
                patient.setId(String.valueOf(c.getInt((c.getColumnIndex(_ID)))));
                patient.setName((c.getString(c.getColumnIndex(PATIENT_COLUMNA_NAME))));
                patient.setAvatar((c.getInt(c.getColumnIndex(PATIENT_COLUMNA_AVATAR))));

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


    public int updateScript(Script script) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCRIPT_COLUMNA_NAME, script.getName());
        values.put(SCRIPT_COLUMNA_IMAGE, script.getImageScripts());

        // updating row
        return db.update(TABLE_SCRIPT, values, _ID + " = ?",
                new String[] { String.valueOf(script.getId()) });
    }

    public int updatePatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PATIENT_COLUMNA_NAME, patient.getName());
        values.put(PATIENT_COLUMNA_AVATAR, patient.getAvatar());

        // updating row
        return db.update(TABLE_PATIENT, values, _ID + " = ?",
                new String[] { String.valueOf(patient.getId()) });
    }
}
