package movieMagnet.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@ManyToMany(mappedBy = "tags")
	private Collection<User> users;
	@ManyToMany(mappedBy = "tags")
	private Collection<Movie> movies;
	private String tagId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Collection<User> getUsers() {
		return users;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	public Collection<Movie> getMovies() {
		return movies;
	}
	public void setMovies(Collection<Movie> movies) {
		this.movies = movies;
	}
	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", movies=" + movies + "]";
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	
}