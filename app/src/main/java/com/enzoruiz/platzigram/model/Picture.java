package com.enzoruiz.platzigram.model;

/**
 * Created by Enzo on 21/06/2017.
 */

public class Picture {

    private String username;
    private String picture;
    private String time;
    private String likeNumber = "0";

    public Picture(String username, String picture, String time, String likeNumber) {
        this.username = username;
        this.picture = picture;
        this.time = time;
        this.likeNumber = likeNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(String likeNumber) {
        this.likeNumber = likeNumber;
    }
}
