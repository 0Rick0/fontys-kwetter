package nl.rickrongen.fontys.kwetter.DAO;

import java.util.*;
import java.util.stream.Collectors;

import nl.rickrongen.fontys.kwetter.Domain.*;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

/**
 * Created by rick on 2/15/17.
 */
@Stateless
@Default
public class KwetterCollectionImplementation implements IKwetterDao {
	Set<User> users = new HashSet<>();
	List<Kwet> kwets = new ArrayList<>();

	@Override
	public boolean addUser(String username) {
		return users.add(new User(){{this.setUsername(username);}});
	}

	@Override
	public User getUser(String username) {
	    return users.stream().filter(user->user.getUsername().equals(username)).findFirst().orElse(null);
	}

	@Override
	public boolean toggleFollowUser(User actor, User toFollow) {
	    // create the list, if necesairy
	    if(actor.getFollowing() == null)
	        actor.setFollowing(new ArrayList<>());
        if(toFollow.getFollowedBy() == null)
            toFollow.setFollowedBy(new ArrayList<>());

        // check if the actor is already following the toFollow, and toggle the state
        if(actor.getFollowing().contains(toFollow)){
            actor.getFollowing().remove(toFollow);
            toFollow.getFollowedBy().remove(actor);
            return true;
        }else{
            actor.getFollowing().add(toFollow);
            toFollow.getFollowedBy().add(actor);
            return true;
        }
	}

	@Override
	public boolean updateUser(User actor) {
		return true;//not useful in collection mode
	}

	@Override
	public boolean postKwet(User actor, String text, List<String> tags, List<User> mentions) {
	    Kwet kwet = new Kwet();
	    kwet.setText(text);
	    kwet.setKwetBy(actor);
	    kwet.setTags(tags);
	    kwet.setMentions(mentions);

	    //todo check null
	    actor.getKwets().add(kwet);
        for (User mention :
                mentions) {
            //todo check null
            mention.getMentionedIn().add(kwet);
        }
        kwets.add(kwet);
        return true;
	}

	@Override
	public boolean likeKwet(User actor, Kwet kwet) {
	    //todo check null
	    kwet.getLikedBy().add(actor);
	    actor.getLikes().add(kwet);
		return true;
	}

	@Override
	public List<Kwet> getKwetsOfUser(String username, int start, int count) {
        User user = getUser(username);
        return user.getKwets().subList(start,count);
	}

	@Override
	public List<Kwet> searchKwets(List<String> texts, List<String> tags, List<String> mentions, int start, int count) {
	    return kwets.stream().filter(kwet -> {
	        return texts.stream().anyMatch(t->kwet.getText().contains(t))
                    || kwet.getTags().stream().anyMatch(t->tags.stream().anyMatch(t2->t2.equals(t)))
                    || kwet.getMentions().stream().anyMatch(m->mentions.stream().anyMatch(m2->m.getUsername().equals(m2)));
	    }).skip(start).limit(count).collect(Collectors.toList());
	}

	@Override
	public int getFollowerCount(String username) {
		return getUser(username).getFollowedBy().size();
	}

	@Override
	public int getFollowingCount(String username) {
		return getUser(username).getFollowing().size();
	}
}