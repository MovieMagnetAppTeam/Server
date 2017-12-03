package movieMagnet.services;

import movieMagnet.dao.MovieRepository;
import movieMagnet.dao.ReviewRepository;
import movieMagnet.dao.UserRepository;
import movieMagnet.dto.MovieDto;
import movieMagnet.dto.ReviewDto;
import movieMagnet.model.Movie;
import movieMagnet.model.Review;
import movieMagnet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private MovieRepository movieRepo;
    @Autowired
    private UserRepository userRepo;

    public Collection<ReviewDto> getMovieReviews(MovieDto movie) {
        return movie.getReviews();
    }

    public Review getReviewById(String id) {
        return reviewRepo.findById(id);
    }

    public Review addReview(ReviewDto review) {
        Review reviewEnt = new Review();
        User user = userRepo.findByEmail(review.getAuthor());
        reviewEnt.setAuthor(user);
        reviewEnt.setId(review.getReviewId());
        Optional<Movie> movie = movieRepo.findById(review.getMovieId());
        movie.ifPresent(reviewEnt::setMovie);
        return reviewRepo.save(reviewEnt);
    }

}
