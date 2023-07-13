package com.practice.expandingtesting.utils;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.UserFactory;
import com.practice.expandingtesting.model.UserModel;

public class UserUtils {
    public UserModel AuthenticationNewUser(){
        UserModel user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewRandomUser(user);
        user = new UsersClient().postLoginReturnUser(user);
        return user;
    }
}
