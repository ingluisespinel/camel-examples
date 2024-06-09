package com.lhespinel.camel.restapi.repository;

import com.lhespinel.camel.restapi.model.User;
import org.apache.camel.Body;
import org.apache.camel.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakeUsersRepository {
    private final List<User> users;

    public FakeUsersRepository(){
        users = new ArrayList<>();
    }

    public List<User> find(){
        return users;
    }

    public User save(@Body User user){
        user.setId(UUID.randomUUID().toString());
        users.add(user);
        return user;
    }

    public boolean delete(@Header("userId") String userId){
        return users.remove(User.builder().id(userId).build());
    }

}
