package domain;

import main.domain.Driver;
import main.domain.User;
import main.domain.UserGroup;
import main.domain.enums.Language;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by martijn on 24-6-2016.
 */
public class UserTest {

    @Test
    public void testGettersAndSetters() {
        User user = new User();
        UserGroup userGroup = new UserGroup();
        Driver driver = new Driver();

        /* Id */
        user.setId(1L);
        assertSame(1L, user.getId());

        /* Username */
        user.setUsername("Username");
        assertSame("Username", user.getUsername());

        /* Password */
        user.setPassword("Password");
        assertSame("Password", user.getUsername());

        /* Email */
        user.setEmail("test@test.nl");
        assertSame("test@test.nl", user.getEmail());

        /* Language */
        user.setLanguage(Language.EN);
        assertSame(Language.EN, user.getLanguage());

        /* User Group */
        user.setUserGroup(userGroup);
        assertSame(userGroup, user.getUserGroup());

        /* Driver */
        user.setDriver(driver);
        assertSame(driver, user.getDriver());
    }
}
