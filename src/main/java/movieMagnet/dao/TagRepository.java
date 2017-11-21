package movieMagnet.dao;

import movieMagnet.model.Tag;
import movieMagnet.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.lang.String;
import java.util.List;
import java.util.Collection;

public interface TagRepository extends JpaRepository<Tag, Long> {

	Tag findByName(String name);
	
    @Override
    void delete(Tag tag);
}
