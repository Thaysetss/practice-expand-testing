package com.practice.expandingtesting.factory;

import com.practice.expandingtesting.model.UserModel;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;

@NoArgsConstructor
public class UserFactory {
    private final Faker faker = new Faker();

    public UserModel generateRandomUser() {
        String name = faker.name().name();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        return new UserModel(name, email, password);
    }

    public UserModel generateUserWithEmail(String email) {
        String name = faker.name().name();
        String password = faker.internet().password();
        return new UserModel(name, email, password);
    }

    public UserModel generateUserInvalidEmail() {
        String name = faker.name().name();
        String email = "test@test";
        String password = faker.internet().password();
        return new UserModel(name, email, password);
    }

    public UserModel generateUserNullName() {
        String name = null;
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        return new UserModel(name, email, password);
    }

    public UserModel generateUserNullPassword() {
        String name = faker.name().name();
        String email = faker.internet().emailAddress();
        String password = null;
        return new UserModel(name, email, password);
    }

    public UserModel generateUserNullEmail() {
        String name = faker.name().name();
        String email = null;
        String password = faker.internet().password();
        return new UserModel(name, email, password);
    }
}