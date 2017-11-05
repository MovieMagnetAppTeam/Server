package movieMagnet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import movieMagnet.openmoviedb.OmdbApiInterface;
import movieMagnet.openmoviedb.model.MovieType;
import movieMagnet.themoviedb.TmdbApiInterface;


@RestController
public class ApiController {

	@Autowired
	public TmdbApiInterface tmdb;
	
	@Autowired
	public OmdbApiInterface omdb;

	@RequestMapping("/tmdb/news")
	public String news() {
		return tmdb.fetchNews().toString();
	}
	
	@RequestMapping("/tmdb/searchMovie")
	public String movies(@RequestParam("query") String query, @RequestParam("page") Integer page, @RequestParam("adult") Boolean includeAdult) {
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

	public TmdbApiInterface getHelper() {
		return tmdb;
	}

	public void setHelper(TmdbApiInterface helper) {
		this.tmdb = helper;
	}

}
