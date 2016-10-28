package com.example.jacobwilliams.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by jacobwilliams on 10/25/16.
 */

public class toDoDetailActivity extends AppCompatActivity {
    private EditText toDoTitle;
    private EditText toDoText;
    private EditText toDoDateMonth;
    private EditText toDoDateDay;
    private EditText toDoDateTime;
    private Button saveButton;
    private ImageButton imgButton;
    private EditText toDoCategory;
    private int index;
    private static int RESULT_LOAD_IMG = 1;
    private String imgDecodableString;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_detail);

        toDoTitle = (EditText) findViewById(R.id.toDoTitle);

        toDoText = (EditText) findViewById(R.id.toDoText);

        iv = (ImageView) findViewById(R.id.imageView);

        toDoDateMonth = (EditText) findViewById(R.id.toDoDueDateMonth);

        toDoDateDay = (EditText) findViewById(R.id.toDoDueDateDay);

        toDoDateTime = (EditText) findViewById(R.id.toDoDueDateTime);

        toDoCategory = (EditText) findViewById(R.id.toDoCategory);

        saveButton = (Button) findViewById(R.id.save_button);

        imgButton = (ImageButton) findViewById(R.id.button1);

        Intent doStuff = getIntent();

        toDoTitle.setText(doStuff.getStringExtra("Title"));
        toDoText.setText(doStuff.getStringExtra("Text"));
        toDoDateMonth.setText(doStuff.getStringExtra("Month"));
        toDoDateDay.setText(doStuff.getStringExtra("Day"));
        toDoDateTime.setText(doStuff.getStringExtra("Time"));
        toDoCategory.setText(doStuff.getStringExtra("Category"));
        index = doStuff.getIntExtra("Index", -1);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("Category", toDoCategory.getText().toString());
                intent.putExtra("Title", toDoTitle.getText().toString());
                intent.putExtra("Text", toDoText.getText().toString());
                intent.putExtra("Month", toDoDateMonth.getText().toString() + "/");
                intent.putExtra("Day", toDoDateDay.getText().toString() + "-");
                intent.putExtra("Time", toDoDateTime.getText().toString());
                intent.putExtra("Index", index);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagefromGallery(v);
            }
        });
    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                iv.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }
}

