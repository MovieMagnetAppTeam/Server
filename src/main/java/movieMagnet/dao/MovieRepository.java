package movieMagnet.dao;

import movieMagnet.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByName(String name);

    @Override
    void delete(Movie movie);
}
