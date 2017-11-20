package movieMagnet.dao;

import movieMagnet.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Override
    void delete(Comment comment);
}
