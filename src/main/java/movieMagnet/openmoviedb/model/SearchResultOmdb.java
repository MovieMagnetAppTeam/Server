package movieMagnet.openmoviedb.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultOmdb {
	@JsonProperty("Search")
	private List<SearchModel> content;

	public List<SearchModel> getContent() {
		return content;
	}

	public void setContent(List<SearchModel> content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SearchResult [content=" + content + "]";
	}

}