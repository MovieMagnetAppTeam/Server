package movieMagnet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import movieMagnet.dto.ReviewDto;
import movieMagnet.model.Review;
import movieMagnet.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import movieMagnet.dto.MovieDto;
import movieMagnet.dto.TagDto;
import movieMagnet.dto.UserDto;
import movieMagnet.model.Tag;
import movieMagnet.openmoviedb.OmdbApiInterface;
import movieMagnet.openmoviedb.model.SearchResultOmdb;
import movieMagnet.themoviedb.TmdbApiInterface;
import movieMagnet.themoviedb.model.Genres;
import movieMagnet.themoviedb.model.SearchResultTmdb;

@RestController
public class ApiController {
	@Autowired
	private ExternalService extService;
	
	@Autowired
	private TmdbApiInterface tmdb;


	@Autowired
	private OmdbApiInterface omdb;

	@Autowired
	public UserService userService;

	@Autowired
	public TagsService tagsService;
	
	@Autowired
	public MovieService movieService;

	@Autowired
	public ReviewService reviewService;

	@RequestMapping("news")
	public ResponseEntity<List<MovieDto>> news() {
		return new ResponseEntity<List<MovieDto>>(extService.fetchNews(), HttpStatus.OK);
	}

	@RequestMapping("news/filter")
	public ResponseEntity<List<MovieDto>> filteredNews() {
		List<MovieDto> results = extService.fetchNews();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		List<String> userTagIds = getTagIds((Set<Tag>) tagsService.getUserTags(userService.getUserByEmail(email)));
		List<MovieDto> filtered = new ArrayList<MovieDto>();
		for (MovieDto result : results) {
			for (String tagId : result.getGenres()) {
				if (userTagIds.contains(tagId)) {
					filtered.add(result);
					break;
				}
			}
		}
		return new ResponseEntity<List<MovieDto>>(filtered, HttpStatus.OK);
	}

	private List<String> getTagIds(Set<Tag> userTags) {
		List<String> strings = new ArrayList<String>();
		for (Tag tag : userTags) {
			strings.add(tag.getTagId());
		}
		return strings;
	}

	@RequestMapping("search_movie")
	public ResponseEntity<List<MovieDto>> movies(@RequestParam("query") String query) {
		return new ResponseEntity<List<MovieDto>>(extService.searchMovie(query), HttpStatus.OK);
	}
	
	@RequestMapping("search_movie_debug_tmdb")
	public ResponseEntity<SearchResultTmdb> tmdb(@RequestParam("query") String query) {
		return new ResponseEntity<SearchResultTmdb>(tmdb.searchMovie(query), HttpStatus.OK);
	}
	
	@RequestMapping("search_movie_debug_omdb")
	public ResponseEntity<SearchResultOmdb> deb(@RequestParam("query") String query) {
		return new ResponseEntity<SearchResultOmdb>(omdb.searchForTitle(query), HttpStatus.OK);
		
	}

	@RequestMapping("search_tv_show")
	public ResponseEntity<List<MovieDto>> tvShows(@RequestParam("query") String query) {
		return new ResponseEntity<List<MovieDto>>(extService.searchTvShow(query), HttpStatus.OK);
	}

	@RequestMapping("get_genres")
	public ResponseEntity<Genres> getGenres() {
		return new ResponseEntity<Genres>(extService.getGenresList(), HttpStatus.OK);
	}
	
	@RequestMapping("get_movie_id")
	public ResponseEntity<MovieDto> movie(@RequestParam("id") Long id) {
		return new ResponseEntity<MovieDto>(movieService.findById(id), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_movie_reviews")
	public ResponseEntity<List<ReviewDto>> getMovieReviews(@RequestParam("id") Long id) {
		List<ReviewDto> reviewList = new ArrayList<>(reviewService.getMovieReviews(movieService.findById(id)));
		return new ResponseEntity<List<ReviewDto>>(reviewList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add_review", consumes = "application/json")
	public ResponseEntity<String> addReview(@RequestBody ReviewDto review) {
		reviewService.addReview(review);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_my_tags")
	public ResponseEntity<List<TagDto>> getUserTags() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		System.out.println("/getMyTags " + email);
		System.out.println(userService.getUserByEmail(email));
		Set<Tag> tags = (Set<Tag>) tagsService.getUserTags(userService.getUserByEmail(email));
		List<TagDto> tagList = mapTagsToDto(tags);
		return new ResponseEntity<List<TagDto>>(tagList, HttpStatus.OK);
	}

	private List<TagDto> mapTagsToDto(Set<Tag> tags) {
		List<TagDto> list = new ArrayList<TagDto>();
		for (Tag tag : tags) {
			list.add(mapTag(tag));
		}
		return list;
	}

	private TagDto mapTag(Tag tag) {
		TagDto dto = new TagDto();
		dto.setGenreId(tag.getTagId());
		dto.setName(tag.getName());
		return dto;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/register", consumes = "application/json")
	public ResponseEntity<String> register(@RequestBody UserDto user) {
		userService.register(user);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@ExceptionHandler({ Exception.class })
	public void resolveException(Exception e) {
		e.printStackTrace();
	}

}
