package tests.services;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;

import main.domain.User;
import main.service.UserService;
import tests.TestHelper;

/**
 * @author Sam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Arquillian.class)
public class UserServiceTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return TestHelper.createDeployment();
    }

    @Inject
    private UserService userService;

    @Test
    public void test1RegisterUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("henk@gmail.com");

        userService.registerUser(user);

        Assert.assertNotNull(userService.findById(1L));
    }

    @Test
    public void test2getUserByCredentials() {
        Assert.assertNotNull(userService.getUserByCredentials("henk@gmail.com", "henk"));
    }
}
