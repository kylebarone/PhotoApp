package com.appsdeveloperblog.photoapp.api.PhotoAppAlbums.Service;


import com.appsdeveloperblog.photoapp.api.PhotoAppAlbums.Data.AlbumDTO;

import java.util.List;

public interface AlbumsService {

    public List<AlbumDTO> getAlbums(String userId);
}
