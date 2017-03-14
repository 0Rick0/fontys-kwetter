package nl.rickrongen.fontys.kwetter.DAO;

import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by rick on 2/22/17.
 */
abstract class IKwetterDaoTest<T extends IKwetterDao> {

    IKwetterDao dao;

    public abstract T createInstance();
    public abstract void flush();

    @BeforeEach
    void setup(){
        dao = createInstance();
    }

    @Test
    void addGetUser() {
        assertTrue(dao.addUser("testUserAddUser"));
        flush();
        assertFalse(dao.addUser("testUserAddUser"));
        User u;
        assertNotNull((u = dao.getUser("testUserAddUser")));
        assertTrue(dao.getUsers(0, 10).contains(u));
    }

    @Test
    void toggleFollowUser() {
        assertTrue(dao.addUser("followTest1"));
        assertTrue(dao.addUser("followTest2"));
        User u1 = dao.getUser("followTest1");
        User u2 = dao.getUser("followTest2");
        assertFalse(u1.getFollowing().contains(u2));
        assertTrue(dao.toggleFollowUser(u1, u2));

        //refresh
        u1 = dao.getUser("followTest1");
        u2 = dao.getUser("followTest2");
        assertTrue(u1.getFollowing().contains(u2));

        assertFalse(dao.toggleFollowUser(u1, u2));

        //refresh
        u1 = dao.getUser("followTest1");
        u2 = dao.getUser("followTest2");
        assertFalse(u1.getFollowing().contains(u2));

        assertFalse(dao.toggleFollowUser(null, null));
    }

    @Test
    void updateUser() {
        assertTrue(dao.addUser("updateUserTest"));
        User updateTest = dao.getUser("updateUserTest");

        assertNull(updateTest.getBiography());
        updateTest.setBiography("New BIO");

        assertTrue(dao.updateUser(updateTest));

        //refresh
        updateTest = dao.getUser("updateUserTest");
        assertEquals("New BIO", updateTest.getBiography());
    }

    @Test
    void postKwet() {
        assertTrue(dao.addUser("postTest"));
        User u = dao.getUser("postTest");

        boolean b = dao.postKwet(u, "Test Kwet", Collections.emptyList(), Collections.emptyList());
        List<Kwet> kwets = dao.getKwetsOfUser("postTest", 0, 10);
        assertTrue(kwets.stream().anyMatch(k -> k.getText().equals("Test Kwet")));
        //todo test mentions and tags
    }

    @Test
    void likeKwet() {
        assertTrue(dao.addUser("likeTest"));
        User u = dao.getUser("likeTest");

        boolean b = dao.postKwet(u, "Test Kwet", Collections.emptyList(), Collections.emptyList());
        List<Kwet> kwets = dao.getKwetsOfUser("likeTest", 0, 10);
        assertTrue(kwets.stream().anyMatch(k -> k.getText().equals("Test Kwet")));

        Kwet k = kwets.get(0);
        int oldsize = k.getLikedBy() == null ? 0 : k.getLikedBy().size();
        assertTrue(dao.likeKwet(u, k));
        k = dao.getKwetById(k.getId());
        assertEquals(oldsize+1, k.getLikedBy().size());

        assertFalse(dao.likeKwet(u, k));
        k = dao.getKwetById(k.getId());
        assertEquals(oldsize, k.getLikedBy().size());

        assertFalse(dao.likeKwet(null, null));
    }


    @Test
    void searchKwets() {

    }

    @Test
    void getKwetByIdAbuse(){
        assertNull(dao.getKwetById(-1));
    }

//    @Test
//    void getFollowerCount() {
//        List<User> followers=new ArrayList<>(10);
//        for (int i = 0; i < 10; i ++){
//            assertTrue(dao.addUser("getFollowCount" + i));
//            followers.add(dao.getUser("getFollowCount" + i));
//        }
//
//        assertTrue(dao.addUser("getFollowCountBase"));
//        User base = dao.getUser("getFollowCountBase");
//
//        for (User u :
//                followers) {
//            assertTrue(dao.toggleFollowUser(u, base));
//        }
//
//        assertEquals(10, dao.getFollowerCount(base.getUsername()));
//        assertEquals(1, dao.getFollowingCount("getFollowCount1"));
//    }
}