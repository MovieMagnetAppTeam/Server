package movieMagnet.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String review;
    @OneToMany(mappedBy="review_comments")
    private Collection<Comment> comments;
    @ManyToOne
    @JoinColumn(name="user_fk")
    private Long userId;
    @ManyToOne
    @JoinColumn(name="movie_fk")
    private Long movieId;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getReview() { return review; }

    public void setReview(String review) { this.review = review; }

    public Collection<Comment> getComments() { return comments; }

    public void setComments(Collection<Comment> comments) { this.comments = comments; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getMovieId() { return movieId; }

    public void setMovieId(Long movieId) { this.movieId = movieId; }
}

