package movieMagnet.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import movieMagnet.dao.ReviewRepository;
import movieMagnet.dto.CommentDto;
import movieMagnet.dto.MovieDto;
import movieMagnet.dto.ReviewDto;
import movieMagnet.model.Comment;
import movieMagnet.model.Movie;
import movieMagnet.model.Review;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepo;
	@Autowired
	private MovieService movieService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;

	public Collection<ReviewDto> getMovieReviews(MovieDto movie) {
		return movie.getReviews();
	}

	public List<ReviewDto> getMovieReviews(Movie movie) {
		List<ReviewDto> reviews = new ArrayList<ReviewDto>();
		for (Review review : movie.getReviews()) {
			reviews.add(convertReviewToDto(review));
		}
		return reviews;
	}

	public Review getReviewById(Long id) {
		try {
			return reviewRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public ReviewDto getReviewByIdDto(Long id) {
		try {
			return convertReviewToDto(reviewRepo.findById(id).get());
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public Review addReview(ReviewDto review) {
		Review reviewEnt = new Review();
		reviewEnt = convertDtoToReview(review);
		Movie mov = reviewEnt.getMovie();
		Collection<Review> revs = mov.getReviews();
		if (revs != null) {
			revs.add(reviewEnt);
		} else {
			revs = new ArrayList<Review>();
			revs.add(reviewEnt);
		}
		mov.setReviews(revs);
		movieService.update(mov);
		return reviewRepo.save(reviewEnt);

	}

	public Review addCommentToReview(Review review, Comment comment) {
		List<Comment> commentList = (List<Comment>) review.getComments();
		comment.setReview(review);
		commentList.add(comment);
		review.setComments(commentList);
		return reviewRepo.save(review);
	}

	public ReviewDto convertReviewToDto(Review review) {
		ReviewDto dto = new ReviewDto();
		dto.setAuthor(review.getAuthor().getName());
		List<CommentDto> comments = new ArrayList<CommentDto>();
		if (comments != null) {
			for (Comment comment : review.getComments()) {
				comments.add(commentService.convertCommentToDto(comment));
			}
		}
		dto.setComments(comments);
		dto.setMovieId(review.getMovie().getId());
		dto.setReviewId(review.getId());
		dto.setText(review.getText());
		return dto;
	}

	public Review convertDtoToReview(ReviewDto dto) {
		Review review = new Review();
		review.setAuthor(userService.getUserByEmail(dto.getAuthor()));
		review.setComments(
				dto.getReviewId() != null ? commentService.getReviewComments(dto.getReviewId().toString()) : null);
		review.setMovie(movieService.findByIdMovie(dto.getMovieId()));
		review.setText(dto.getText());
		return review;
	}

}
