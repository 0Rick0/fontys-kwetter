package nl.rickrongen.fontys.kwetter.Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Group implements Serializable{

	@Id
	@GeneratedValue
	private int id;
	private String name;
	@ManyToMany(mappedBy = "user_groups")
	private List<User> users;

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}