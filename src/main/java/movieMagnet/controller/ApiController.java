package movieMagnet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import movieMagnet.openmoviedb.OmdbApiInterface;
import movieMagnet.themoviedb.TmdbApiInterface;


@RestController
public class ApiController {

	@Autowired
	public TmdbApiInterface tmdb;
	
	@Autowired
	public OmdbApiInterface omdb;

	@RequestMapping("/tmdb/news")
	public String index() {
		return tmdb.fetchNews();
	}
	
	@RequestMapping("/omdb/searchTitle")
	public String title(@RequestParam("title") String title) {
		return omdb.searchForTitle(title);
	}

	public TmdbApiInterface getHelper() {
		return tmdb;
	}

	public void setHelper(TmdbApiInterface helper) {
		this.tmdb = helper;
	}

}
