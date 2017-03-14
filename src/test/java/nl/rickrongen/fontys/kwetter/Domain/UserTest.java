package nl.rickrongen.fontys.kwetter.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

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
    void constructorTest(){
        List<User> following = Arrays.asList(user);
        List<User> followedBy = Arrays.asList(user);
        List<Group> groups = Arrays.asList(new Group(), new Group());
        Kwet testkwet = new Kwet();
        List<Kwet> mentionedIn = Arrays.asList(testkwet);
        List<Kwet> kwets = Arrays.asList(testkwet);
        List<Kwet> like = Arrays.asList(testkwet);
        User u = new User("username_test",
                "password",
                "Full Name",
                "Location",
                "WebSite",
                "Bio",
                "BASE64 image",
                following,
                followedBy,
                groups,
                mentionedIn,
                kwets,
                like);
        assertEquals("username_test", u.getUsername());
        assertEquals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8".toUpperCase(), u.getPassword());
        assertEquals("Full Name", u.getFullName());
        assertEquals("Location", u.getLocation());
        assertEquals("WebSite", u.getWebsite());
        assertEquals("Bio", u.getBiography());
        assertEquals("BASE64 image", u.getProfilePicture());
        assertEquals(following, u.getFollowing());
        assertEquals(followedBy, u.getFollowedBy());
        assertEquals(groups, u.getGroups());
        assertEquals(mentionedIn, u.getMentionedIn());
        assertEquals(kwets, u.getKwets());
        assertEquals(like, u.getLikes());
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
        assertTrue(user.getMentionedIn().isEmpty());
        List<Kwet> kwets = new LinkedList<>();
        kwets.add(new Kwet(){{setText("text1");}});
        kwets.add(new Kwet(){{setText("text2");}});
        user.setMentionedIn(kwets);
        assertTrue(kwets.equals(user.getMentionedIn()));
    }

    @Test
    void getSetFollowing() {
        assertTrue(user.getFollowing().isEmpty());
        List<User> others = new LinkedList<>();
        others.add(new User(){{user.setUsername("usr1");}});
        others.add(new User(){{user.setUsername("usr2");}});
        user.setFollowing(others);
        assertTrue(others.equals(user.getFollowing()));
    }

    @Test
    void getSetLikes() {
        assertTrue(user.getLikes().isEmpty());
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
        assertTrue(user.getFollowedBy().isEmpty());
        List<User> others = new LinkedList<>();
        others.add(new User(){{user.setUsername("usr1");}});
        others.add(new User(){{user.setUsername("usr2");}});
        user.setFollowedBy(others);
        assertTrue(others.equals(user.getFollowedBy()));
    }

    @Test
    void getSetKwets() {
        assertTrue(user.getKwets().isEmpty());
        List<Kwet> kwets = new LinkedList<>();
        kwets.add(new Kwet(){{setText("text1");}});
        kwets.add(new Kwet(){{setText("text2");}});
        user.setKwets(kwets);
        assertTrue(kwets.equals(user.getKwets()));
    }

    @Test
    void getSetGroups() {
        assertTrue(user.getGroups().isEmpty());
        List<Group> groups = new LinkedList<>();
        groups.add(new Group());
        groups.add(new Group());
        user.setGroups(groups);
        assertTrue(groups.equals(user.getGroups()));
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

    @Test
    void testPassword(){
        assertNull(user.getPassword());
        user.setPassword("test");
        assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08".toUpperCase(), user.getPassword());
    }
}