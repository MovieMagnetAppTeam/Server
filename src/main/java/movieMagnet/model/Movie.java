package movieMagnet.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String category;
    private String description;
    private String poster;
    @OneToMany(mappedBy="movie_rates")
    private Collection<Rate> rates;
    @OneToMany(mappedBy="movie_reviews")
    private Collection<Review> reviews;
    @ManyToMany
    @JoinTable(name = "role_priv", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Collection<Tag> tags;

    public Movie(String name) { this.name = name; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getPoster() { return poster; }

    public void setPoster(String poster) { this.poster = poster; }

    public Collection<Rate> getRates() { return rates; }

    public void setRates(Collection<Rate> rates) { this.rates = rates; }

    public Collection<Review> getReviews() { return reviews; }

    public void setReviews(Collection<Review> reviews) { this.reviews = reviews; }

    public Collection<Tag> getTags() { return tags; }

    public void setTags(Collection<Tag> tags) { this.tags = tags; }
}
