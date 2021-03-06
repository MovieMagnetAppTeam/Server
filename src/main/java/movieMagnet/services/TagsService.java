package movieMagnet.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import movieMagnet.dao.TagRepository;
import movieMagnet.model.Tag;
import movieMagnet.model.User;

@Service
public class TagsService {
	@Autowired
	private TagRepository tagRepo;

	public Collection<Tag> getUserTags(User user) {
		return user.getTags();
	}

	public Tag getTagByName(String name) {
		return tagRepo.findByName(name);
	}

	public Tag getTagById(String id) {
		Tag tag = tagRepo.findByTagId(id);
		return tag;
	}

	public void saveAllTags(List<Tag> tags) {
		for (Tag tag : tags) {
			tagRepo.save(tag);
		}
	}

}
