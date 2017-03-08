package nl.rickrongen.fontys.kwetter.Service;

import nl.rickrongen.fontys.kwetter.DAO.*;
import nl.rickrongen.fontys.kwetter.Domain.*;

public class KwetterService {

	@Inject
	private IKwetterDao kwetterDao;

	/**
	 * 
	 * @param username
	 */
	public User getUser(String username) {
		// TODO - implement KwetterService.getUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param user
	 */
	public boolean updateUser(User user) {
		// TODO - implement KwetterService.updateUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param username
	 */
	public boolean addUser(String username) {
		// TODO - implement KwetterService.addUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param username
	 * @param fullName
	 * @param location
	 * @param website
	 * @param biography max 160
	 */
	public boolean addUser(String username, String fullName, String location, String website, String biography) {
		// TODO - implement KwetterService.addUser
		throw new UnsupportedOperationException();
	}

	/**
	 * true if now following, false otherwise
	 * @param actor
	 * @param target
	 */
	public boolean toggleFollow(User actor, User target) {
		// TODO - implement KwetterService.toggleFollow
		throw new UnsupportedOperationException();
	}

	/**
	 * The created kwet if successful, Null if it failed
	 * @param actor
	 * @param text
	 */
	public Kwet postKwet(User actor, String text) {
		// TODO - implement KwetterService.postKwet
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param query
	 */
	public List<Kwet> searchKwets(String query) {
		// TODO - implement KwetterService.searchKwets
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param username
	 * @param start
	 * @param count
	 */
	public List<Kwet> getKwetsOfUser(String username, int start, int count) {
		// TODO - implement KwetterService.getKwetsOfUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param user
	 */
	public boolean updateUser(User user) {
		// TODO - implement KwetterService.updateUser
		throw new UnsupportedOperationException();
	}

	/**
	 * true if now following, false otherwise
	 * @param actor
	 * @param target
	 */
	public boolean toggleFollow(User actor, User target) {
		// TODO - implement KwetterService.toggleFollow
		throw new UnsupportedOperationException();
	}

	/**
	 * The created kwet if successful, Null if it failed
	 * @param actor
	 * @param text
	 */
	public Kwet postKwet(User actor, String text) {
		// TODO - implement KwetterService.postKwet
		throw new UnsupportedOperationException();
	}

}