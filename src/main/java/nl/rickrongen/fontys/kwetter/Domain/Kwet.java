package nl.rickrongen.fontys.kwetter.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "kwetter_kwet")
@NamedQueries({
		@NamedQuery(name = "Kwet.getKwetsOfUser", query = "SELECT k FROM kwetter_kwet k WHERE k.kwetBy.username = :username"),
        @NamedQuery(name = "Kwet.getKwetById", query = "SELECT k FROM kwetter_kwet k WHERE k.id = :id")
})
public class Kwet implements Serializable{

    @Id
    @GeneratedValue
	private int id;
	private String text;
	@ElementCollection
	private List<String> tags;
	@ManyToMany(mappedBy = "mentionedIn")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
	@JsonIdentityReference(alwaysAsId = true)
	private List<User> mentions;
	@ManyToOne()
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
	@JsonIdentityReference(alwaysAsId = true)
	private User kwetBy;
	@ManyToMany(mappedBy = "likes")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
	@JsonIdentityReference(alwaysAsId = true)
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