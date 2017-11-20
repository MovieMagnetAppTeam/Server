package movieMagnet.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String text;
	@OneToMany(mappedBy = "review")
	private Collection<Comment> comments;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_review")
	private User author;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_review")
	private Movie movie;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}
