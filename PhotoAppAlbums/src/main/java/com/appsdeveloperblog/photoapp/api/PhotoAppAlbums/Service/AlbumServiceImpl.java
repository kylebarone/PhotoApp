package com.appsdeveloperblog.photoapp.api.PhotoAppAlbums.Service;

import com.appsdeveloperblog.photoapp.api.PhotoAppAlbums.Data.AlbumDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumsService{

    @Override
    public List<AlbumDTO> getAlbums(String userId) {

        List<AlbumDTO> albumList = new ArrayList<>();
        // come back and make this more flexible
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setId(1L);
        albumDTO.setAlbumId("album1");
        albumDTO.setName("Album 1");
        albumDTO.setDescription("This is album 1");
        albumDTO.setUserId(userId);

        AlbumDTO albumDTO2 = new AlbumDTO();
        albumDTO2.setId(2L);
        albumDTO2.setAlbumId("album2");
        albumDTO2.setName("Album 2");
        albumDTO2.setDescription("This is album 2");
        albumDTO2.setUserId(userId);

        albumList.add(albumDTO);
        albumList.add(albumDTO2);

        return albumList;
    }
}
