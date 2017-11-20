package movieMagnet.model;

import javax.persistence.*;

@Entity
public class Rate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int rate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rate_author")
	private User author;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_rate")
	private Movie movie;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
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
