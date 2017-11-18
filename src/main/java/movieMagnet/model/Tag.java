package movieMagnet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tag;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTag() { return tag; }

    public void setTag(String tag) { this.tag = tag; }
}
