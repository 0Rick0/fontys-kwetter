package nl.rickrongen.fontys.kwetter.Service;

import nl.rickrongen.fontys.kwetter.DAO.*;
import nl.rickrongen.fontys.kwetter.Domain.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KwetterService {

    private static final Pattern reHashtag = Pattern.compile("#(\\S+)");
    private static final Pattern reMention = Pattern.compile("@(\\S+)");

	@Inject
	private IKwetterDao kwetterDao;

	/**
	 * Get the user object connected to a specific username
	 * @param username the username
	 */
	public User getUser(String username) {
		return kwetterDao.getUser(username);
	}

	/**
	 * Update a user
	 * @param user the user object
	 */
	public boolean updateUser(User user) {
	    return kwetterDao.updateUser(user);
	}

	/**
	 * Create a new user with only a username
	 * @param username the username
	 */
	public boolean addUser(String username) {
	    return kwetterDao.addUser(username);
	}

	/**
	 * Create a new user with some extra information
	 * @param username The username
	 * @param fullName The password
     * @param password the users password
	 * @param location The location
	 * @param website The website
	 * @param biography The biography of max 160
	 */
	public boolean addUser(String username, String fullName, String password, String location, String website, String biography) throws IllegalArgumentException
    {
        if(username == null || username.length() == 0)
            throw new IllegalArgumentException("Username not set!");
        if(fullName == null || fullName.length() == 0)
            throw new IllegalArgumentException("FullName not set!");
        if(password == null || password.length() == 0)
            throw new IllegalArgumentException("Password not set!");
        if(location == null || location.length() == 0)
            throw new IllegalArgumentException("Location not set!");
        if(website == null || website.length() == 0)
            throw new IllegalArgumentException("Website not set!");
        if(biography == null || biography.length() == 0 || biography.length()>160)
            throw new IllegalArgumentException("Biography not set or to long!");

        if(!kwetterDao.addUser(username))
            return false;
        User u = kwetterDao.getUser(username);
        u.setFullName(fullName);
        u.setPassword(password);
        u.setLocation(location);
        u.setWebsite(website);
        u.setBiography(biography);
        return kwetterDao.updateUser(u);
	}

	/**
	 * Toggle if a user is following another user
	 * @param actor the user that want's to follow another
	 * @param target the user to follow
     * @return true if now following, false otherwise
	 */
	public boolean toggleFollow(User actor, User target) {
	    return toggleFollow(actor,target);
	}

	/**
     * Post a new kwet message
	 * @param actor The actor that posts the kwet
	 * @param text the text to post
     * @return The created kwet if successful, Null if it failed
	 */
	public Kwet postKwet(User actor, String text) {
	    // get the tags
	    List<String> tags = new ArrayList<>();
	    Matcher tagMatcher = reHashtag.matcher(text);
	    while (tagMatcher.find())
            tags.add(tagMatcher.group(1));

	    // get the mentions
        Set<User> mentions = new HashSet<>();
        Matcher mentionMatcher = reMention.matcher(text);
        while (mentionMatcher.find())
            mentions.add(kwetterDao.getUser(mentionMatcher.group(1)));

	    if(kwetterDao.postKwet(actor, text, tags, new ArrayList<>(mentions))) {
	        return kwetterDao.getKwetsOfUser(actor.getUsername(), 0, 1).get(0);
        }
        return null;
	}

	/**
	 * Search for kwets, not yet implemented
	 * @param query The search query
	 */
	public List<Kwet> searchKwets(String query) {
		// TODO - implement KwetterService.searchKwets
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the kwets of a specific user
	 * @param username The username of the user to get kwets from
	 * @param start the start index
	 * @param count the amount of kwets to get
	 */
	public List<Kwet> getKwetsOfUser(String username, int start, int count) {
	    return kwetterDao.getKwetsOfUser(username, start, count);
	}
}