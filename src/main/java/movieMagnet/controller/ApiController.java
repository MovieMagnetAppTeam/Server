package movieMagnet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import movieMagnet.dto.UserDto;
import movieMagnet.openmoviedb.OmdbApiInterface;
import movieMagnet.openmoviedb.model.MovieType;
import movieMagnet.services.UserService;
import movieMagnet.themoviedb.TmdbApiInterface;

@RestController
public class ApiController {

	@Autowired
	public TmdbApiInterface tmdb;

	@Autowired
	public OmdbApiInterface omdb;

	@Autowired
	public UserService userService;

	@RequestMapping("/tmdb/news")
	public String news() {
		return tmdb.fetchNews().toString();
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

	@RequestMapping(method = RequestMethod.POST, value = "/login", headers = "Content-Type=application/x-www-form-urlencoded")
	public String login(@RequestParam("p") String password, @RequestParam("l") String login) {
		return userService.login(password, login).toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/register", headers = "Content-Type=application/x-www-form-urlencoded")
	public String register(@RequestParam("user") UserDto user) {
		return userService.register(user).toString();
	}

	public TmdbApiInterface getHelper() {
		return tmdb;
	}

	public void setHelper(TmdbApiInterface helper) {
		this.tmdb = helper;
	}

}
