package nl.rickrongen.fontys.kwetter.Service;

import nl.rickrongen.fontys.kwetter.DAO.*;
import nl.rickrongen.fontys.kwetter.Domain.*;
import nl.rickrongen.fontys.kwetter.interceptors.QualifiedEvent;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
public class KwetterService{

    private static final Pattern reHashtag = Pattern.compile("#(\\S+)");
    private static final Pattern reMention = Pattern.compile("@(\\S+)");

    @Inject
	@QualifiedEvent("PostKwet")
    private Event<Kwet> kwetPosted;

	@Inject @JPA
	IKwetterDao kwetterDao;

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
	 * @exception IllegalArgumentException Thrown when one of the arguments is not valid
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
	    return kwetterDao.toggleFollowUser(actor,target);
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
	        Kwet k = kwetterDao.getKwetsOfUser(actor.getUsername(), 0, 1).get(0);
	        kwetPosted.fire(k);
	        return k;
        }
        return null;
	}

	/**
	 * Search for kwets, not yet implemented
	 * @param query The search query
	 */
	public List<Kwet> searchKwets(String query, int start, int count) {
        String[] split = query.split(" ");
        return kwetterDao.searchKwets(Arrays.asList(split), null, null, start, count);
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

    /**
     * Let an actor like a specific kwet
     * @param actor The actor
     * @param kwet The kwet
     * @return true if the user likes the kwet afterwards, false otherwise
     */
	public boolean likeKwet(User actor, Kwet kwet){
	    return kwetterDao.likeKwet(actor, kwet);
    }

	/**
	 * Get a kwet by it's ID
	 * @param id the ID of the kwet
	 * @return The kwet or null
	 */
	public Kwet getKwetById(int id) {
		return kwetterDao.getKwetById(id);
	}

	/**
	 * Get a list of users
	 * @param start The first user to get
	 * @param count The amount of users
	 * @return A list of users or null if an error occurs
	 */
	public List<User> getUsers(int start, int count) {
		return kwetterDao.getUsers(start, count);
	}

    /**
     * Get a list of kwets that contain a specific tag (#{tag})
     * @param tag the tag(without #)
     * @param start the start
     * @param count the count
     * @return a list of kwets
     */
    public List<Kwet> getKwetsByTag(String tag, int start, int count) {
	    return kwetterDao.getKwetsByTag(tag, start, count);
    }

	/**
	 * Get a list of kwets that are relavent to the user
	 * @param user The user to get the feed of
	 * @return A list of kwets
	 */
	public List<Kwet> getUserFeed(User user, int start, int count) {
	    return kwetterDao.getUserFeed(user, start, count);
	}

    /**
     * Get a list of trending topics
     * @return A list of Strings
     */
	public List<String> getTrendingKwets(){
		return kwetterDao.getTrendingKwets();
	}

    public List<Kwet> getMentions(User user, int start, int count) {
		return kwetterDao.getMentions(user, start, count);
    }
}