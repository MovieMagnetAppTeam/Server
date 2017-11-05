package movieMagnet.themoviedb;

import movieMagnet.themoviedb.model.SearchResultTmdb;

public interface TmdbApiInterface {
	public SearchResultTmdb fetchNews();

	public SearchResultTmdb searchMovie(String query, Integer page, Boolean includeAdult);

	public SearchResultTmdb searchTvShow(String query, Integer page);
}