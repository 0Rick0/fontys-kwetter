package nl.rickrongen.fontys.kwetter.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by rick on 2/22/17.
 */
abstract class IKwetterDaoTest<T extends IKwetterDao> {

    IKwetterDao dao;

    public abstract T createInstance();

    @BeforeEach
    void setup(){
        dao = createInstance();
    }

    @Test
    void addUser() {

    }

    @Test
    void getUser() {

    }

    @Test
    void toggleFollowUser() {

    }

    @Test
    void updateUser() {

    }

    @Test
    void postKwet() {

    }

    @Test
    void likeKwet() {

    }

    @Test
    void getKwetsOfUser() {

    }

    @Test
    void searchKwets() {

    }

    @Test
    void getFollowerCount() {

    }

    @Test
    void getFollowingCount() {

    }

}