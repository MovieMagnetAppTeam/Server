package movieMagnet.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import movieMagnet.dao.UserRepository;
import movieMagnet.dto.UserDto;
import movieMagnet.model.Role;
import movieMagnet.model.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public User register(UserDto user) {
		if (userExist(user.getEmail())) {
			throw new RuntimeException("User with email " + user.getEmail() + " exist!");
		}
		User userEnt = new User();
		userEnt.setEmail(user.getEmail());
		userEnt.setLastname(user.getLastName());
		userEnt.setName(user.getFirstName());
		userEnt.setPassword(passwordEncoder.encode(user.getPassword()));
		userEnt.setRoles(Arrays.asList(new Role("ROLE_USER")));

		return userRepo.save(userEnt);

	}

	private boolean userExist(String email) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public User login(String password, String login) {
		if (password == null || login == null) {
			return null;
		} else {
			User user = userRepo.findByEmail(login);
			if (user != null && passwordEncoder.encode(user.getPassword()) == password) {
				return user;
			} else {
				return null;
			}
		}
	}

}
