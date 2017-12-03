package movieMagnet.themoviedb;

import movieMagnet.themoviedb.model.Genres;
import movieMagnet.themoviedb.model.SearchResultTmdb;

public interface TmdbApiInterface {
	public SearchResultTmdb fetchNews();

	public SearchResultTmdb searchMovie(String query);

	public SearchResultTmdb searchTvShow(String query);
	
	public Genres getGenresList();
}