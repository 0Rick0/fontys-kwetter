package nl.rickrongen.fontys.kwetter.Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Kwet implements Serializable{

    @Id
    @GeneratedValue
	private int id;
	private String text;
	@ElementCollection
	private List<String> tags;
	@ManyToMany(mappedBy = "user_mentions")
	private List<User> mentions;
	@OneToMany
    @JoinColumn(name = "userId")
	private User kwetBy;
	@ManyToMany(mappedBy = "user_likes")
	private List<User> likedBy;

	public int getId() {
		return this.id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getTags() {
		return this.tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<User> getMentions() {
		return this.mentions;
	}

	public void setMentions(List<User> mentions) {
		this.mentions = mentions;
	}

	public User getKwetBy() {
		return this.kwetBy;
	}

	public void setKwetBy(User kwetBy) {
		this.kwetBy = kwetBy;
	}

	public List<User> getLikedBy() {
		return this.likedBy;
	}

	public void setLikedBy(List<User> likedBy) {
		this.likedBy = likedBy;
	}

}