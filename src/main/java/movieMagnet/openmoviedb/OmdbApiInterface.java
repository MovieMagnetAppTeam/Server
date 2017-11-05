package movieMagnet.openmoviedb;

import movieMagnet.openmoviedb.model.MovieType;
import movieMagnet.openmoviedb.model.SearchResultOmdb;

public interface OmdbApiInterface {
	public SearchResultOmdb searchForTitle(String title);
	public SearchResultOmdb searchForType(String title, MovieType type);
}
