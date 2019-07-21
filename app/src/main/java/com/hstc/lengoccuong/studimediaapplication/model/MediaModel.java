package com.hstc.lengoccuong.studimediaapplication.model;

public class MediaModel {
    private String id,title,album,artist,data,displayName;
    private boolean isSelect;
    private  long duration;

    public MediaModel(String id, String title, String album, String artist, String data, String displayName, long duration) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.data = data;
        this.displayName = displayName;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getData() {
        return data;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getDuration() {
        return duration;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "\nId: " + id
                + "\nTitle: " + title
                + "\nAlbum: " + album
                + "\nArtist: " + artist
                + "\nData: " + data
                + "\nDuration: " + duration
                + "\nDisplayName: " + displayName;
    }
}
