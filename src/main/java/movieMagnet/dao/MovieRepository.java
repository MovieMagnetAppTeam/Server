package movieMagnet.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import movieMagnet.model.Movie;
import java.lang.String;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	Movie findByName(String name);

	Optional<Movie> findById(Long id);

	List<Movie> findByImdbId(String imdbid);

	@Override
	void delete(Movie movie);
}
