package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static String TAG = "MainActivity";
    static final int LOGIN_REQUEST_CODE = 1;
    final List<Songs> songsLibrary = new ArrayList<>();
    FrontViewCustomAdapter arrayAdapter;
    //ArrayAdapter<Songs> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ListView songsListView = new ListView(this); //findViewById(R.id.mainListView);
        setContentView(songsListView);

        songsLibrary.add(new Songs("Kanye West", "", "https://lastfm.freetls.fastly.net/i/u/300x300/375a9688a2cb3d4e073bacf71735a63f.png", "", "Sphaghet codes with me"));
        songsLibrary.add(new Songs("Evolve", "Imagine Dragons", "https://lastfm.freetls.fastly.net/i/u/300x300/8c77e9f509c4dd3bca8d3ac6b5344ce5.png", "", "inspirational music from Imagine Dragons."));
        songsLibrary.add(new Songs("Destiny","NF","https://lastfm.freetls.fastly.net/i/u/300x300/65613775652434e5111cd24bb02503e4.png","","Heart Throbbing beats with deep lyrics"));
       arrayAdapter = new FrontViewCustomAdapter(this, R.id.frontArtistView , songsLibrary);

       songsListView.setAdapter(arrayAdapter);
       songsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);


        songsListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                int numChecked = songsListView.getCheckedItemCount();
                actionMode.setTitle(numChecked + " selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
            }
        });



    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            String song = data.getStringExtra("song");
            String artist = data.getStringExtra("artist");
            String album = data.getStringExtra("album");
            String photoURL = data.getStringExtra("photoURL");
            String comment = data.getStringExtra("comment");

            songsLibrary.add(new Songs(artist,song,photoURL,album,comment));
            arrayAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.addPostButton:
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}

