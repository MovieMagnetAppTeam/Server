package movieMagnet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String category;
    private String desription;
    private String poster;
    private Collection<Rate> rates;
    private Collection<Review> reviews;

    public Movie(String name) { this.name = name; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getDesription() { return desription; }

    public void setDesription(String desription) { this.desription = desription; }

    public String getPoster() { return poster; }

    public void setPoster(String poster) { this.poster = poster; }

    public Collection<Rate> getRates() { return rates; }

    public void setRates(Collection<Rate> rates) { this.rates = rates; }

    public Collection<Review> getReviews() { return reviews; }

    public void setReviews(Collection<Review> reviews) { this.reviews = reviews; }
}
