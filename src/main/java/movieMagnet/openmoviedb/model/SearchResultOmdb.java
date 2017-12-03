package movieMagnet.openmoviedb.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultOmdb {
	@JsonProperty("Title")
	private String title;
	@JsonProperty("Year")
	private String year;
	@JsonProperty("imdbID")
	private String imdbId;
	@JsonProperty("Type")
	private MovieType type;
	@JsonProperty("Poster")
	private String posterLink;
	@JsonProperty("Genre")
	private String genre;
	@JsonProperty("Ratings")
	private List<Rating> ratings;
	@JsonProperty("Plot")
	private String plot;
	@JsonProperty("Response")
	private String response;
	@JsonProperty("Error")
	private String errorMsg;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getImdbId() {
		return imdbId;
	}
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	public MovieType getType() {
		return type;
	}
	public void setType(MovieType type) {
		this.type = type;
	}
	public String getPosterLink() {
		return posterLink;
	}
	public void setPosterLink(String posterLink) {
		this.posterLink = posterLink;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public List<Rating> getRatings() {
		return ratings;
	}
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	public String getPlot() {
		return plot;
	}
	public void setPlot(String plot) {
		this.plot = plot;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}