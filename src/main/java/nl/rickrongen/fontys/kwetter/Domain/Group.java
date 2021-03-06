package nl.rickrongen.fontys.kwetter.Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "kwetter_group")
public class Group implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToMany(mappedBy = "groups")
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