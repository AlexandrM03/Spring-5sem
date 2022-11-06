package by.belstu.mylife;

import by.belstu.mylife.payload.request.SignupRequest;
import by.belstu.mylife.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Before
    public void createUserWithoutActivation() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstname("Test");
        signupRequest.setLastname("Test");
        signupRequest.setUsername("Test");
        signupRequest.setPassword("Test");
        signupRequest.setConfirmPassword("Test");
        signupRequest.setEmail("test@gmail.io");
        userService.createUser(signupRequest);
    }

    @Test
    public void newUserNotActivated() {
        Assert.assertFalse(userService.isActiveUser("test@gmail.io"));
    }
}
