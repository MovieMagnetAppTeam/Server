package movieMagnet.dao;

import movieMagnet.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Override
    void delete(Review review);
}
