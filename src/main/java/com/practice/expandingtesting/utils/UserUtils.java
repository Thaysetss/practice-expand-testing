package com.practice.expandingtesting.utils;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.UserFactory;
import com.practice.expandingtesting.model.UserModel;

public class UserUtils {
    public UserModel authenticationNewUser(){
        UserModel user = new UserFactory().generateRandomUser();
        String password = user.getPassword();
        new UsersClient().postRegisterNewUser(user);
        user = new UsersClient().postLoginReturnUser(user);
        user.setPassword(password);
        return user;
    }

    public UserModel authenticationNewUserWithEmail(String email){
        UserModel user = new UserFactory().generateUserWithEmail(email);
        String password = user.getPassword();
        new UsersClient().postRegisterNewUser(user);
        user = new UsersClient().postLoginReturnUser(user);
        user.setPassword(password);
        return user;
    }
}