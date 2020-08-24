package com.appsdeveloperblog.photoapp.api.PhotoAppAlbums.Controller;

import com.appsdeveloperblog.photoapp.api.PhotoAppAlbums.Data.AlbumDTO;
import com.appsdeveloperblog.photoapp.api.PhotoAppAlbums.Model.AlbumResponseModel;
import com.appsdeveloperblog.photoapp.api.PhotoAppAlbums.PhotoAppAlbumsApplication;
import com.appsdeveloperblog.photoapp.api.PhotoAppAlbums.Service.AlbumServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users/{id}/albums")
public class AlbumsController {

    @Autowired
    AlbumServiceImpl albumService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<AlbumResponseModel>> getAlbums(@PathVariable String id) {

        List<AlbumResponseModel> returnValue = new ArrayList<>();

        List<AlbumDTO> albumDTOS = albumService.getAlbums(id);
        if (albumDTOS == null || albumDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Type listType = new TypeToken<List<AlbumResponseModel>>(){}.getType();
        returnValue = new ModelMapper().map(albumDTOS, listType);
        logger.info("Returning " + returnValue.size() + " albums");
        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

}
