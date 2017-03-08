package nl.rickrongen.fontys.kwetter.Domain;

import java.util.*;

public class Group {

	private int id;
	private String name;
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