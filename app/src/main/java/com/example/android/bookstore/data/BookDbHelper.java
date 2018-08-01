package com.example.android.bookstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.bookstore.data.BookContract;

public class BookDbHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "books.db";

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME + " ("
                + BookContract.BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookContract.BookEntry.COLUMN_BOOK_PRODUCT_NAME + " TEXT, "
                + BookContract.BookEntry.COLUMN_BOOK_PRICE + " INTEGER DEFAULT 0, "
                + BookContract.BookEntry.COLUMN_BOOK_QUANTITY + " INTEGER DEFAULT 0, "
                + BookContract.BookEntry.COLUMN_BOOK_SUPPLIER +  " TEXT, "
                + BookContract.BookEntry.COLUMN_BOOK_SUPPLIER_PHONE + " TEXT);";
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
