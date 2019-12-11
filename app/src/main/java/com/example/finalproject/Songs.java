package com.example.finalproject;

import androidx.annotation.NonNull;

public class Songs {
    String id;
    String artist;
    String song;
    String photoURL;
    String album;
    String description;

    public Songs( String artists, String song, String photoURL, String album) {
        this.artist = artists;
        this.song = song;
        this.photoURL = photoURL;
        this.album = album;
    }

    public Songs( String artists, String song, String photoURL, String album, String description) {
        this.artist = artists;
        this.song = song;
        this.photoURL = photoURL;
        this.album = album;
        this.description =description;
    }

    public Songs(){
        this.id = "";
        this.artist = "";
        this.song= "";
        this.photoURL="";
        this.album="";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @NonNull
    @Override
    public String toString() {
        return song + " by: " + artist;
    }
}

