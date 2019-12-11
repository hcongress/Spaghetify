package com.example.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FrontViewCustomAdapter extends ArrayAdapter<Songs> {

    List<Songs> listOfSongs;
    Context context;
    Bitmap imageBit;
    View view;

    public FrontViewCustomAdapter(Context context, int id, List<Songs> helperList)
    {
        super(context, id, helperList);
        this.listOfSongs = helperList;
        this.context = context;
    }

    public View getView(int postion, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater airpump = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = airpump.inflate(R.layout.lastfm_front_page, parent, false);

        Songs song = listOfSongs.get(postion);
        TextView songView = view.findViewById(R.id.frontSongView);
        songView.setText(song.getSong());

        TextView artistView = view.findViewById(R.id.frontArtistView);
        artistView.setText(song.getArtist());

        TextView commentView = view.findViewById(R.id.frontCommentView);
        commentView.setText(song.getDescription());

        fetchImage(song.getPhotoURL());
        //imageBox.setImageBitmap(imageBit);




        return view;
    }

    final public void fetchImage(String url){
        PhotoRequestAsyncTask asyncTask = new PhotoRequestAsyncTask();
        asyncTask.execute(url);
    }

    public void imageRecieved(Bitmap bitmap){
        ImageView imageView = view.findViewById(R.id.frontImageBox);
        imageView.setImageBitmap(bitmap);
    }

    class PhotoRequestAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)
                        url.openConnection();

                InputStream in = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageRecieved(bitmap);

        }
    }
}
