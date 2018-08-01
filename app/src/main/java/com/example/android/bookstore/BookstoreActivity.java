package com.example.android.bookstore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.bookstore.data.BookContract.BookEntry;
import com.example.android.bookstore.data.BookDbHelper;

public class BookstoreActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;
    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mSupplierNameEditText;
    private EditText mSupplierPhoneEditText;
    private TextView mInventory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookstore);
        mDbHelper = new BookDbHelper(this);
        mNameEditText = findViewById(R.id.product_name);
        mPriceEditText = findViewById(R.id.price);
        mQuantityEditText = findViewById(R.id.quantity);
        mSupplierNameEditText = findViewById(R.id.supplier_name);
        mSupplierPhoneEditText = findViewById(R.id.supplier_phone);
        mInventory = findViewById(R.id.inventory);
        Button insertBookButton = findViewById(R.id.insert_book);
        insertBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBook();
                eraseText();
                shelfInfo();
            }
        });
        shelfInfo();
    }

    protected void onStart() {
        super.onStart();
        shelfInfo();
            }

    private void eraseText(){
        mNameEditText.setText("");
        mPriceEditText.setText("");
        mQuantityEditText.setText("");
        mSupplierNameEditText.setText("");
        mSupplierPhoneEditText.setText("");
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
            TextView InventoryView = findViewById(R.id.inventory);
            InventoryView.setText("the shelf contains " + cursor.getCount() + "titles\n\n");
            InventoryView.append(BookEntry._ID + " - "
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
                InventoryView.append(("\n" + currentID +
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
    }
}