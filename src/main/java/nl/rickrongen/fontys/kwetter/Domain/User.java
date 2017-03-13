package nl.rickrongen.fontys.kwetter.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Entity(name = "kwetter_user")
@NamedQueries({
        @NamedQuery(name = "User.getUserByName", query = "SELECT u FROM kwetter_user u WHERE u.username = :username"),
        @NamedQuery(name = "User.getFollowingCount", query = "SELECT COUNT(u.following) FROM kwetter_user u WHERE u.username = :username"),
        @NamedQuery(name = "User.getFollowedByCount", query = "SELECT COUNT(u.followedBy) FROM kwetter_user u WHERE u.username = :username"),
		@NamedQuery(name = "User.getAllUsers", query = "SELECT u FROM kwetter_user u")
})
public class User {

    @Id
    @GeneratedValue
	private int id;
	private String username;
	/**
	 * sha-256
	 */
	@JsonIgnore
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
	@ManyToMany
    @JoinTable(
            name = "following",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_target", referencedColumnName = "id")
    )
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
	@JsonIdentityReference(alwaysAsId = true)
	private List<User> following;
	@ManyToMany(mappedBy = "following")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
	@JsonIdentityReference(alwaysAsId = true)
	private List<User> followedBy;
	@ManyToMany
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id")
    )
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
	@JsonIdentityReference(alwaysAsId = true)
	private List<Group> groups;
	@ManyToMany
    @JoinTable(
            name = "user_mentions",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "kwet_id", referencedColumnName = "id")
    )
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
	private List<Kwet> mentionedIn;
	@OneToMany(mappedBy = "kwetBy")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
	private List<Kwet> kwets;
	@ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "kwet_id", referencedColumnName = "id")
    )
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
	private List<Kwet> likes;

    public User(String username, String password, String fullName, String location, String website, String biography, String profilePicture, List<User> following, List<User> followedBy, List<Group> groups, List<Kwet> mentionedIn, List<Kwet> kwets, List<Kwet> likes) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.location = location;
        this.website = website;
        this.biography = biography;
        this.profilePicture = profilePicture;
        this.following = following;
        this.followedBy = followedBy;
        this.groups = groups;
        this.mentionedIn = mentionedIn;
        this.kwets = kwets;
        this.likes = likes;
    }

    public User(){
        following = new ArrayList<>();
        followedBy = new ArrayList<>();
        groups = new ArrayList<>();
        mentionedIn = new ArrayList<>();
        kwets = new ArrayList<>();
        likes = new ArrayList<>();
    }

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
		this.password = hashPassword(password);
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

	private String hashPassword(String password){
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] hash = md.digest();
			for (byte b :
					hash) {
				sb.append(Integer.toString((b & 0xff) + 0x100, 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}