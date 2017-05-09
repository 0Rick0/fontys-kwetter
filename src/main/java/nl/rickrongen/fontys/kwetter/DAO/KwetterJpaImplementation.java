package nl.rickrongen.fontys.kwetter.DAO;

import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by rick on 2/15/17.
 */
@Stateless
@JPA
public class KwetterJpaImplementation implements IKwetterDao {

    @PersistenceContext(unitName = "JaeMySQLSource")
    EntityManager context;

    @Override
    public boolean addUser(String username) {
        if (getUser(username) != null)
            return false;
        User u = new User();
        u.setUsername(username);
        context.persist(u);
        context.flush();
        context.refresh(u);
        return u.getId() != 0;
    }

    @Override
    public User getUser(String username) {
        Query namedQuery = context.createNamedQuery("User.getUserByName");
        namedQuery.setParameter("username", username);
        try {
            return (User) namedQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public boolean toggleFollowUser(User actor, User toFollow) {
        if (actor == null || toFollow == null)
            return false;
        actor = context.merge(actor);
        toFollow = context.merge(toFollow);
        if (actor == null || toFollow == null)
            return false;
        if (actor.getFollowing().contains(toFollow)) {
            actor.getFollowing().remove(toFollow);
            toFollow.getFollowedBy().remove(actor);
        } else {
            actor.getFollowing().add(toFollow);
            toFollow.getFollowedBy().add(actor);
        }
        context.merge(actor);
        context.merge(toFollow);
        return actor.getFollowing().contains(toFollow);
    }

    @Override
    public boolean updateUser(User actor) {
        return context.merge(actor) != null;
    }

    @Override
    public boolean postKwet(User actor, String text, List<String> tags, List<User> mentions) {
        Kwet kwet = new Kwet();
        kwet.setText(text);
        kwet.setKwetBy(actor);
        kwet.setTags(tags);
        kwet.setMentions(mentions);
        context.persist(kwet);

        actor.getKwets().add(kwet);
        context.merge(actor);
        for (User m :
                mentions) {
            m.getMentionedIn().add(kwet);
            context.merge(m);
        }

        return true;
    }

    @Override
    public boolean likeKwet(User actor, Kwet kwet) {
        if (kwet == null || actor == null)
            return false;
        kwet = context.merge(kwet);
        actor = context.merge(actor);
        if (kwet == null || actor == null)
            return false;
        if (kwet.getLikedBy().contains(actor)) {
            kwet.getLikedBy().remove(actor);
            actor.getLikes().remove(kwet);
        } else {
            kwet.getLikedBy().add(actor);
            actor.getLikes().add(kwet);
        }

        context.merge(actor);
        context.merge(kwet);
        return kwet.getLikedBy().contains(actor);
    }

    @Override
    public List<Kwet> getKwetsOfUser(String username, int start, int count) {
        Query namedQuery = context.createNamedQuery("Kwet.getKwetsOfUser");
        namedQuery.setParameter("username", username);
        namedQuery.setFirstResult(start);
        namedQuery.setMaxResults(count);
        List resultList = namedQuery.getResultList();
        if(start == 0 && count == 1)
            context.refresh(resultList.get(0));
        return (List<Kwet>) resultList;
    }

    @Override
    public List<Kwet> searchKwets(List<String> texts, List<String> tags, List<String> mentions, int start, int count) {
        String param = "(" + String.join(
                "|",
                texts.stream().map(s -> Pattern.quote(s)).collect(Collectors.toList())) + ")"; //build regex
        Query namedQuery = context.createNamedQuery("kwet.native.searchregexp");
        namedQuery.setParameter(1, param);
        return (List<Kwet>) namedQuery.getResultList();
    }

    @Override
    public Kwet getKwetById(int id) {
        Query namedQuery = context.createNamedQuery("Kwet.getKwetById");
        namedQuery.setParameter("id", id);
        try {
            return (Kwet) namedQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<User> getUsers(int start, int count) {
        Query namedQuery = context.createNamedQuery("User.getAllUsers");
        namedQuery.setFirstResult(start);
        namedQuery.setMaxResults(count);
        return (List<User>) namedQuery.getResultList();
    }

    @Override
    public List<Kwet> getKwetsByTag(String tag, int start, int count) {
        Query namedQuery = context.createNamedQuery("Kwet.getByTag");
        namedQuery.setFirstResult(start);
        namedQuery.setMaxResults(count);
        namedQuery.setParameter("tag", tag);
        return (List<Kwet>) namedQuery.getResultList();
    }

    @Override
    public List<Kwet> getUserFeed(User user, int start, int count) {
        Query namedQuery = context.createNamedQuery("Kwet.getFeed");
        namedQuery.setFirstResult(start);
        namedQuery.setMaxResults(count);
        namedQuery.setParameter("username", user.getUsername());
        return (List<Kwet>) namedQuery.getResultList();
    }

    @Override
    public List<String> getTrendingKwets() {
        Query nativeQuery = context.createNativeQuery(
                "SELECT TAGS " +
                        "FROM kwetter_kwet_TAGS " +
                        "WHERE kwetter_kwet_ID IN (" +
                        "   SELECT ID " +
                        "   FROM KWETTER_KWET " +
                        "   WHERE POSTED > DATE_SUB(now(), INTERVAL 1 MONTH) " +
                        "   ORDER BY POSTED DESC) " + // NOTE: MariaDB doesn't support LIMIT in subquery
                        "GROUP BY TAGS " +
                        "ORDER BY count(TAGS) DESC " +
                        "LIMIT 5;");
        return (List<String>) nativeQuery.getResultList();
    }

    @Override
    public List<Kwet> getMentions(User user, int start, int count) {
//        Query namedQuery = context.createNamedQuery("Kwet.getMentions");
//        namedQuery.setFirstResult(start);
//        namedQuery.setMaxResults(count);
//        namedQuery.setParameter("username", user.getUsername());
//        return (List<Kwet>) namedQuery.getResultList();
        return Collections.emptyList();
    }
}