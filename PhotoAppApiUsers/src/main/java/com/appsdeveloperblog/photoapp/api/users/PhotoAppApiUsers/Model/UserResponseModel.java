package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model;

import java.util.List;

/**
 * Returns User Details
 */
public class UserResponseModel {

    private String name;
    private String userId;
    private String username;
    private List<AlbumResponseModel> albums;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<AlbumResponseModel> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumResponseModel> albums) {
        this.albums = albums;
    }
}
