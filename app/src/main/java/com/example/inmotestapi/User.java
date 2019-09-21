package com.example.inmotestapi;

import com.google.gson.annotations.SerializedName;

class User {

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatar_url;

    public User(String login, String avatar_url){
        this.login = login;
        this.avatar_url = avatar_url;
    }

    public String getName(){
        return login;
    }
    public void setName(String name){
        this.login = name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
