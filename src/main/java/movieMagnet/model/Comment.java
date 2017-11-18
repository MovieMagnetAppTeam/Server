package movieMagnet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String comment;
    private Long userId;

    public Comment(String comment) { this.comment = comment; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }
}
