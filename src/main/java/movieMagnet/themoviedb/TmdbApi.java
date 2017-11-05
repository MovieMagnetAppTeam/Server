package movieMagnet.themoviedb;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import movieMagnet.config.KeyChainConfig;
import movieMagnet.themoviedb.model.SearchResultTmdb;

@Service
public class TmdbApi implements TmdbApiInterface {
	private String key = "";
	private String readAccessToken = "";
	private static final String UPCOMING_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=%s&language=en-US&page=1";
	private static final String SEARCH_MOVIES_URL = "https://api.themoviedb.org/3/search/movie?api_key=%s&language=en-US&query=%s&page=%i&include_adult=%s";
	private static final String SEARCH_SERIES_URL = "https://api.themoviedb.org/3/search/tv?api_key=%s&language=en-US&query=%s&page=%i";
	/*
	 * Example request: https://api.themoviedb.org/3/movie/550?api_key=
	 * 63695ff09031431214f5e6a27f684a1d
	 */

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private KeyChainConfig keys;
	
	@PostConstruct
	public void prepareBean() {
		key = keys.getThemoviedbKey();
		readAccessToken = keys.getThemoviedbReadAccessToken();
	}
	
	@Override
	public SearchResultTmdb fetchNews() {
		String url = String.format(UPCOMING_URL, key);
		return restTemplate.getForObject(url, SearchResultTmdb.class);
	}

	@Override
	public SearchResultTmdb searchMovie(String query, Integer page, Boolean includeAdult) {
		String url = String.format(SEARCH_MOVIES_URL, key, query, page, includeAdult.toString());
		return restTemplate.getForObject(url, SearchResultTmdb.class);
	}

	@Override
	public SearchResultTmdb searchTvShow(String query, Integer page) {
		String url = String.format(SEARCH_SERIES_URL, key, query, page);
		return restTemplate.getForObject(url, SearchResultTmdb.class);
	}

}
