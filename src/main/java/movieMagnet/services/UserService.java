package movieMagnet.services;

import movieMagnet.dto.UserDto;
import movieMagnet.model.User;

public interface UserService {
	public User register(UserDto user);
	public User login(String password, String login);
}
