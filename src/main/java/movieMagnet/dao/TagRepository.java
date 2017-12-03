package movieMagnet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import movieMagnet.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

	Tag findByName(String name);

	Tag findByTagId(String id);

	@Override
	void delete(Tag tag);
}
