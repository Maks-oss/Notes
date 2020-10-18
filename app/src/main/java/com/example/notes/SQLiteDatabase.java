package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "Notes";

    private static final String Notes = "Notes";
    private static final String NAME = "Name";
    private static final String DATE = "Date";
    private static final String TEXT = "text";
    private static final String ID="id";

    SQLiteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + Notes + "(" + NAME
                + " Notename,"
                + DATE + " Notedate,"
                + TEXT + " Notetext"+ ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Notes);
        onCreate(sqLiteDatabase);
    }

    ArrayList<Note> listNotes() {
        String sql = "select * from " + Notes;
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Note> storeNotes = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String date = cursor.getString(1);
                String text =cursor.getString(2);
                storeNotes.add(new Note(name, date,text));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeNotes;
    }

    void addNote(Note contacts) {
        ContentValues values = new ContentValues();
        values.put(NAME, contacts.getName());
        values.put(DATE, contacts.getDate());
        values.put(TEXT,contacts.getText());
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        db.insert(Notes, null, values);
    }

    void updateNote(Note contacts,String oldName) {
        ContentValues values = new ContentValues();
        values.put(NAME, contacts.getName());
        values.put(DATE, contacts.getDate());
        values.put(TEXT,contacts.getText());
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        db.update(Notes, values, NAME + " = ?", new String[]{oldName});
        db.update(Notes, values, TEXT + " = ?", new String[]{contacts.getText()});

    }
    void deleteNote(String name)
    {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Notes, NAME + " = ?",new String[]{name});
    }
    void clear()
    {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(Notes, null, null);
    }
}
