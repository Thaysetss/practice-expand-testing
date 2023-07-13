package expandingtesting.user;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.UserFactory;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.USER_LOGIN_SUCCESS;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class UserLoginTests {

    @Test
    @DisplayName("Test add new user successfully with random data.")
    void createNewRandomDataUser() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewRandomUser(user);
        ValidatableResponse response = new UsersClient().postLogin(user);
        response.statusCode(SC_OK)
                .body("success", is(true))
                .body("status", is(SC_OK))
                .body("message", is(USER_LOGIN_SUCCESS.message))
                .body("data.id", is(not(empty())))
                .body("data.name", is(user.getName()))
                .body("data.email", is(user.getEmail()))
                .body("data.token", is(not(empty())));
    }
}
