package com.globant.scriptsapadea.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.models.Slide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas.quartieri.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "scriptapadea.db";

    //Table Names
    public static final String TABLE_PATIENT = "patient";
    public static final String TABLE_SCRIPT = "script";
    public static final String TABLE_SLIDE = "slide";

    // Common column names
    public static final String PATIENT_ID = "_id";
    public static final String SCRIPT_ID = "_id";
    public static final String SLIDE_ID = "_id";

    //Patient colum names
    public static final String PATIENT_COLUMN_NAME = "name_patient";
    public static final String PATIENT_COLUMN_AVATAR = "avatar";
    public static final String PATIENT_COLUMN_IS_RES_AVATAR = "is_res_avatar";

    //Script colum names
    public static final String SCRIPT_COLUMN_NAME = "name_script";
    public static final String SCRIPT_COLUMN_IMAGE = "image";
    public static final String SCRIPT_COLUMN_KEY_PATIENT = "patient_id";
    public static final String SCRIPT_COLUMN_IS_RES_IMAGE = "is_res_image";

    //Slide colum names
    public static final String SLIDE_COLUMN_NAME = "name_slide";
    public static final String SLIDE_COLUMN_IMAGE = "image";
    public static final String SLIDE_COLUMN_TYPE = "type";
    public static final String SLIDE_COLUMN_KEY_SCRIPT = "script_id";
    public static final String SLIDE_COLUMN_IS_RES_IMAGE = "is_res_image";

    public static final int DATABASE_VERSION = 1;

    private final Context context;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_PATIENT + "(" + PATIENT_ID + " integer primary key autoincrement, " +
                PATIENT_COLUMN_NAME + " text not null, " +
                PATIENT_COLUMN_IS_RES_AVATAR + " integer not null, " +
                PATIENT_COLUMN_AVATAR + " text null ); ");
        db.execSQL("create table " + TABLE_SCRIPT + "(" + SCRIPT_ID + " integer primary key autoincrement, " +
                SCRIPT_COLUMN_NAME + " text not null, " +
                SCRIPT_COLUMN_IS_RES_IMAGE + " integer not null, " +
                SCRIPT_COLUMN_IMAGE + " text null, " +
                SCRIPT_COLUMN_KEY_PATIENT + " integer null );");
        db.execSQL("create table " + TABLE_SLIDE + "(" + SLIDE_ID + " integer primary key autoincrement, " +
                SLIDE_COLUMN_NAME + " text not null, " +
                SLIDE_COLUMN_IS_RES_IMAGE + " integer not null, " +
                SLIDE_COLUMN_IMAGE + " text null, " +
                SLIDE_COLUMN_TYPE + " integer not null, " +
                SLIDE_COLUMN_KEY_SCRIPT + " integer null );");

        Log.e("INFO", "DB Tables Created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        Log.e("INFO", "DB Updated.");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCRIPT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLIDE);
        onCreate(db);
    }

    public long createPatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PATIENT_COLUMN_NAME, patient.getName());
        if (patient.isResourceAvatar()) {
            values.put(PATIENT_COLUMN_AVATAR, patient.getResAvatar());
            values.put(PATIENT_COLUMN_IS_RES_AVATAR, 1);
        } else {
            values.put(PATIENT_COLUMN_AVATAR, patient.getAvatar());
            values.put(PATIENT_COLUMN_IS_RES_AVATAR, 0);
        }

        long patientId = db.insert(TABLE_PATIENT, null, values);

        List<Script> scriptList = patient.getScriptList();
        if (!scriptList.isEmpty()) {
            int scriptListSize = scriptList.size();
            for (int i = 0 ; i < scriptListSize ; i++) {
                createScript(scriptList.get(i), patientId);
            }
        }

        patient.setId(patientId);

        return patientId;
    }

    public long createSlide(Slide slide, long scriptId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SLIDE_COLUMN_NAME, slide.getText());
        if (slide.isResourceImage()) {
            values.put(SLIDE_COLUMN_IMAGE, slide.getResImage());
            values.put(SLIDE_COLUMN_IS_RES_IMAGE, 1);
        } else {
            values.put(SLIDE_COLUMN_IMAGE, slide.getUrlImage());
            values.put(SLIDE_COLUMN_IS_RES_IMAGE, 0);
        }

        values.put(SLIDE_COLUMN_TYPE, slide.getType());
        values.put(SLIDE_COLUMN_KEY_SCRIPT, scriptId);

        long slideId = db.insert(TABLE_SLIDE, null, values);

        slide.setId(slideId);

        return slideId;
    }

    public long createScript(Script script, long patientId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCRIPT_COLUMN_NAME, script.getName());
        if (script.isResourceImage()) {
            values.put(SCRIPT_COLUMN_IMAGE, script.getResImage());
            values.put(SCRIPT_COLUMN_IS_RES_IMAGE, 1);
        } else {
            values.put(SCRIPT_COLUMN_IMAGE, script.getImageScripts());
            values.put(SCRIPT_COLUMN_IS_RES_IMAGE, 0);
        }
        values.put(SCRIPT_COLUMN_KEY_PATIENT, patientId);

        long scriptId = db.insert(TABLE_SCRIPT, null, values);

        List<Slide> slideList = script.getSlides();
        if (!slideList.isEmpty()) {
            int scriptListSize = slideList.size();
            for (int i = 0 ; i < scriptListSize ; i++) {
                createSlide(slideList.get(i), scriptId);
            }
        }

        script.setId(scriptId);

        return scriptId;
    }

    /**
     * getting all Scripts under single tag
     */
    public List<Script> getAllScriptsFromPatient(long patientId) {
        List<Script> scripts = new ArrayList<Script>();

        String selectQuery = "SELECT * FROM " + TABLE_SCRIPT + " s "
                + " WHERE s." + SCRIPT_COLUMN_KEY_PATIENT + " = " + patientId;

        Log.d("Query", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Script script;
                if (c.getInt(c.getColumnIndex(SCRIPT_COLUMN_IS_RES_IMAGE)) == 1) {
                    script = new Script(c.getLong(c.getColumnIndex(SCRIPT_ID)),
                                (c.getString(c.getColumnIndex(SCRIPT_COLUMN_NAME))),
                                (c.getInt(c.getColumnIndex(SCRIPT_COLUMN_IMAGE))));
                } else {
                    script = new Script(c.getLong(c.getColumnIndex(SCRIPT_ID)),
                                (c.getString(c.getColumnIndex(SCRIPT_COLUMN_NAME))),
                                (c.getString(c.getColumnIndex(SCRIPT_COLUMN_IMAGE))));
                }

                scripts.add(script);
            } while (c.moveToNext());
        }

        return scripts;
    }

    /**
     * getting all Slide under single tag
     */
    public List<Slide> getAllSlidesFromScript(long scriptId) {
        List<Slide> slides = new ArrayList<Slide>();

        String selectQuery = "SELECT * FROM " + TABLE_SLIDE + " s "
                + " WHERE s." + SLIDE_COLUMN_KEY_SCRIPT+ " = " + scriptId;

        Log.d("Query", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Slide slide;
                if (c.getInt(c.getColumnIndex(SLIDE_COLUMN_IS_RES_IMAGE)) == 1) {
                    slide = new Slide(c.getLong((c.getColumnIndex(SLIDE_ID))),
                                        (c.getInt(c.getColumnIndex(SLIDE_COLUMN_IMAGE))),
                                        (c.getString(c.getColumnIndex(SLIDE_COLUMN_NAME))),
                                        (c.getInt(c.getColumnIndex(SLIDE_COLUMN_TYPE))));
                } else {
                    slide = new Slide(c.getLong((c.getColumnIndex(SLIDE_ID))),
                                        (c.getString(c.getColumnIndex(SLIDE_COLUMN_IMAGE))),
                                        (c.getString(c.getColumnIndex(SLIDE_COLUMN_NAME))),
                                        (c.getInt(c.getColumnIndex(SLIDE_COLUMN_TYPE))));
                }

                slides.add(slide);
            } while (c.moveToNext());
        }

        return slides;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();

        String selectPatientQuery = "SELECT * FROM " + TABLE_PATIENT;

        Log.d("Query", selectPatientQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectPatientQuery, null);

        if (c.moveToFirst()) {
            do {
                Patient patient = new Patient(c.getLong((c.getColumnIndex(PATIENT_ID))),
                                            (c.getString(c.getColumnIndex(PATIENT_COLUMN_NAME))),
                                            (c.getString(c.getColumnIndex(PATIENT_COLUMN_AVATAR))));

                List<Script> scriptList = getAllScriptsFromPatient(patient.getId());
                if (!scriptList.isEmpty()) {
                    patient.setScriptList(scriptList);

                    int size = scriptList.size();
                    for (int i = 0 ; i < size; i++) {
                        Script script = scriptList.get(i);
                        List<Slide> slideList = getAllSlidesFromScript(script.getId());
                        script.setSlides(slideList);
                    }
                }

                patients.add(patient);
            } while (c.moveToNext());
        }

        return patients;
    }

    public void deletePatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Script> scripts = getAllScriptsFromPatient(patient.getId());

        for (Script script : scripts) {
            deleteScript(script);
        }

        db.delete(TABLE_PATIENT, PATIENT_ID + " = ?",
                new String[]{String.valueOf(patient.getId())});

        Log.d("DEBUG", "Delete patient with ID: " + patient.getId());
    }

    public void deleteScript(Script script) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCRIPT, PATIENT_ID + " = ?",
                new String[]{String.valueOf(script.getId())});
    }

    public void deleteDataBase() {
        context.deleteDatabase(SQLiteHelper.DATABASE_NAME);

        Log.e("INFO", "DB deleted.");
    }

    public int updateScript(Script script) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCRIPT_COLUMN_NAME, script.getName());
        values.put(SCRIPT_COLUMN_IMAGE, script.getImageScripts());

        return db.update(TABLE_SCRIPT, values, PATIENT_ID + " = ?",
                new String[] { String.valueOf(script.getId()) });
    }

    public int updatePatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PATIENT_COLUMN_NAME, patient.getName());
        values.put(PATIENT_COLUMN_AVATAR, patient.getAvatar());

        return db.update(TABLE_PATIENT, values, PATIENT_ID + " = ?",
                new String[] { String.valueOf(patient.getId()) });
    }
}
