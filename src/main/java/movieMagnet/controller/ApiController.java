package movieMagnet.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import movieMagnet.dao.TagRepository;
import movieMagnet.dao.UserRepository;
import movieMagnet.dto.CredentialsDto;
import movieMagnet.dto.UserDto;
import movieMagnet.model.Tag;
import movieMagnet.model.User;
import movieMagnet.openmoviedb.OmdbApiInterface;
import movieMagnet.openmoviedb.model.MovieType;
import movieMagnet.services.TagsService;
import movieMagnet.services.UserService;
import movieMagnet.themoviedb.TmdbApiInterface;
import movieMagnet.themoviedb.model.Genres;
import movieMagnet.themoviedb.model.Result;

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

	@Autowired
	private TagRepository tagsRepo;
	
	@Autowired
	private UserRepository	userRepo;
	
	@RequestMapping("/tmdb/news")
	public String news() {
		return tmdb.fetchNews().toString();
	}
	
	@RequestMapping("/tmdb/news/filter")
	public String filteredNews() {
		List<Result> results = tmdb.fetchNews().getResults();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		List<String> userTagIds = getTagIds((List<Tag>) tagsService.getUserTags(userService.getUserByEmail(email)));
		List<Result> filtered = new ArrayList<Result>();
		List<String> allTagNames = new ArrayList<String>();
		for (Result result : results) {
			for (String tagId : result.getIds()) {
				if(userTagIds.contains(tagId)) {
					filtered.add(result);
				}
			}
		}
		return filtered.toString();
	}

	private List<String> getTagIds(List<Tag> userTags) {
		List<String> strings = new ArrayList<String>();
		for (Tag tag : userTags) {
			strings.add(tag.getTagId());
		}
		return strings;
	}

	private List<String> getTagNames(List<Tag> userTags) {
		List<String> strings = new ArrayList<String>();
		for (Tag tag : userTags) {
			strings.add(tag.getName());
		}
		return strings;
	}


	@RequestMapping("/tmdb/searchMovie")
	public String movies(@RequestParam("query") String query, @RequestParam("page") Integer page,
			@RequestParam("adult") Boolean includeAdult) {
		return tmdb.searchMovie(query, page, includeAdult).toString();
	}

	@RequestMapping("/tmdb/searchTvShow")
	public String tvShows(@RequestParam("query") String query, @RequestParam("page") Integer page) {
		return tmdb.searchTvShow(query, page).toString();
	}

	@RequestMapping("/omdb/searchTitle")
	public String movieTitle(@RequestParam("title") String title) {
		return omdb.searchForTitle(title).toString();
	}

	@RequestMapping("/omdb/searchType")
	public String movieType(@RequestParam("title") String title, @RequestParam("type") MovieType type) {
		return omdb.searchForType(title, type).toString();
	}
	
	@RequestMapping("/tmdb/getGenres")
	public String getGenres() {
		return tmdb.getGenresList().toString();
	}

//	@RequestMapping(method = RequestMethod.POST, value = "/login", headers = "Content-Type:application/x-www-form-urlencoded")
//	public String login(@RequestBody CredentialsDto creds) {
//		return userService.login(creds.getPassword(), creds.getLogin()).toString();
//	}
//
	
	@RequestMapping(method = RequestMethod.GET, value = "/getMyTags")
	public String getUserTags() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		System.out.println("/getMyTags " + email);
		System.out.println(userService.getUserByEmail(email));
		Collection<Tag> tags = tagsService.getUserTags(userService.getUserByEmail(email));
		return tags.toString();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/register", consumes="application/json")
	public String register(@RequestBody UserDto user) {
		return userService.register(user).toString();
	}

	public TmdbApiInterface getHelper() {
		return tmdb;
	}

	public void setHelper(TmdbApiInterface helper) {
		this.tmdb = helper;
	}
	
	@ExceptionHandler({Exception.class})
	public void resolveException(Exception e) {
	    e.printStackTrace();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/init")
	public void init() {
		initTags();
		initUserTags();
	}
	
	@Transactional
	private void initTags() {
		User user = userService.getUserByEmail("admin@admin.com");
		Genres genres = tmdb.getGenresList();
		System.out.println(genres.toString());
		for (movieMagnet.themoviedb.model.Tag tag : genres.getTags()) {
			Tag modelTag = new Tag();
			modelTag.setName(tag.getName());
			modelTag.setTagId(tag.getId());
			tagsRepo.save(modelTag);
		}
	}
	
	@Transactional
	private void initUserTags() {
		User user = userService.getUserByEmail("admin@admin.com");
		List<Tag> tagslist = new ArrayList<Tag>();
		tagslist.add(tagsRepo.findByName("Adventure"));
		tagslist.add(tagsRepo.findByName("Animation"));
		tagslist.add(tagsRepo.findByName("Music"));
		userService.addTagsToUser(user, tagslist);
	}
	
}
