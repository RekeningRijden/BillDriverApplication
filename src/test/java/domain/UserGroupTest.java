package domain;

import main.domain.UserGroup;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by martijn on 24-6-2016.
 */
public class UserGroupTest {

    @Test
    public void testGettersAndSetters() {
        UserGroup userGroup = new UserGroup();

        /* Id */
        userGroup.setId(1L);
        assertSame(1L, userGroup.getId());

        /* Username */
        userGroup.setName("Admin");
        assertSame("Admin", userGroup.getName());
    }
}
