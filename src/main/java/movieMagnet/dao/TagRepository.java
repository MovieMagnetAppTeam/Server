package movieMagnet.dao;

import movieMagnet.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Override
    void delete(Tag tag);
}
