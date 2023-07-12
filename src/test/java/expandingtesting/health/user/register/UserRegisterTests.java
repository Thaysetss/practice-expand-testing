package expandingtesting.health.user.register;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.UserFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserRegisterTests {

    @Test
    @DisplayName("Test add new user successfully with random data.")
    void createNewRandomDataUser() {
        var user = new UserFactory().generateRandomUser();
        Response response = new UsersClient().postNewRandomUser(user);
        assertThat(response.statusCode(), is(SC_CREATED));
        assertThat(response.body().jsonPath().get("data.name"), is(user.getName()));
        assertThat(response.body().jsonPath().get("data.email"), is(user.getEmail()));
        assertThat(response.body().jsonPath().get("success"), is(true));
        assertThat(response.body().jsonPath().get("status"), is(SC_CREATED));
        assertThat(response.body().jsonPath().get("message"), is(USER_REGISTER_ACCOUNT_CREATED.message));
    }

    @Test
    @DisplayName("Test adding new user with invalid email.")
    void createNewUserInvalidEmail() {
        var user = new UserFactory().generateUserInvalidEmail();
        Response response = new UsersClient().postNewRandomUser(user);
        assertThat(response.statusCode(), is(SC_BAD_REQUEST));
        assertThat(response.body().jsonPath().get("success"), is(false));
        assertThat(response.body().jsonPath().get("message"), is(USER_REGISTER_INVALID_EMAIL.message));
    }

    @Test
    @DisplayName("Test adding new user with null name.")
    void createNewUserNullName() {
        var user = new UserFactory().generateUserNullName();
        Response response = new UsersClient().postNewRandomUser(user);
        assertThat(response.statusCode(), is(SC_BAD_REQUEST));
        assertThat(response.body().jsonPath().get("success"), is(false));
        assertThat(response.body().jsonPath().get("message"), is(USER_REGISTER_NULL_NAME.message));
    }

    @Test
    @DisplayName("Test adding new user with null password.")
    void createNewUserNullPassword() {
        var user = new UserFactory().generateUserNullPassword();
        Response response = new UsersClient().postNewRandomUser(user);
        assertThat(response.statusCode(), is(SC_BAD_REQUEST));
        assertThat(response.body().jsonPath().get("success"), is(false));
        assertThat(response.body().jsonPath().get("message"), is(USER_REGISTER_NULL_PASSWORD.message));
    }

    @Test
    @DisplayName("Test adding new user with null email.")
    void createNewUserNullEmail() {
        var user = new UserFactory().generateUserNullEmail();
        Response response = new UsersClient().postNewRandomUser(user);
        assertThat(response.statusCode(), is(SC_BAD_REQUEST));
        assertThat(response.body().jsonPath().get("success"), is(false));
        assertThat(response.body().jsonPath().get("message"), is(USER_REGISTER_INVALID_EMAIL.message));
    }
}