package movieMagnet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import movieMagnet.model.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDto {
    @JsonProperty
    private Long reviewId;
    @JsonProperty
    private String text;
    @JsonProperty
    private String author;
    @JsonProperty
    private Long movieId;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
