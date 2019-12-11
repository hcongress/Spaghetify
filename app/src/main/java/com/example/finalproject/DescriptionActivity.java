package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {
    EditText commentEditText;
    Button postButton;
    TextView selectedView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        commentEditText = findViewById(R.id.commentEditText);
        postButton = findViewById(R.id.postButton);
        selectedView = findViewById(R.id.selectedView);

        Intent intent = getIntent();

        final String song = intent.getStringExtra("song");
        final String artist = intent.getStringExtra("artist");
        final String album = intent.getStringExtra("album");
        final String photoURL = intent.getStringExtra("photoURL");

        selectedView.setText(song);


        postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.putExtra("artist",artist);
                intent.putExtra("song",song);
                intent.putExtra("photoURL",photoURL);
                intent.putExtra("album", album);
                EditText desc = findViewById(R.id.commentEditText);
                String comment = desc.getText().toString();
                intent.putExtra("comment", comment);

                setResult(Activity.RESULT_OK,intent);
                DescriptionActivity.this.finish();

            }
        });


    }
}
