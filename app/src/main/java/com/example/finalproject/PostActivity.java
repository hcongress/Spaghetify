package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    Button albumSearchButton;
    Button songSearchButton;
    Button artistSearchButton;
    EditText searchEditText;
    ListView searchListView;
    final List<Songs> songsLibrary =  new ArrayList<>();;
    final String KEY = "45e8b846aa90e9bf51c22b51d286d71e";
    static final int DESC_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        searchListView = findViewById(R.id.searchResultListView);

        final ArrayAdapter<Songs> arrayAdapter = new FrontViewCustomAdapter(this, R.id.frontArtistView , songsLibrary);

        searchListView.setAdapter(arrayAdapter);
        searchListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        albumSearchButton = findViewById(R.id.albumSearchButton);
        songSearchButton = findViewById(R.id.songSearchButton);
        artistSearchButton = findViewById(R.id.artistSearchButton);

        searchEditText = findViewById(R.id.russiaVariety);

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(PostActivity.this, DescriptionActivity.class);
                intent.putExtra("artist", songsLibrary.get(position).getArtist());
                intent.putExtra("song", songsLibrary.get(position).getSong());
                intent.putExtra("photoURL", songsLibrary.get(position).getPhotoURL());
                intent.putExtra("album", songsLibrary.get(position).getAlbum());

                startActivityForResult(intent,DESC_REQUEST_CODE );

            }
        });

        albumSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songsLibrary.clear();
                String searchEditTextResult = searchEditText.getText().toString();

                if (!(searchEditTextResult.matches(""))) {
                    RequestQueue requestQueue = Volley.newRequestQueue(PostActivity.this);
                    String URL = "http://ws.audioscrobbler.com/2.0/?method=Album.search&album=";
                    URL += searchEditTextResult
                            + "&api_key="
                            + KEY
                            + "&format=json";

                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,

                            URL, null,
                            new Response.Listener() {
                                @Override
                                public void onResponse(Object response) {
                                    String responseString = response.toString();

                                    try {
                                        JSONObject jsonObject = new JSONObject(responseString);
                                        JSONObject results = jsonObject.getJSONObject("results");
                                        JSONObject albumMatches = results.getJSONObject("albummatches");
                                        JSONArray albumArray = albumMatches.getJSONArray("album");


                                        for (int k = 0; k < 10; k++) {
                                            JSONObject album = albumArray.getJSONObject(k);
                                            String name = album.getString("name");
                                            String artist = album.getString("artist");
                                            JSONArray images = album.getJSONArray("image");
                                            JSONObject image = images.getJSONObject(3);
                                            String imageURL = image.getString("#text");
                                            Log.e("albumLoop", name + " " + imageURL);

                                            songsLibrary.add(new Songs(artist, name, imageURL, name));
                                        }
                                        arrayAdapter.notifyDataSetChanged();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    Log.e("Rest Response", response.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Rest Response", error.toString());
                                }
                            });
                    requestQueue.add(objectRequest);


                } else
                    Toast.makeText(PostActivity.this, "You need to search something", Toast.LENGTH_SHORT).show();
            }
        });


        songSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songsLibrary.clear();
                String searchEditTextResult = searchEditText.getText().toString();

                if (!(searchEditTextResult.matches(""))) {
                    RequestQueue requestQueue = Volley.newRequestQueue(PostActivity.this);
                    String URL = "http://ws.audioscrobbler.com/2.0/?method=track.search&track=";
                    URL += searchEditTextResult
                            + "&api_key="
                            + KEY
                            + "&format=json";

                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,

                            URL, null,
                            new Response.Listener() {
                                @Override
                                public void onResponse(Object response) {
                                    Log.e("Rest Response", response.toString());

                                    String responseString = response.toString();

                                    try {
                                        JSONObject jsonObject = new JSONObject(responseString);
                                        JSONObject results = jsonObject.getJSONObject("results");
                                        JSONObject trackmatchs = results.getJSONObject("trackmatches");
                                        JSONArray tracks = trackmatchs.getJSONArray("track");

                                        for (int k = 0; k < 10; k++) {
                                            JSONObject track = tracks.getJSONObject(k);
                                            String songName = track.getString("name");
                                            String songArtist = track.getString("artist");
                                            JSONArray images = track.getJSONArray("image");
                                            JSONObject image = images.getJSONObject(3);
                                            String imageURL = image.getString("#text");
                                            Log.e("songLoop", songName + " " + songArtist);

                                            songsLibrary.add(new Songs(songName, songArtist, imageURL, ""));
                                        }
                                        arrayAdapter.notifyDataSetChanged();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Rest Response", error.toString());
                                }
                            });
                    requestQueue.add(objectRequest);

                } else
                    Toast.makeText(PostActivity.this, "You need to search something", Toast.LENGTH_SHORT).show();
            }
        });
        artistSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songsLibrary.clear();
                String searchEditTextResult = searchEditText.getText().toString();

                if (!(searchEditTextResult.matches(""))) {
                    RequestQueue requestQueue = Volley.newRequestQueue(PostActivity.this);
                    String URL = "http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=";
                    URL += searchEditTextResult
                            + "&api_key="
                            + KEY
                            + "&format=json";

                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,

                            URL, null,
                            new Response.Listener() {
                                @Override
                                public void onResponse(Object response) {
                                    String responseString = response.toString();

                                    try {
                                        JSONObject jsonObject = new JSONObject(responseString);
                                        JSONObject results = jsonObject.getJSONObject("results");
                                        JSONObject artistMatches = results.getJSONObject("artistmatches");
                                        JSONArray artistsArray = artistMatches.getJSONArray("artist");

                                        for (int k = 0; k < 10; k++) {
                                            JSONObject artist = artistsArray.getJSONObject(k);
                                            String name = artist.getString("name");
                                            JSONArray images = artist.getJSONArray("image");
                                            JSONObject image = images.getJSONObject(3);
                                            String imageURL = image.getString("#text");

                                            songsLibrary.add(new Songs(name, "", imageURL, ""));
                                        }
                                        arrayAdapter.notifyDataSetChanged();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Log.e("Rest Response", response.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Rest Response", error.toString());
                                }
                            });
                    requestQueue.add(objectRequest);

                } else
                    Toast.makeText(PostActivity.this, "You need to search something", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DESC_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            setResult(Activity.RESULT_OK,data);
            this.finish();
        }

    }
}
