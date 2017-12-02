package movieMagnet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import movieMagnet.dto.TagDto;
import movieMagnet.dto.UserDto;
import movieMagnet.model.Tag;
import movieMagnet.openmoviedb.OmdbApiInterface;
import movieMagnet.openmoviedb.model.MovieType;
import movieMagnet.openmoviedb.model.SearchResultOmdb;
import movieMagnet.services.TagsService;
import movieMagnet.services.UserService;
import movieMagnet.themoviedb.TmdbApiInterface;
import movieMagnet.themoviedb.model.Genres;
import movieMagnet.themoviedb.model.Result;
import movieMagnet.themoviedb.model.SearchResultTmdb;

@RestController
public class ApiController {

	@Autowired
	public TmdbApiInterface tmdb;

	@Autowired
	public OmdbApiInterface omdb;

	@Autowired
	public UserService userService;

	@Autowired
	public TagsService tagsService;

	@RequestMapping("/tmdb/news")
	public ResponseEntity<SearchResultTmdb> news() {
		return new ResponseEntity<SearchResultTmdb>(tmdb.fetchNews(), HttpStatus.OK);
	}

	@RequestMapping("/tmdb/news/filter")
	public ResponseEntity<List<Result>> filteredNews() {
		List<Result> results = tmdb.fetchNews().getResults();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		List<String> userTagIds = getTagIds((Set<Tag>) tagsService.getUserTags(userService.getUserByEmail(email)));
		List<Result> filtered = new ArrayList<Result>();
		for (Result result : results) {
			for (String tagId : result.getIds()) {
				if (userTagIds.contains(tagId)) {
					filtered.add(result);
					break;
				}
			}
		}
		return new ResponseEntity<List<Result>>(filtered, HttpStatus.OK);
	}

	private List<String> getTagIds(Set<Tag> userTags) {
		List<String> strings = new ArrayList<String>();
		for (Tag tag : userTags) {
			strings.add(tag.getTagId());
		}
		return strings;
	}

	@RequestMapping("/tmdb/searchMovie")
	public ResponseEntity<SearchResultTmdb> movies(@RequestParam("query") String query,
			@RequestParam("page") Integer page, @RequestParam("adult") Boolean includeAdult) {
		return new ResponseEntity<SearchResultTmdb>(tmdb.searchMovie(query, page, includeAdult), HttpStatus.OK);
	}

	@RequestMapping("/tmdb/searchTvShow")
	public ResponseEntity<SearchResultTmdb> tvShows(@RequestParam("query") String query,
			@RequestParam("page") Integer page) {
		return new ResponseEntity<SearchResultTmdb>(tmdb.searchTvShow(query, page), HttpStatus.OK);
	}

	@RequestMapping("/omdb/searchTitle")
	public ResponseEntity<SearchResultOmdb> movieTitle(@RequestParam("title") String title) {
		return new ResponseEntity<SearchResultOmdb>(omdb.searchForTitle(title), HttpStatus.OK);
	}

	@RequestMapping("/omdb/searchType")
	public ResponseEntity<SearchResultOmdb> movieType(@RequestParam("title") String title,
			@RequestParam("type") MovieType type) {
		return new ResponseEntity<SearchResultOmdb>(omdb.searchForType(title, type), HttpStatus.OK);
	}

	@RequestMapping("/tmdb/getGenres")
	public ResponseEntity<Genres> getGenres() {
		return new ResponseEntity<Genres>(tmdb.getGenresList(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getMyTags")
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

	public TmdbApiInterface getHelper() {
		return tmdb;
	}

	public void setHelper(TmdbApiInterface helper) {
		this.tmdb = helper;
	}

	@ExceptionHandler({ Exception.class })
	public void resolveException(Exception e) {
		e.printStackTrace();
	}

}
