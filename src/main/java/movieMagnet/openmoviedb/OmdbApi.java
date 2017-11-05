package movieMagnet.openmoviedb;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import movieMagnet.config.KeyChainConfig;
import movieMagnet.openmoviedb.model.MovieType;
import movieMagnet.openmoviedb.model.SearchResultOmdb;

@Component
public class OmdbApi implements OmdbApiInterface {
	public static String apiKey = "";
	public static final String SEARCH_FOR_TITLE_URL = "http://omdbapi.com/?s=%s&apikey=%s";
	public static final String SEARCH_FOR_TYPE_URL = "http://omdbapi.com/?s=%s&apikey=%s&type=%s";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private KeyChainConfig keys;

	@PostConstruct
	public void prepareBean() {
		apiKey = keys.getOmdbKey();
	}

	@Override
	public SearchResultOmdb searchForTitle(String title) {
		String url = String.format(SEARCH_FOR_TITLE_URL, title, apiKey);
		return restTemplate.getForObject(url, SearchResultOmdb.class);
	}

	@Override
	public SearchResultOmdb searchForType(String title, MovieType type) {
		String url = String.format(SEARCH_FOR_TYPE_URL, title, type.toString(), apiKey);
		return restTemplate.getForObject(url, SearchResultOmdb.class);
	}

}
