package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Data;

import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Spring framework creates an implementation for this interface but we need to
 * declare it and the method that we wanna be able to call
 */

@FeignClient(name = "albums-ws") //this is the name of the microservice in which feign is communicating with
public interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albums")
    public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}
