package nl.rickrongen.fontys.kwetter.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Created by rick on 3/14/17.
 */
public class GroupTest {

    Group group;

    @BeforeEach
    void beforeTest() throws NoSuchFieldException, IllegalAccessException {
        group = new Group();
        Field name = Group.class.getDeclaredField("name");
        name.setAccessible(true);
        name.set(group, "admin");
    }

    @Test
    void getName(){
        assertEquals("admin", group.getName());
    }

    @Test
    void getId(){
        assertEquals(0, group.getId());
    }

    @Test
    void getSetUsers(){
        assertNull(group.getUsers());
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        group.setUsers(users);
        assertEquals(users, group.getUsers());
    }
}
