package movieMagnet.model;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "user_acc")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String lastname;
	private String password;
	private String secret;
	private String email;
	private boolean enabled;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "users_tags", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
	private Collection<Tag> tags;
	@OneToMany(mappedBy="author")
	private Collection<Comment> comments;
	@OneToMany(mappedBy="author")
	private Collection<Review> reviews;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "users_movies", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
	private Collection<Movie> movies;
	@OneToMany(mappedBy="author")
	private Collection<Rate> rates;
	
	public User() {
		super();
	}

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

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Collection<Tag> getTags() { return tags;	}

	public void setTags(Collection<Tag> tags) {	this.tags = tags; }

	public Collection<Comment> getComments() { return comments; }

	public void setComments(Collection<Comment> comments) { this.comments = comments; }

	public Collection<Review> getReviews() { return reviews; }

	public void setReviews(Collection<Review> reviews) { this.reviews = reviews; }


	
}
