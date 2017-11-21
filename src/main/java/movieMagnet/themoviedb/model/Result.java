package movieMagnet.themoviedb.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	@JsonProperty("poster_path")
	private String poster;
	private boolean adult;
	private String overview;
	@JsonProperty("release_date")
	private String released;
	private String title;
	@JsonProperty("original_title")
	private String originalTitle;
	@JsonProperty("vote_average")
	private float voteAvg;
	@JsonProperty("genre_ids")
	private List<String> ids;
	

	public String getPoster() {
		return "https://image.tmdb.org/t/p/w500"+poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public float getVoteAvg() {
		return voteAvg;
	}

	public void setVoteAvg(float voteAvg) {
		this.voteAvg = voteAvg;
	}


	@Override
	public String toString() {
		return "Result [poster=" + poster + ", adult=" + adult + ", overview=" + overview + ", released=" + released
				+ ", title=" + title + ", originalTitle=" + originalTitle + ", voteAvg=" + voteAvg  + "]";
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}


}
