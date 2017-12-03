package movieMagnet.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import movieMagnet.dao.CommentRepository;
import movieMagnet.dao.ReviewRepository;
import movieMagnet.dto.CommentDto;
import movieMagnet.model.Comment;
import movieMagnet.model.Review;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ReviewRepository reviewRepo;

	public Review addCommentToReview(Review review, Comment comment) {
		List<Comment> commentList = (List<Comment>) review.getComments();
		comment.setReview(review);
		commentList.add(comment);
		review.setComments(commentList);
		return reviewRepo.save(review);
	}
	
	public Comment addComment(CommentDto dto) {
		Comment comment = convertDtoToComment(dto);
		return commentRepo.save(comment);
	}

	public List<Comment> getReviewComments(String reviewId) {
		return null;
	}

	public CommentDto findById(Long id) {
		try {
			return convertCommentToDto(commentRepo.findById(id).get());
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public CommentDto convertCommentToDto(Comment comment) {
		CommentDto dto = new CommentDto();
		dto.setAuthorName(comment.getAuthor().getName());
		dto.setBody(comment.getComment());
		dto.setId(comment.getId().toString());
		dto.setReviewId(comment.getReview().getId().toString());
		return dto;
	}
	
	public Comment convertDtoToComment(CommentDto dto) {
		Comment comment = new Comment();
		comment.setAuthor(userService.getUserByEmail(dto.getAuthorName()));
		comment.setComment(dto.getBody());
		comment.setReview(reviewService.getReviewById(Long.parseLong(dto.getReviewId())));
		return comment;
	}

}
