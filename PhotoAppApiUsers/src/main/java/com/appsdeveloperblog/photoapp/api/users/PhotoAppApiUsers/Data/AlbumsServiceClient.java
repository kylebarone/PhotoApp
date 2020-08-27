package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Data;

import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.AlbumResponseModel;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring framework creates an implementation for this interface but we need to
 * declare it and the method that we wanna be able to call
 */

@FeignClient(name = "albums-ws", fallbackFactory = AlbumsFallBackFactory.class) //this is the name of the microservice in which feign is communicating with
public interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albums")
    public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}

@Component
class AlbumsFallBackFactory implements FallbackFactory<AlbumsServiceClient> {

    @Override
    public AlbumsServiceClient create(Throwable throwable) {
        return new AlbumsServiceClientFallback(throwable);
    }
}

class AlbumsServiceClientFallback implements AlbumsServiceClient {

    private final Throwable cause;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public AlbumsServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public List<AlbumResponseModel> getAlbums(String id) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            logger.error("404 error took place when getAlbums was called with userId: "
            + id + ". Error Message: " + cause.getLocalizedMessage());
        } else {
            logger.error("Other error took place: " + cause.getLocalizedMessage());
        }
        return new ArrayList<>();
    }
}
