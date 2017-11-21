package movieMagnet.services;

import java.util.List;

import movieMagnet.dto.UserDto;
import movieMagnet.model.Tag;
import movieMagnet.model.User;

public interface UserService {
	public User register(UserDto user);
	public User login(String password, String login);
	public User getUserByEmail(String email);
	public void addTagsToUser(User user, List<Tag> tags);
}
