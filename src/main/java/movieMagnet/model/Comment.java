package movieMagnet.model;

import javax.persistence.*;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String comment;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_comment")
	private User author;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_review")
	private Review review;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

}
