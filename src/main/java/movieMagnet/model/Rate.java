package movieMagnet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rate;
    private Long userId;

    public Rate(int rate) { this.rate = rate; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getRate() { return rate; }

    public void setRate(int rate) { this.rate = rate; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }
}
