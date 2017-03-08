package nl.rickrongen.fontys.kwetter.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by rick on 2/22/17.
 */
class UserTest {
    User user;
    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void getSetUsername() {
        assertNull(user.getUsername());
        user.setUsername("testusrn");
        assertEquals("testusrn", user.getUsername());
    }

    @Test
    void getSetFullName() {
        assertNull(user.getFullName());
        user.setFullName("My Name");
        assertEquals("My Name", user.getFullName());
    }

    @Test
    void getSetLocation() {
        assertNull(user.getLocation());
        user.setLocation("City");
        assertEquals("City", user.getLocation());
    }

    @Test
    void getSetWebsite() {
        assertNull(user.getWebsite());
        user.setWebsite("http://example.com/");
        assertEquals("http://example.com/", user.getWebsite());
    }

    @Test
    void getSetBiography() {
        assertNull(user.getBiography());
        user.setBiography("Thats me");
        assertEquals("Thats me", user.getBiography());
    }

    @Test
    void getSetProfilePicture() {
        assertNull(user.getProfilePicture());
        user.setProfilePicture("data:image/png;base64,ABA==");
        assertEquals("data:image/png;base64,ABA==", user.getProfilePicture());
    }

    @Test
    void getSetMentions() {
        assertNull(user.getMentionedIn());
        List<Kwet> kwets = new LinkedList<>();
        kwets.add(new Kwet(){{setText("text1");}});
        kwets.add(new Kwet(){{setText("text2");}});
        user.setMentionedIn(kwets);
        assertTrue(kwets.equals(user.getMentionedIn()));
    }

    @Test
    void getSetFollowing() {
        assertNull(user.getFollowing());
        List<User> others = new LinkedList<>();
        others.add(new User(){{user.setUsername("usr1");}});
        others.add(new User(){{user.setUsername("usr2");}});
        user.setFollowing(others);
        assertTrue(others.equals(user.getFollowing()));
    }

    @Test
    void getSetLikes() {
        assertNull(user.getLikes());
        List<Kwet> kwets = new LinkedList<>();
        kwets.add(new Kwet(){{setText("text1");}});
        kwets.add(new Kwet(){{setText("text2");}});
        user.setLikes(kwets);
        assertTrue(kwets.equals(user.getLikes()));
    }

    @Test
    void getId() {
        assertEquals(0, user.getId());
    }

    @Test
    void getSetFollowedBy() {
        assertNull(user.getFollowedBy());
        List<User> others = new LinkedList<>();
        others.add(new User(){{user.setUsername("usr1");}});
        others.add(new User(){{user.setUsername("usr2");}});
        user.setFollowedBy(others);
        assertTrue(others.equals(user.getFollowedBy()));
    }

    @Test
    void uniqueUsernameTest(){
        user.setUsername("test");
        assertEquals(user.hashCode(), user.getUsername().hashCode());
        Set<User> users = new HashSet<>();
        assertTrue(users.add(new User(){{setUsername("user1");}}));
        assertTrue(users.add(new User(){{setUsername("user2");}}));
        assertTrue(users.add(new User(){{setUsername("user3");}}));
        assertFalse(users.add(new User(){{setUsername("user1");}}));
    }
}