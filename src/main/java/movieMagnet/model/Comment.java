package movieMagnet.model;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String comment;
    @ManyToOne
    @JoinColumn(name="user_fk")
    private Long userId;
    @ManyToOne
    @JoinColumn(name="review_fk")
    private Long reviewId;

    public Comment(String comment) { this.comment = comment; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getReviewId() { return reviewId; }

    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }
}
