package movieMagnet.themoviedb.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Genres {
	@JsonProperty("genres")
	List<Tag> tags;

	@Override
	public String toString() {
		return "Genres [tags=" + tags + "]";
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	
	
}
