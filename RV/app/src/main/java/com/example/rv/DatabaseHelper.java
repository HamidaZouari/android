package com.example.rv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "contacts.sqlite3", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table contacts (id integer primary key autoincrement, nom varchar(20), tel varchar(8))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void addContact(Contact c){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nom",c.getContactName());
        cv.put("tel",c.getContactNumber());
        db.insert("contacts","null",cv);
        db.close();
    }
    public ArrayList<Contact> getAll(){
        ArrayList<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("contacts",new String[]{"id","nom","tel"},null,null,null,null,null);
        while(c.moveToNext()){
            contacts.add(new Contact(c.getInt(0),c.getString(1),c.getString(2)));
        }
        db.close();
        return contacts;
    }
    public ArrayList<Contact> getAll(String keyword){
        ArrayList<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query("contacts",new String[]{"id","nom","tel"},"nom like ? or tel LIKE ?", new String[]{keyword + "%", keyword + "%"},null,null,null);
        while(c.moveToNext()){
            contacts.add(new Contact(c.getInt(0),c.getString(1),c.getString(2)));
        }
        db.close();
        return contacts;
    }

    public void updateContact(Contact c){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update contacts set nom = '"+c.getContactName()+"', tel = '"+c.getContactNumber()+"' where id = "+c.getId());
        db.close();
    }
    public void updateItem(Contact c) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE contacts SET " + "nom" + " = '" + c.getContactName() + "', " + "tel" + " = '" + c.getContactNumber()+  "' WHERE " + "id" + " = " + 2;
        db.execSQL(query);
        db.close();
    }


    public void deleteContact(Contact c){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from contacts where id="+c.getId());
        db.close();
    }
}
