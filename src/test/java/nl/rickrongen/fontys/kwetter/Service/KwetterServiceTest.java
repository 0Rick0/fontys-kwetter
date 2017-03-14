package nl.rickrongen.fontys.kwetter.Service;

import nl.rickrongen.fontys.kwetter.DAO.IKwetterDao;
import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by rick on 3/14/17.
 */
class KwetterServiceTest {

    private KwetterService service = new KwetterService();

    @BeforeEach
    void setUp() {
        IKwetterDao tomok = service.kwetterDao = mock(IKwetterDao.class);
        Mockito.when(tomok.getUser(Mockito.anyString()))
                .thenAnswer(invocationOnMock ->
                        new User() {{
                            setUsername((String) invocationOnMock.getArguments()[0]);
                        }});
        Mockito.when(tomok.addUser(anyString())).thenReturn(true);
        Mockito.when(tomok.toggleFollowUser(anyObject(), anyObject())).thenReturn(true);
    }

    @Test
    void getUser() {
        assertEquals("username", service.getUser("username").getUsername());
    }

    @Test
    void updateUser() {
        service.updateUser(new User());
        verify(service.kwetterDao).updateUser(anyObject());
    }

    @Test
    void addUser() {
        assertTrue(service.addUser("newUser"));
        verify(service.kwetterDao).addUser(anyString());
    }

    @Test
    void addUser1() {
        Mockito.when(service.kwetterDao.updateUser(anyObject())).thenReturn(true);
        assertTrue(service.addUser("username", "fn", "pw", "lc", "ws", "bi"));
        verify(service.kwetterDao).addUser(anyString());
    }

    @Test
    void addUser1Abuse() {
        assertThrows(IllegalArgumentException.class, () -> service.addUser(null, null, null, null, null, null));
    }

    @Test
    void toggleFollow() {
        assertTrue(service.toggleFollow(new User(), new User()));
        verify(service.kwetterDao).toggleFollowUser(anyObject(), anyObject());

    }

    @Test
    void postKwet() {
        final List<Object[]> arguments = new ArrayList<>();
        Mockito.when(service.kwetterDao.postKwet(anyObject(), anyString(), anyList(), anyList())).then(m -> {
            arguments.add(m.getArguments());
            return true;
        });
        Mockito.when(service.kwetterDao.getKwetsOfUser(anyString(), anyInt(), anyInt())).thenAnswer(m->Collections.singletonList(new Kwet()));

        //because of mockito
        assertNotNull(service.postKwet(service.getUser("test"), "@You #YOLO #OnlyOnce my life"));

        User u = (User)arguments.get(0)[0];
        String text = (String)arguments.get(0)[1];
        List<String> tags = (List<String>) arguments.get(0)[2];
        List<User> users = (List<User>) arguments.get(0)[3];

        assertEquals("test", u.getUsername());
        assertEquals("@You #YOLO #OnlyOnce my life", text);
        assertEquals(Arrays.asList("YOLO", "OnlyOnce"), tags);
        assertEquals("You", users.get(0).getUsername());
    }

    @Test
    void searchKwets() {

    }

    @Test
    void getKwetsOfUser() {
        Mockito.when(service.kwetterDao.getKwetsOfUser(anyString(), anyInt(), anyInt())).then(a->Collections.emptyList());
        service.getKwetsOfUser("me",0, 10);
        verify(service.kwetterDao).getKwetsOfUser(anyString(), anyInt(), anyInt());
    }

    @Test
    void likeKwet() {
        Mockito.when(service.kwetterDao.likeKwet(anyObject(), anyObject())).thenReturn(true);
        assertTrue(service.likeKwet(service.getUser("me"), new Kwet()));
        verify(service.kwetterDao).likeKwet(anyObject(), anyObject());
    }

    @Test
    void getKwetById() {
        Kwet k = new Kwet();
        Mockito.when(service.kwetterDao.getKwetById(anyInt())).thenReturn(k);
        assertSame(k, service.getKwetById(123));
    }

    @Test
    void getUsers() {
        Mockito.when(service.kwetterDao.getUsers(anyInt(), anyInt())).thenReturn(Collections.emptyList());
        assertEquals(0, service.getUsers(0,10).size());
    }

    @Test
    void getKwetsByTag() {
        Mockito.when(service.kwetterDao.getKwetsByTag(anyString(), anyInt(), anyInt())).thenReturn(Collections.emptyList());
        assertEquals(0, service.getKwetsByTag("YOLO", 0, 10).size());
    }

    @Test
    void getUserFeed() {
        Mockito.when(service.kwetterDao.getUserFeed(anyObject(), anyInt(), anyInt())).thenReturn(Collections.emptyList());
        assertEquals(0, service.getUserFeed(service.getUser("username"), 0, 10).size());
    }

}