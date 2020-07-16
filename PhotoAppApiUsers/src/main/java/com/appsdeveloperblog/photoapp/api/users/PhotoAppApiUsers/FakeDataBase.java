package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class FakeDataBase<K, V>  extends HashMap<K, V> {

    public void statusMessage() {
        System.out.println("db up and running");
    }
}
