package nl.rickrongen.fontys.kwetter.Domain;

import java.util.*;

public class User {

	private int id;
	private String username;
	/**
	 * sha-256
	 */
	private String password;
	private String fullName;
	private String location;
	private String website;
	/**
	 * max len 160
	 */
	private String biography;
	/**
	 * base64 image
	 */
	private String profilePicture;
	private List<User> following;
	private List<User> followedBy;
	private List<Group> groups;
	private List<Kwet> mentionedIn;
	private List<Kwet> kwets;
	private List<Kwet> likes;

	public int getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBiography() {
		return this.biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getProfilePicture() {
		return this.profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<User> getFollowing() {
		return this.following;
	}

	public void setFollowing(List<User> following) {
		this.following = following;
	}

	public List<User> getFollowedBy() {
		return this.followedBy;
	}

	public void setFollowedBy(List<User> followedBy) {
		this.followedBy = followedBy;
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Kwet> getMentionedIn() {
		return this.mentionedIn;
	}

	public void setMentionedIn(List<Kwet> mentionedIn) {
		this.mentionedIn = mentionedIn;
	}

	public List<Kwet> getKwets() {
		return this.kwets;
	}

	public void setKwets(List<Kwet> kwets) {
		this.kwets = kwets;
	}

	public List<Kwet> getLikes() {
		return this.likes;
	}

	public void setLikes(List<Kwet> likes) {
		this.likes = likes;
	}

	@Override()
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof User) && ((User) o).getUsername().equals(username);
	}
}