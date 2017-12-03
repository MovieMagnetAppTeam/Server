package movieMagnet.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import movieMagnet.dto.MovieDto;
import movieMagnet.dto.VoteDto;
import movieMagnet.model.Tag;
import movieMagnet.openmoviedb.OmdbApiInterface;
import movieMagnet.openmoviedb.model.MovieType;
import movieMagnet.openmoviedb.model.Rating;
import movieMagnet.openmoviedb.model.SearchResultOmdb;
import movieMagnet.themoviedb.TmdbApiInterface;
import movieMagnet.themoviedb.model.Genres;
import movieMagnet.themoviedb.model.Result;
import movieMagnet.themoviedb.model.SearchResultTmdb;

@Service
public class ExternalService {
	@Autowired
	private TmdbApiInterface tmdb;

	@Autowired
	private TagsService tagService;

	@Autowired
	private OmdbApiInterface omdb;
	
	@Autowired
	private MovieService movieService;
	
	public List<MovieDto> fetchNews() {
		List<MovieDto> list = new ArrayList<MovieDto>();
		list.addAll(convertTmdbToDto(tmdb.fetchNews(), MovieType.movie));
		list = saveMovies(list);
		return list;
	}

	public List<MovieDto> searchMovie(String query) {
		List<MovieDto> list = new ArrayList<MovieDto>();
		list.addAll(convertTmdbToDto(tmdb.searchMovie(query), MovieType.movie));
		list.addAll(convertOmdbToDto(omdb.searchForTitle(query)));
		list = saveMovies(list);
		return list;
	}

	private List<MovieDto> saveMovies(List<MovieDto> list) {
		for (MovieDto movieDto : list) {
			movieDto.setId(movieService.saveMovie(movieDto).toString());
		}
		return list;
	}

	public List<MovieDto> searchTvShow(String query) {
		List<MovieDto> list = new ArrayList<MovieDto>();
		list.addAll(convertTmdbToDto(tmdb.searchTvShow(query), MovieType.series));
		list.addAll(convertOmdbToDto(omdb.searchForType(query, MovieType.series)));
		list = saveMovies(list);
		return list;
	}

	public Genres getGenresList() {
		return tmdb.getGenresList();
	}

	public List<MovieDto> searchForType(String title, MovieType type) {
		return new ArrayList<MovieDto>();
	}

	private List<MovieDto> convertTmdbToDto(SearchResultTmdb result, MovieType type) {
		List<MovieDto> list = new ArrayList<MovieDto>();
		for (Result res : result.getResults()) {
			list.add(convertSingleTmdb(res, type));
		}
		return list;
	}

	private MovieDto convertSingleTmdb(Result res, MovieType type) {
		MovieDto movie = new MovieDto();
		if (res != null) {
			movie.setDescription(res.getOverview());
			movie.setGenres(res.getIds());
			movie.setPoster(res.getPoster());
			movie.setTitle(res.getTitle() != null ? res.getTitle() : res.getName());
			movie.setType(type.name());
			movie.setVotes(convertVotes(res.getVoteAvg()));
			movie.setYear(convertDate(res.getReleased()));
			movie.setImdb_id(res.getImdbId());
		}
		return movie;
	}

	private String convertDate(String released) {
		if (released != null) {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
			LocalDate date = LocalDate.parse(released, format);
			return Integer.toString(date.getYear());
		}
		return null;
	}

	private List<VoteDto> convertVotes(float voteAvg) {
		List<VoteDto> list = new ArrayList<VoteDto>();
		VoteDto vote = new VoteDto();
		vote.setSource("Average");
		vote.setValue(Float.toString(voteAvg));
		list.add(vote);
		return list;
	}

	private List<MovieDto> convertOmdbToDto(SearchResultOmdb result) {
		List<MovieDto> list = new ArrayList<MovieDto>();
		if (result != null) {
			list.add(convertSingleOmdb(result));
		}
		return list;
	}

	private MovieDto convertSingleOmdb(SearchResultOmdb res) {
		MovieDto movie = new MovieDto();
		if (res != null) {
			movie.setDescription(res.getPlot());
			movie.setGenres(convertGenres(res.getGenre()));
			movie.setPoster(res.getPosterLink());
			movie.setTitle(res.getTitle());
			movie.setType(res.getType().name());
			movie.setVotes(convertVotes(res.getRatings()));
			movie.setYear(res.getYear());
			movie.setImdb_id(res.getImdbId());
		}
		return movie;
	}

	private List<VoteDto> convertVotes(List<Rating> ratings) {
		List<VoteDto> votes = new ArrayList<VoteDto>();
		if (ratings != null) {
			for (Rating rat : ratings) {
				VoteDto vote = new VoteDto();
				vote.setSource(rat.getSource());
				vote.setValue(rat.getValue());
				votes.add(vote);
			}
		}
		return votes;
	}

	private List<String> convertGenres(String genre) {
		List<String> result = new ArrayList<String>();
		if (genre != null) {
			List<String> strings = Arrays.asList(genre.split(","));
			for (String string : strings) {
				Tag tag = tagService.getTagByName(string);
				if (tag != null) {
					result.add(tag.getTagId());
				}
			}
		}
		return result;
	}
}
