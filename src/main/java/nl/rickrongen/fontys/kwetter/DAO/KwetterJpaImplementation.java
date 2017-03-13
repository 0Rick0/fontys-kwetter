package nl.rickrongen.fontys.kwetter.DAO;

import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by rick on 2/15/17.
 */
@Stateless
@JPA
public class KwetterJpaImplementation implements IKwetterDao {

    @PersistenceContext(unitName = "JaeMySQLSource")
	private EntityManager context;

	@Override
	public boolean addUser(String username) {
	    User u = new User();
	    u.setUsername(username);
	    context.persist(u);
		return u.getId()!=0;
	}

	@Override
	public User getUser(String username) {
        Query namedQuery = context.createNamedQuery("User.getUserByName");
        namedQuery.setParameter("username", username);
        try {
            return (User)namedQuery.getSingleResult();
        }catch (NoResultException nre){
            return null;
        }
	}

	@Override
	public boolean toggleFollowUser(User actor, User toFollow) {
	    actor = context.merge(actor);
	    toFollow = context.merge(toFollow);
	    if(actor == null || toFollow == null)
	        return false;
	    if(actor.getFollowing().contains(toFollow)){
	        actor.getFollowing().remove(toFollow);
        }else {
	        actor.getFollowing().add(toFollow);
        }
        context.persist(actor);
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
        return true;
	}

	@Override
	public boolean likeKwet(User actor, Kwet kwet) {
	    kwet = context.merge(kwet);
	    actor = context.merge(actor);
	    if (kwet == null || actor == null)
            return false;
	    if(kwet.getLikedBy().contains(actor)){
	        kwet.getLikedBy().remove(actor);
        }else {
	        kwet.getLikedBy().add(actor);
        }
        context.persist(kwet);
	    return kwet.getLikedBy().contains(actor);
	}

	@Override
	public List<Kwet> getKwetsOfUser(String username, int start, int count) {
        Query namedQuery = context.createNamedQuery("Kwet.getKwetsOfUser");
        namedQuery.setParameter("username", username);
        namedQuery.setFirstResult(start);
        namedQuery.setMaxResults(count);
        List resultList = namedQuery.getResultList();
        return (List<Kwet>)resultList;
    }

	@Override
	public List<Kwet> searchKwets(List<String> texts, List<String> tags, List<String> mentions, int start, int count) {
		return null;
	}

	@Override
	public int getFollowerCount(String username) {
	    Query namedQuery = context.createNamedQuery("User.getFollowingCount");
	    namedQuery.setParameter("username", username);
	    return (int)namedQuery.getSingleResult();
	}

	@Override
	public int getFollowingCount(String username) {
        Query namedQuery = context.createNamedQuery("User.getFollowedByCount");
        namedQuery.setParameter("username", username);
        return (int)namedQuery.getSingleResult();
	}
}