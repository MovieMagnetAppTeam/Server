package movieMagnet.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import movieMagnet.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	Optional<Review> findById(Long id);

    @Override
    void delete(Review review);
}
