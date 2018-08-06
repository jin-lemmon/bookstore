package com.example.android.bookstore;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstore.data.BookContract.BookEntry;
import com.example.android.bookstore.data.BookDbHelper;

public class BookstoreActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;
    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mSupplierNameEditText;
    private EditText mSupplierPhoneEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookstore);
        mDbHelper = new BookDbHelper(this);

        Button insertBookButton = findViewById(R.id.insert_book);
        insertBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(BookstoreActivity.this,DetailActivity.class);
                Toast.makeText(BookstoreActivity.this,"Inserting New Book",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        shelfInfo();
    }

    protected void onStart() {
        super.onStart();
        shelfInfo();
    }



    private void shelfInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {BookEntry._ID,
                BookEntry.COLUMN_BOOK_PRODUCT_NAME,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_BOOK_SUPPLIER,
                BookEntry.COLUMN_BOOK_SUPPLIER_PHONE};
        Cursor cursor = db.query(BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        try {
            TextView mInventory = findViewById(R.id.inventory);
            mInventory.setText("the shelf contains " + cursor.getCount() + "titles\n\n");
            mInventory.append(BookEntry._ID + " - "
                    + BookEntry.COLUMN_BOOK_PRODUCT_NAME + " - "
                    + BookEntry.COLUMN_BOOK_PRICE + " - "
                    + BookEntry.COLUMN_BOOK_QUANTITY + " - "
                    + BookEntry.COLUMN_BOOK_SUPPLIER + " - "
                    + BookEntry.COLUMN_BOOK_SUPPLIER_PHONE + "\n");
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int productNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE);
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentProductName = cursor.getString(productNameColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);
                mInventory.append(("\n" + currentID +
                        " - " + currentProductName +
                        " - " + currentQuantity +
                        " - " + currentPrice +
                        " - " + currentSupplierName +
                        " - " + currentSupplierPhone));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertBook() {
        if (mPriceEditText != null && mQuantityEditText != null) {
            int price = Integer.parseInt(mPriceEditText.getText().toString().trim());
            int quantity = Integer.parseInt(mQuantityEditText.getText().toString().trim());
            BookDbHelper mDbHelper = new BookDbHelper(this);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BookEntry.COLUMN_BOOK_PRODUCT_NAME, mNameEditText.getText().toString().trim());
            values.put(BookEntry.COLUMN_BOOK_PRICE, price);
            values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
            values.put(BookEntry.COLUMN_BOOK_SUPPLIER, mSupplierNameEditText.getText().toString().trim());
            values.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE, mSupplierPhoneEditText.getText().toString().trim());
            db.insert(BookEntry.TABLE_NAME, null, values);
        }else{
            Toast.makeText(this,"missing fields",Toast.LENGTH_SHORT).show();
        }
    }
}