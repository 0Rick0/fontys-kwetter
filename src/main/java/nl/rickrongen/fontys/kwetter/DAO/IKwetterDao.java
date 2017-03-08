package nl.rickrongen.fontys.kwetter.DAO;

import nl.rickrongen.fontys.kwetter.Domain.*;

import java.util.List;

public interface IKwetterDao {

	/**
	 * 
	 * @param username
	 */
	boolean addUser(String username);

	/**
	 * 
	 * @param username
	 */
	User getUser(String username);

	/**
	 * 
	 * @param actor
	 * @param toFollow
	 */
	boolean toggleFollowUser(User actor, User toFollow);

	/**
	 * 
	 * @param actor also has updated data
	 */
	boolean updateUser(User actor);

	/**
	 * 
	 * @param actor
	 * @param text
	 * @param tags
	 * @param mentions
	 */
	boolean postKwet(User actor, String text, List<String> tags, List<User> mentions);

	/**
	 * 
	 * @param actor
	 * @param kwet
	 */
	boolean likeKwet(User actor, Kwet kwet);

	/**
	 * lazyloading list
	 * @param username
	 * @param start
	 * @param count
	 */
	java.util.List<Kwet> getKwetsOfUser(String username, int start, int count);

	/**
	 * 
	 * @param texts
	 * @param tags
	 * @param mentions
	 * @param start default 0
	 * @param count
	 */
	java.util.List<Kwet> searchKwets(List<String> texts, List<String> tags, List<String> mentions, int start, int count);

	/**
	 * 
	 * @param username
	 */
	int getFollowerCount(String username);

	/**
	 * 
	 * @param username
	 */
	int getFollowingCount(String username);

}