package movieMagnet.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import movieMagnet.dao.MovieRepository;
import movieMagnet.dto.MovieDto;
import movieMagnet.model.Movie;
import movieMagnet.model.Tag;

@Service
public class MovieService {
	@Autowired
	private TagsService tagService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private MovieRepository movieRepo;

	public MovieDto findById(Long id) {
		Optional<Movie> movie = movieRepo.findById(id);
		try {
			return covertToMovieDto(movie.get());
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public Long saveMovie(MovieDto movie) {
		Movie saved = movieRepo.findByName(movie.getTitle());
		if (saved == null) {
			Movie m = new Movie();
			m.setDescription(movie.getDescription());
			m.setImdbId(movie.getImdb_id());
			m.setPoster(movie.getPoster());
			m.setTags(convertToTags(movie.getGenres()));
			m.setName(movie.getTitle());
			m.setYear(movie.getYear());
			m.setType(movie.getType());
			saved = movieRepo.save(m);
		} else {
			if (movie.getImdb_id() != null && !movie.getImdb_id().equals(saved.getImdbId())) {
				saved.setImdbId(movie.getImdb_id());
				saved = movieRepo.save(saved);
			}
			if (movie.getYear() != null && !movie.getYear().equals(saved.getYear())) {
				saved.setYear(movie.getYear());
				saved = movieRepo.save(saved);
			}
		}
		return saved.getId();
	}

	private Collection<Tag> convertToTags(List<String> genres) {
		List<Tag> tags = new ArrayList<Tag>();
		for (String id : genres) {
			tags.add(tagService.getTagById(id));
		}
		return tags;
	}

	public MovieDto covertToMovieDto(Movie movie) {
		MovieDto dto = new MovieDto();
		dto.setDescription(movie.getDescription());
		dto.setPoster(movie.getPoster());
		dto.setTitle(movie.getName());
		dto.setYear(movie.getYear());
		dto.setType(movie.getType());
		dto.setGenres(convertToGenres(movie.getTags()));
		dto.setImdb_id(movie.getImdbId());
		dto.setId(movie.getId().toString());
		return dto;
	}

	private List<String> convertToGenres(Collection<Tag> tags) {
		List<String> list = new ArrayList<String>();
		for (Tag tag : tags) {
			list.add(tag.getTagId());
		}
		return list;
	}

}
