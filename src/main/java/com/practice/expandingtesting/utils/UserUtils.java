package com.practice.expandingtesting.utils;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.UserFactory;
import com.practice.expandingtesting.model.UserModel;

public class UserUtils {
    public UserModel authenticationNewUser(){
        UserModel user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewUser(user);
        user = new UsersClient().postLoginReturnUser(user);
        return user;
    }

    public UserModel authenticationNewUserWithEmail(String email){
        UserModel user = new UserFactory().generateUserWithEmail(email);
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        new UsersClient().postRegisterNewUser(user);
        user = new UsersClient().postLoginReturnUser(user);
        return user;
    }
}