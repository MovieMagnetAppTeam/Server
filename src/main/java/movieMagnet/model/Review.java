package movieMagnet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String review;
    private Collection<Comment> comments;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getReview() { return review; }

    public void setReview(String review) { this.review = review; }

    public Collection<Comment> getComments() { return comments; }

    public void setComments(Collection<Comment> comments) { this.comments = comments; }
}

