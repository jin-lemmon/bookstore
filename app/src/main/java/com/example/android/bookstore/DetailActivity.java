package com.example.android.bookstore;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstore.data.BookDbHelper;
import com.example.android.bookstore.data.BookContract.BookEntry;

public class DetailActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mSupplierNameEditText;
    private EditText mSupplierPhoneEditText;
    private TextView mQuantityTextView;
    private int mQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mNameEditText = findViewById(R.id.product_name);
        mPriceEditText = findViewById(R.id.price);
        mSupplierNameEditText = findViewById(R.id.supplier_name);
        mQuantityTextView =findViewById(R.id.quantity);
        mSupplierPhoneEditText = findViewById(R.id.supplier_phone);
        Button validate = findViewById(R.id.validate_book);
        updateQuantity();
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateBook();
            }
        });
        Button minus = findViewById(R.id.buttonMinus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityMinus();
            }
        });
        Button plus = findViewById(R.id.buttonPlus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityPlus();
            }
        });
    }

    private void quantityPlus() {
        mQuantity += 1;
        updateQuantity();
    }

    private void quantityMinus() {
        if (mQuantity>0){
        mQuantity -= 1;
        updateQuantity();
    }}

    private void updateQuantity() {
        mQuantityTextView.setText(mQuantity+"");
    }

    private void eraseText() {
        mNameEditText.setText("");
        mPriceEditText.setText("");
        mQuantityTextView.setText("");
        mSupplierNameEditText.setText("");
        mSupplierPhoneEditText.setText("");
    }

    private void validateBook() {
        if (mPriceEditText != null) {
            long price = Long.parseLong(mPriceEditText.getText().toString().trim());

            BookDbHelper mDbHelper = new BookDbHelper(this);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BookEntry.COLUMN_BOOK_PRODUCT_NAME, mNameEditText.getText().toString().trim());
            values.put(BookEntry.COLUMN_BOOK_PRICE, price);
            values.put(BookEntry.COLUMN_BOOK_QUANTITY, mQuantity);
            values.put(BookEntry.COLUMN_BOOK_SUPPLIER, mSupplierNameEditText.getText().toString().trim());
            values.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE, mSupplierPhoneEditText.getText().toString().trim());
            db.insert(BookEntry.TABLE_NAME, null, values);
        } else {
            Toast.makeText(this, "missing fields", Toast.LENGTH_SHORT).show();
        }
        eraseText();
    }
}


