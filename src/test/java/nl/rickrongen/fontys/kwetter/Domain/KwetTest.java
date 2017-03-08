package nl.rickrongen.fontys.kwetter.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by rick on 2/22/17.
 */
class KwetTest {
    Kwet kwet;
    @BeforeEach
    void setUp() {
        kwet = new Kwet();
    }

    @Test
    void getSetText() {
        assertNull(kwet.getText());
        String text = "text";
        kwet.setText(text);
        assertEquals(text, kwet.getText());
    }

    @Test
    void getSetTags() {
        assertNull(kwet.getTags());
        List<String> tags = new LinkedList<>();
        tags.add("tagOne");
        tags.add("tagTwo");
        kwet.setTags(tags);
        assertTrue(tags.equals(kwet.getTags()));
    }

    @Test
    void getSetTweetedBy() {
        User u = new User();
        u.setUsername("usrn");
        kwet.setKwetBy(u);
        assertEquals(u.getUsername(), kwet.getKwetBy().getUsername());
    }

    @Test
    void getLikes() {
        assertNull(kwet.getLikedBy());
    }

    @Test
    void getId() {
        assertEquals(0, kwet.getId());
    }

}